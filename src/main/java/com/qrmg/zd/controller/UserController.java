package com.qrmg.zd.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qrmg.zd.model.User;
import com.qrmg.zd.service.impl.UserServiceImpl;

@RestController
@RequestMapping(value="/qrmg/user")
public class UserController {  

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    @Resource(name="userService")
    private UserServiceImpl userService; 

    @RequestMapping(value="/getUser", method=RequestMethod.GET)    
    public User getUser(){      
        User user = userService.selectUserById(1);
        logger.info(String.valueOf(user));
        return user; 
    }    
}  