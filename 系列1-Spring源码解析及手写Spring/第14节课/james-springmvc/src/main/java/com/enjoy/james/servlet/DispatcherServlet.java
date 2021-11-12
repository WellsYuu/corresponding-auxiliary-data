package com.enjoy.james.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enjoy.james.annotation.EnjoyController;
import com.enjoy.james.annotation.EnjoyQualifier;
import com.enjoy.james.annotation.EnjoyRequestMapping;
import com.enjoy.james.annotation.EnjoyService;
import com.enjoy.james.controller.JamesController;
import com.enjoy.james.handlerAdapter.HandlerAdapterService;

/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	List<String> classNames = new ArrayList<String>();

	Map<String, Object> beans = new HashMap<String, Object>();

	Map<String, Object> handlerMap = new HashMap<String, Object>();

	Properties prop = null;

	private static String HANDLERADAPTER = "jamesHandlerAdapter";

	/**
	 * Default constructor.
	 */
	public DispatcherServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// 1、我们要根据一个基本包进行扫描，扫描里面的子包以及子包下的类
		scanPackage("com.enjoy");

		for (String classname : classNames) {
			System.out.println(classname);
		}

		// 2、我们肯定是要把扫描出来的类进行实例化
		instance();
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

		// 3、依赖注入，把service层的实例注入到controller
		ioc();

		// 4、建立一个path与method的映射关系
		HandlerMapping();
		for (Map.Entry<String, Object> entry : handlerMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		/*
		 * InputStream is = this.getClass()
		 * .getResourceAsStream("/config/properties/spring.properties"); prop =
		 * new Properties(); try { prop.load(is); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取到请求路径   /james-springmvc/james/query
		String uri = request.getRequestURI();

		String context = request.getContextPath();
		//将  "/james-springmvc/james/query"  去掉"/james-springmvc"
		String path = uri.replace(context, "");
		//根据请求路径来获取要执行的方法 
		Method method = (Method) handlerMap.get(path);
		//拿到控制类
		JamesController instance = (JamesController) beans.get("/" + path.split("/")[1]);
		//处理器
		HandlerAdapterService ha = (HandlerAdapterService) beans.get(HANDLERADAPTER);

		
		/*
		 * @RequestMapping("/order")
		 * order(@RequestBody String params, @RequestHeader @RequestParam String param1){//参数有多种类型接收方式
		 * }
		 */
		
		
		
		
		Object[] args = ha.hand(request, response, method, beans);

		try {
			method.invoke(instance, args);
			// method.invoke(instance, new
			// Object[]{request,response,null});//拿参数
			
			/*如果有多个参数类型,就得这样写了(可用策略模式,省去以下代码)
			 * if(ParamType == HttpServletRequest){
				
			}else if(ParamType == @RquestHeader){
				
			}else
			*用策略模式实现(把粒度控制得更细),新建 JamesHandlerAdapter
			*/
			
			
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void HandlerMapping() {
		if (beans.entrySet().size() <= 0) {
			System.out.println("没有类的实例化！");
			return;
		}

		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			Object instance = entry.getValue();

			Class<?> clazz = instance.getClass();
			// 拿所有Controller的类
			if (clazz.isAnnotationPresent(EnjoyController.class)) {
				//@com.enjoy.james.annotation.EnjoyRequestMapping(value=/james)
				EnjoyRequestMapping requestMapping = (EnjoyRequestMapping) clazz
						.getAnnotation(EnjoyRequestMapping.class);
				// 获取Controller类上面的EnjoyRequestMapping注解里的请求路径
				String classPath = requestMapping.value();
				// 获取控制类里的所有方法
				Method[] methods = clazz.getMethods();
				// 获取方法上的EnjoyRequestMapping设置的路径，与方法名建立映射关系
				for (Method method : methods) {
					//判断哪些方法上使用EnjoyRequestMapping路径注解
					if (method.isAnnotationPresent(EnjoyRequestMapping.class)) {
						//@com.enjoy.james.annotation.EnjoyRequestMapping(value=/query)
						EnjoyRequestMapping methodrm = (EnjoyRequestMapping) method
								.getAnnotation(EnjoyRequestMapping.class);
						String methodPath = methodrm.value();
						// 把方法上与路径建立映射关系( /james/query--->public void com.enjoy.james.controller.JamesController.query )
						handlerMap.put(classPath + methodPath, method);
					} else {
						continue;
					}
				}
			}
		}
	}

	// 初始化IOC容器
	private void ioc() {

		if (beans.entrySet().size() <= 0) {
			System.out.println("没有类的实例化！");
			return;
		}
		//将实例化好的bean遍历,
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			Object instance = entry.getValue();//获取bean实例

			Class<?> clazz = instance.getClass();//获取类,用来判断类里声明了哪些注解(主要是针对控制类里的判断,比如使用了@Autowired  @Qualifier,对这些注解进行解析)
			//判断该类是否使用了EnjoyController注解
			if (clazz.isAnnotationPresent(EnjoyController.class)) {
				Field[] fields = clazz.getDeclaredFields();// 拿到类里面的属性
				// 判断是否声明了自动装配（依赖注入）注解，比如@Autrowired @Qualifier
				for (Field field : fields) {
					if (field.isAnnotationPresent(EnjoyQualifier.class)) {
						EnjoyQualifier qualifier = (EnjoyQualifier) field.getAnnotation(EnjoyQualifier.class);
						//拿到@EnjoyQualifier("JamesServiceImpl")里的指定要注入的bean名字"JamesServiceImpl"
						String value = qualifier.value();

						field.setAccessible(true);
						try {
							// 从MAP容器中获取"JamesServiceImpl"对应的bean,并注入实例控制层bean,解决依赖注入
							field.set(instance, beans.get(value));
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						continue;
					}
				}
			} else {
				continue;
			}
		}
	}

	private void instance() {
		if (classNames.size() <= 0) {
			System.out.println("包扫描失败！");
			return;
		}
		//遍历扫描到的class文件,将需要实例化的类(加了注解的类)进行反射创建对象(像注解就不需要实例化)
		for (String className : classNames) {
			// com.enjoy.james.service.impl.JamesServiceImpl.class
			String cn = className.replace(".class", "");

			try {
				Class<?> clazz = Class.forName(cn);//拿到class类,用来实例化
				// 将扫描到的类，获取类名，并判断是否标记了EnjoyController注解
				if (clazz.isAnnotationPresent(EnjoyController.class)) {
					EnjoyController controller = (EnjoyController) clazz.getAnnotation(EnjoyController.class);
					Object instance = clazz.newInstance();
					//获取对应的请求路径"/james"
					EnjoyRequestMapping requestMapping = (EnjoyRequestMapping) clazz
							.getAnnotation(EnjoyRequestMapping.class);
					String rmvalue = requestMapping.value();//得到"/james"请求路径
					//用路径做为key,对应value为实例化对象
					beans.put(rmvalue, instance);
				} else if (clazz.isAnnotationPresent(EnjoyService.class)) {
					//获取当前clazz类的注解(通过这个注解可得到当前service的id)  @com.enjoy.james.annotation.EnjoyService(value=JamesServiceImpl)
					EnjoyService service = (EnjoyService) clazz.getAnnotation(EnjoyService.class);
					Object instance = clazz.newInstance();
					//put(JamesServiceImpl,instance)
					beans.put(service.value(), instance);
				} else {
					continue;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void scanPackage(String basePackage) {
		//扫描编译好的类路径下所有的类
		URL url = this.getClass().getClassLoader().getResource("/" + replaceTo(basePackage));

		String fileStr = url.getFile();

		File file = new File(fileStr);
		//拿到所有类com.enjoy下的james文件夹
		String[] filesStr = file.list();

		for (String path : filesStr) {
			File filePath = new File(fileStr + path);//扫描com.enjoy.james下的所有class类

			//递归调用扫描,如果是路径,继续扫描
			if (filePath.isDirectory()) {
				// com.enjoy.james
				scanPackage(basePackage + "." + path);
			} else {
				classNames.add(basePackage + "." + filePath.getName());//如果是class文件则加入List集合(待生成bean)
			}
		}
	}

	private String replaceTo(String basePackage) {
		return basePackage.replaceAll("\\.", "/");
	}

}
