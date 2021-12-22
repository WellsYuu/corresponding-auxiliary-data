package com.tuling.springboot.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuling.springboot.autoconfiguration.HelloService;

@Controller
public class HelloController {
	//private final Logger logger = LoggerFactory.getLogger(HelloController.class);
	
	private final Logger logger = Logger.getLogger(HelloController.class);
	
	@Value("${teacher.name}")
	private String teacherName;
	
	@Value("${teacher.info}")
	private String info;
	
	@Autowired
	private HelloService helloService;
	
	@RequestMapping("/testHello")
	@ResponseBody
	public Object testHello(Map<String, String> map){
		logger.info(this.helloService.sayHello());
		return this.helloService.sayHello();
	}

	@RequestMapping("/")
	@ResponseBody
	public String index(){
//		logger.debug("test {}"," -----debug test");
//		logger.info("test {}"," -----info test");
		logger.debug("log4j debug level");
		logger.info("log4j info level");
		return "Hello World !" + this.teacherName + this.info;
	}
	
	@RequestMapping("/testThymeleaf")
	public String testThymeleaf(ModelMap map){
		map.put("name", "zhangsan1");
		//对应src/main/resources/templates/testThymeleaf.html
		return "testThymeleaf";
	}
	
	@RequestMapping("/testFreeMarker")
	public String testFreeMarker(Map<String, String> map){
		map.put("hello", "world!!!");
		//对应src/main/resources/templates/hello.ftl
		return "hello";
	}
	
	@RequestMapping("/exception")
	public String hello() throws Exception {
		throw new Exception("发生错误");
	}
}
