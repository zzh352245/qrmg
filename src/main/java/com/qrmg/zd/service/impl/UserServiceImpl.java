package com.qrmg.zd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qrmg.zd.dao.UserDao;
import com.qrmg.zd.model.User;
import com.qrmg.zd.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired  
    private UserDao userDao;  

    public User selectUserById(Integer userId) {  
        return userDao.selectUserById(userId);  
    }  
}