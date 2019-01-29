package com.qrmg.zd.dao;

import java.util.List;
import java.util.Map;

import com.qrmg.zd.model.Person;

/**
 * @Description: 用户登记
 * @ClassName: PersonDao  
 * @author zz
 * @date 2019年1月22日 上午11:05:57
 */
public interface PersonDao {

	/**
	 * @Description: 用户登记
	 * @author zz
	 * @date 2019年1月22日 下午2:46:01
	 * @return 
	 * @param
	 */
	public void addPersonRegister(Person person);
	
	/**
	 * @Description: 查询登记用户列表
	 * @author zz
	 * @date 2019年1月24日 上午9:35:37
	 * @return 
	 * @param
	 */
	public List<Person> queryPersonList(Map<String, String> map);
	
	/**
	 * @Description: 用户总数
	 * @author zz
	 * @date 2019年1月24日 上午9:39:22
	 * @return 
	 * @param
	 */
	public int getCountPerson(Map<String, String> map);
	
}
