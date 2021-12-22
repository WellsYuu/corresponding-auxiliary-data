package com.tuling.springcloud.api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {
	private final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	//http://localhost:8082/order/user/getById?userId=1
    @RequestMapping("/user/getById")
    public Object getUserById(String userId) {
    	String url = "http://localhost:8081/user/getById?id=" + userId;
    	logger.debug("param userId : {}, request url : {}", userId, url);
    	Object result = restTemplate.getForEntity(url, Object.class);
        return result;
    }
    
    @RequestMapping("/user/getByIdEureka")
    public Object getUserByIdEureka(String userId) {
    	String url = "http://microservice-user/user/getById?id=" + userId;
    	Object result = restTemplate.getForEntity(url, Object.class);
        return result;
    }
    
    
}