package com.bit.api.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
// API IOC 大仓库
/**
 * api 注册中心 
 * @author Tommy
 *
 */
public class ApiStore {
    private ApplicationContext applicationContext;
    // API 接口住的地方 
    private HashMap<String, ApiRunnable> apiMap = new HashMap<String, ApiRunnable>();
    
    
// spring ioc 
    public ApiStore(ApplicationContext applicationContext) {
        Assert.notNull(applicationContext);
        this.applicationContext = applicationContext;
    }
    /**
     * 
     */
    public void loadApiFromSpringBeans() {
    	// ioc 所有BEan
    	// spring ioc 扫描
        String[] names = applicationContext.getBeanDefinitionNames();
        Class<?> type;
        //反谢
        for (String name : names) {
            type = applicationContext.getType(name);
            for (Method m : type.getDeclaredMethods()) {
            	// 通过反谢拿到APIMapping注解
                APIMapping apiMapping = m.getAnnotation(APIMapping.class);
                if (apiMapping != null) {
                    addApiItem(apiMapping, name, m);
                }
            }
        }
    }

    public ApiRunnable findApiRunnable(String apiName) {
        return apiMap.get(apiName);
    }

    /**
     * 
     * 添加api <br/>
     * 
     * @param apiMapping
     *            api配置
     * @param beanName
     *            beanq在spring context中的名称
     * @param method
     */
    private void addApiItem(APIMapping apiMapping, String beanName, Method method) {
    	ApiRunnable apiRun = new ApiRunnable();
        apiRun.apiName = apiMapping.value();
        apiRun.targetMethod = method;
        apiRun.targetName = beanName;
        apiRun.apiMapping=apiMapping;
        apiMap.put(apiMapping.value(), apiRun);
    }

    public ApiRunnable findApiRunnable(String apiName, String version) {
        return (ApiRunnable) apiMap.get(apiName + "_" + version);
    }

    public List<ApiRunnable> findApiRunnables(String apiName) {
        if (apiName == null) {
            throw new IllegalArgumentException("api name must not null!");
        }
        List<ApiRunnable> list = new ArrayList<ApiRunnable>(20);
        for (ApiRunnable api : apiMap.values()) {
            if (api.apiName.equals(apiName)) {
                list.add(api);
            }
        }
        return list;
    }

    public List<ApiRunnable> getAll() {
        List<ApiRunnable> list = new ArrayList<ApiRunnable>(20);
        list.addAll(apiMap.values());
        Collections.sort(list, new Comparator<ApiRunnable>() {
            public int compare(ApiRunnable o1, ApiRunnable o2) {
                return o1.getApiName().compareTo(o2.getApiName());
            }
        });
        return list;
    }

    public boolean containsApi(String apiName, String version) {
        return apiMap.containsKey(apiName + "_" + version);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    
    // 用于执行对应的API方法，
    public class ApiRunnable {
        String apiName;  //bit.api.user.getUser

        String targetName; //ioc bean 名称

        Object target; // UserServiceImpl 实例
        Method targetMethod; // 目标方法 getUser
        APIMapping apiMapping;

        public Object run(Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			if (target == null) { // spring ioc 容器里面去服务Bean 比如GoodsServiceImpl
				target = applicationContext.getBean(targetName);
            }
            return targetMethod.invoke(target, args);
        }

        public Class<?>[] getParamTypes() {
            return targetMethod.getParameterTypes();
        }

        public String getApiName() {
            return apiName;
        }

        public String getTargetName() {
            return targetName;
        }

        public Object getTarget() {
            return target;
        }

        public Method getTargetMethod() {
            return targetMethod;
        }

        public APIMapping getApiMapping() {
            return apiMapping;
        }
    }

}
