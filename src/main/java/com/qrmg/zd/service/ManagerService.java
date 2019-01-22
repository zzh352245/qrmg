package com.qrmg.zd.service;

import java.util.Map;

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
	void addAccount(Map<String, String> map);
	
}
