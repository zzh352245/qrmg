package com.qrmg.zd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ai.frame.bean.OutputObject;
import com.qrmg.zd.service.impl.ManagerServiceImpl;
import com.qrmg.zd.util.StringUtil;

/**
 * @Description: 后台管理
 * @ClassName: ManagerController  
 * @author zz
 * @date 2019年1月22日 下午3:24:07
 */
@RestController
@RequestMapping(value="/front/mana")
public class ManagerController {

	@Resource(name="managerService")
	private ManagerServiceImpl managerService;
	
//	@RequestMapping(value="/login", method=RequestMethod.POST, consumes="application/json")
	@RequestMapping(value="/login", method=RequestMethod.GET)
//	public OutputObject login(@RequestBody Map<String, Object> params, HttpServletRequest request){
	public OutputObject login(HttpServletRequest request){
		OutputObject outputObj = new OutputObject();
		String userName = request.getParameter("userName");
		String password = request.getParameter("userPass");
		if(StringUtil.isEmpty(userName)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("用户名不能为空！");
			return outputObj;
		}
		if(StringUtil.isEmpty(password)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("密码不能为空！");
			return outputObj;
		}
		Map<String, String> map = new HashMap<>();
		map.put("mgName", userName);
		map.put("mgPassword", password);
		Map<String, String> returnMap = managerService.loginManager(map);
		if(!CollectionUtils.isEmpty(returnMap)){
			outputObj.setBean(returnMap);
			outputObj.setReturnCode("0");
			outputObj.setReturnMessage("登录成功！");
		}else{
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("用户名或密码错误！");
		}
		return outputObj;
	}
	
}
