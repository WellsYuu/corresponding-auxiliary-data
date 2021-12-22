package com.cbt.agent.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.core.AgentFinal;

/**
 * 工具类
 *
 * @since 0.1.0
 */
public class JsonUtils implements AgentFinal {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    public static final String JSON_IMPL_NAME = "com.alibaba.fastjson.JSON";
    public static final String JSON_IMPL_CLASS = "com/alibaba/fastjson/JSON.class";

    private volatile static Map<ClassLoader, FastJsonAdapter> jsonLoad = new HashMap<>();
    private volatile static Map<ClassLoader, Exception> jsonLoadError = new HashMap<>();



    public static String toJson(Object obj, ClassLoader loader) {
        // TODO: 16/11/9 需要 重新考虑
        String json = null;
        if (!jsonLoad.containsKey(loader) && !jsonLoadError.containsKey(loader)) {
            synchronized (jsonLoad) { // 双重判断
                if (!jsonLoad.containsKey(loader) && !jsonLoadError.containsKey(loader)) {
                    // TODO: 16/11/9 初始json解析器
                    try {
                        jsonLoad.put(loader, buildFastJsonAdapter(loader));
                    } catch (Exception e) {
                        jsonLoadError.put(loader, e);
                        e.printStackTrace();
                    }
                }
            }
        }
        if (jsonLoad.containsKey(loader)) {
            FastJsonAdapter adapter = jsonLoad.get(loader);
            json = adapter.toJsonString(obj);
        } else if (jsonLoadError.containsKey(loader)) {
            throw new RuntimeException("json util load faile " + jsonLoadError.get(loader).getMessage());
        }

        return json;
    }

    private static FastJsonAdapter buildFastJsonAdapter(ClassLoader loader) throws ClassNotFoundException {
        URL re = loader.getResource(JSON_IMPL_CLASS);
        FastJsonAdapter adapter;
        if (re == null) {
            // attached fastJson.jar
            if (!LOCAL_CONFIG.containsKey(FAST_JSON_URL)) { // 该路径值由系统自动配置,非用户设置
                throw new RuntimeException("Is not set " + FAST_JSON_URL);
            }
            try {
                URL url = new URL(LOCAL_CONFIG.getProperty(FAST_JSON_URL));
                URLClassLoader newLoader = new URLClassLoader(new URL[] { url }, loader);
                // JarUtil.addUrl(url, loader);
                Class<?> fastClass = newLoader.loadClass(JSON_IMPL_NAME);
                adapter = new FastJsonAdapter(fastClass, newLoader);
            } catch (MalformedURLException e) {
                throw new RuntimeException("set " + FAST_JSON_URL + " invalid! " + e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException("init FastJson load fail ! " + e.getMessage());
            }
        } else {
            Class<?> fastClass = loader.loadClass(JSON_IMPL_NAME);
            adapter = new FastJsonAdapter(fastClass, null);
        }
        return adapter;
    }

    /*
     * public static <T> T parse(String json, Class<T> type) { // try { //
     * return JSON.parseObject(json, type); // } catch (IllegalStateException e)
     * { // logger.error("解析JSON失败 (" + json + "):" + e.getMessage()); // return
     * null; // } }
     */

    public static String getStackTrace(Throwable throwable) {
        Assert.notNull(throwable);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);
        throwable.printStackTrace(printStream);
        return outStream.toString();
    }

    private static class FastJsonAdapter {
        private Class<?> cla;
        private Method toJsonStringMethod;
        private ClassLoader newLoader;

        private FastJsonAdapter(Class<?> cla, ClassLoader newLoader) {
            try {
                this.cla = cla;
                toJsonStringMethod = cla.getDeclaredMethod("toJSONString", Object.class);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("fastJson not found  toJSONString(Object object) Method ! check fastJson version", e);
            }
            this.newLoader = newLoader;
        }


        private String toJsonString(Object obje) {
            try {
                ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
                try {
                    if (newLoader != null)
                        Thread.currentThread().setContextClassLoader(newLoader);
                    return (String) toJsonStringMethod.invoke(cla, obje);
                } finally {
                    if (newLoader != null)
                        Thread.currentThread().setContextClassLoader(oldLoader);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
