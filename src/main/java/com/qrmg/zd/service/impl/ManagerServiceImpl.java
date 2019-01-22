package com.qrmg.zd.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qrmg.zd.dao.ManagerDao;
import com.qrmg.zd.model.Manager;
import com.qrmg.zd.service.ManagerService;
import com.qrmg.zd.util.StringUtil;

/**
 * @Description: 账号管理
 * @ClassName: ManagerServiceImpl  
 * @author zz
 * @date 2019年1月22日 下午3:21:28
 */
@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	private ManagerDao managerDao;
	
	@Override
	public Map<String, String> loginManager(Map<String, String> map){
		Map<String, String> returnMap = new HashMap<>();
		Manager manager = managerDao.loginManager(map);
		if(StringUtil.isNotEmpty(manager.getMgCode())){
			returnMap.put("mgCode", manager.getMgCode());
			returnMap.put("mgName", manager.getMgName());
		}
		return returnMap;
	}
	
}
