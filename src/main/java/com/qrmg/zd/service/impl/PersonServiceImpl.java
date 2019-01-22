package com.qrmg.zd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
