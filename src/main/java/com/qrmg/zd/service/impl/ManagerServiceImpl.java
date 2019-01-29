package com.qrmg.zd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ai.frame.bean.OutputObject;
import com.qrmg.zd.dao.ManagerDao;
import com.qrmg.zd.model.Manager;
import com.qrmg.zd.service.ManagerService;
import com.qrmg.zd.util.MD5Util;
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
		Manager manager = managerDao.loginManagerName(map);
		if(manager == null || StringUtil.isEmpty(manager.getMgPassword())){
			return returnMap;
		}
		boolean islogin = MD5Util.verify(map.get("mgPassword"), manager.getMgPassword());
		if(islogin){
			returnMap.put("mgCode", manager.getMgCode());
			returnMap.put("mgName", manager.getMgName());
		}
		return returnMap;
	}

	/**
	 * @Description: 添加账号
	 * @author zz
	 * @date 2019年1月22日 下午8:03:28
	 * @return 
	 * @param
	 */
	@Override
	public void addAccount(Manager manager) {
		Map<String, String> map = MD5Util.generate(manager.getMgPassword());
		manager.setMgPassword(map.get("p"));
		manager.setMgSalt(map.get("s"));
		managerDao.addAccount(manager);
	}

	/**
	 * @Description: 修改账号信息
	 * @author zz
	 * @date 2019年1月24日 下午3:51:21
	 * @return 
	 * @param
	 */
	public void updateAccount(Manager manager) {
		if(StringUtil.isNotEmpty(manager.getMgPassword())){
			//修改密码
			Map<String, String> map = MD5Util.generate(manager.getMgPassword());
			manager.setMgPassword(map.get("p"));
			manager.setMgSalt(map.get("s"));
		}
		managerDao.updateAccount(manager);
	}

	/**
	 * @Description: 获取账号列表
	 * @author zz
	 * @date 2019年1月24日 下午5:44:09
	 * @return 
	 * @param
	 */
	@Override
	public OutputObject queryManagerList(Map<String, String> map) {
		OutputObject outputObj = new OutputObject();
		int total = managerDao.getCountManager(map);
		if(total < 1){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("没有获取到对应的账号列表！");
			return outputObj;
		}
		List<Manager> list = managerDao.queryManagerList(map);
		if(CollectionUtils.isEmpty(list)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("没有获取到对应的账号列表！");
			return outputObj;
		}
		outputObj.getBean().put("total", String.valueOf(total));
		outputObj.setObject(list);
		outputObj.setReturnCode("0");
		outputObj.setReturnMessage("账号列表获取成功！");
		return outputObj;
	}

	@Override
	public boolean loginManagerName(String userName) {
		Map<String, String> map = new HashMap<>();
		map.put("mgAccount", userName);
		Manager manager = managerDao.loginManagerName(map);
		if(StringUtil.isEmpty(manager.getMgPassword())){
			return true;
		}
		return false;
	}
	
}
