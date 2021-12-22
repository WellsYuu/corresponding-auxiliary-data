package com.tuling.springcloud.api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuling.springcloud.bean.User;
import com.tuling.springcloud.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
    @RequestMapping("/getById")
    public User getUserById(String id) {
    	logger.debug("param id : {}", id);
        return this.userService.findById(Integer.parseInt(id));
    }

    
}