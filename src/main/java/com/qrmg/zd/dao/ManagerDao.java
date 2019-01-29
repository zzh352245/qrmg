package com.qrmg.zd.dao;

import java.util.List;
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
	public void addAccount(Manager manager);
	
	/**
	 * @Description: 修改账号信息
	 * @author zz
	 * @date 2019年1月24日 下午3:51:21
	 * @return 
	 * @param
	 */
	public void updateAccount(Manager manager);
	
	public Manager loginManagerName(Map<String, String> map);
	
	public List<Manager> queryManagerList(Map<String, String> map);
	
	public int getCountManager(Map<String, String> map);
	
}
