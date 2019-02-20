package com.qrmg.zd.service;

import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.ai.frame.bean.OutputObject;
import com.qrmg.zd.model.Person;

public interface PersonService {

	/**
	 * @Description: 用户登记
	 * @author zz
	 * @date 2019年1月22日 下午2:48:48
	 * @return 
	 * @param
	 */
	void addPersonRegister(Person person);
	
	/**
	 * @Description: 获取登记用户列表
	 * @author zz
	 * @date 2019年1月24日 上午9:37:46
	 * @return 
	 * @param
	 */
	OutputObject queryPerson(Map<String, String> map);
	
	/**
	 * @Description: 导出用户登记列表
	 * @author zz
	 * @date 2019年2月20日 下午6:20:56
	 * @return 
	 * @param
	 */
	void export(String[] titles, ServletOutputStream out, Map<String, String> map);
	
}
