package com.qrmg.zd.dao;

import java.util.Map;

import com.qrmg.zd.model.Manager;

/**
 * @Description: 账号管理
 * @ClassName: Manager  
 * @author zz
 * @date 2019年1月22日 下午2:33:58
 */
public interface ManagerDao {

	/**
	 * @Description: 账号登录
	 * @author zz
	 * @date 2019年1月22日 下午3:19:56
	 * @return 
	 * @param
	 */
	public Manager loginManager(Map<String, String> map);
	
	/**
	 * @Description: 添加账号
	 * @author zz
	 * @date 2019年1月22日 下午8:03:28
	 * @return 
	 * @param
	 */
	public void addAccount(Map<String, String> map);
	
}
