package com.qrmg.zd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ai.frame.bean.OutputObject;
import com.qrmg.zd.dao.PersonDao;
import com.qrmg.zd.model.Person;
import com.qrmg.zd.service.PersonService;

/**
 * @Description: 用户管理
 * @ClassName: PersonServiceImpl  
 * @author zz
 * @date 2019年1月22日 下午2:47:38
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {

	@Autowired  
    private PersonDao personDao; 
	
	@Override
	public void addPersonRegister(Person person) {
		personDao.addPersonRegister(person);
	}

	/**
	 * 获取登记用户列表
	 */
	@Override
	public OutputObject queryPerson(Map<String, String> map) {
		OutputObject outputObject = new OutputObject();
		int total = personDao.getCountPerson(map);
		if(total < 1){
			outputObject.setReturnCode("9999");
			outputObject.setReturnMessage("没有获取到对应的用户列表！");
			return outputObject;
		}
		List<Person> list = personDao.queryPersonList(map);
		if(CollectionUtils.isEmpty(list)){
			outputObject.setReturnCode("9999");
			outputObject.setReturnMessage("没有获取到对应的用户列表！");
			return outputObject;
		}
		outputObject.getBean().put("total", String.valueOf(total));
		outputObject.setObject(list);
		outputObject.setReturnCode("0");
		outputObject.setReturnMessage("用户列表获取成功！");
		return outputObject;
	}

}
