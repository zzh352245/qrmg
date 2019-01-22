package com.qrmg.zd.dao;

import com.qrmg.zd.model.User;

public interface UserDao {
	/**
     * @param userId
     * @return User
     */
    public User selectUserById(Integer userId);
    
}
