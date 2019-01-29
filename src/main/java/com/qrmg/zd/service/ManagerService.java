package com.qrmg.zd.service;

import java.util.Map;

import com.ai.frame.bean.OutputObject;
import com.qrmg.zd.model.Manager;

public interface ManagerService {

	/**
	 * @Description: 账号登录
	 * @author zz
	 * @date 2019年1月22日 下午3:19:56
	 * @return 
	 * @param
	 */
	Map<String, String> loginManager(Map<String, String> map);
	
	/**
	 * @Description: 添加账号
	 * @author zz
	 * @date 2019年1月22日 下午8:03:28
	 * @return 
	 * @param
	 */
	void addAccount(Manager manager);
	
	/**
	 * @Description: 修改账号信息
	 * @author zz
	 * @date 2019年1月24日 下午3:51:21
	 * @return 
	 * @param
	 */
	void updateAccount(Manager manager);
	
	/**
	 * @Description: 获取账号列表
	 * @author zz
	 * @date 2019年1月24日 下午5:44:09
	 * @return 
	 * @param
	 */
	OutputObject queryManagerList(Map<String, String> map);
	
	boolean loginManagerName(String userName);
	
}
