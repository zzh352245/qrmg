package com.qrmg.zd.service;

import com.qrmg.zd.model.User;

public interface UserService {
    User selectUserById(Integer userId);  
}