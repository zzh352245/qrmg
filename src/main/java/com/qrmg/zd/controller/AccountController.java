package com.qrmg.zd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ai.frame.bean.OutputObject;
import com.qrmg.zd.service.impl.ManagerServiceImpl;
import com.qrmg.zd.util.StringUtil;

/**
 * @Description: 管理平台账号管理
 * @ClassName: AccountController  
 * @author zz
 * @date 2019年1月22日 下午7:33:44
 */
@RestController
@RequestMapping(value="/front/mana/account")
public class AccountController {

	@Resource(name="managerService")
	private ManagerServiceImpl managerService;
	
//	@RequestMapping(value="/login", method=RequestMethod.POST, consumes="application/json")
	@RequestMapping(value="/login", method=RequestMethod.GET)
//	public OutputObject login(@RequestBody Map<String, Object> params, HttpServletRequest request){
	public OutputObject login(HttpServletRequest request){
		OutputObject outputObj = new OutputObject();
		String userName = request.getParameter("userName");
		String password = request.getParameter("userPass");
		String vcode = request.getParameter("vcode");
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
		if(StringUtil.isEmpty(vcode) || StringUtil.isEmpty(String.valueOf(request.getSession().getAttribute("LOGIN_CHECK_CODE")))){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("验证码已过期，请刷新重试！");
			return outputObj;
		}
		if(!StringUtils.equals(vcode.toLowerCase(), String.valueOf(request.getSession().getAttribute("LOGIN_CHECK_CODE")))){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("验证码错误，请重新输入！");
			return outputObj;
		}
		request.getSession().removeAttribute("LOGIN_CHECK_CODE");
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
	
	/**
	 * @Description: 注册账号
	 * @author zz
	 * @date 2019年1月22日 下午8:08:07
	 * @return 
	 * @param
	 */
	@RequestMapping(value="/addAccount", method=RequestMethod.GET)
	public OutputObject addAccount(HttpServletRequest request){
		OutputObject outputObj = new OutputObject();
		String mgName = request.getParameter("name");
		String mgAccount = request.getParameter("loginName");
		String mgPassword = request.getParameter("password");
		String mgPhone = request.getParameter("phone");
		String mgEmail = request.getParameter("email");
		String mgContact = request.getParameter("contact");
		String mgCode = request.getParameter("level");
		if(StringUtil.isEmpty(mgName) || StringUtil.isEmpty(mgAccount) || StringUtil.isEmpty(mgPassword)
				|| StringUtil.isEmpty(mgPhone) || StringUtil.isEmpty(mgCode)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("请完善账号信息！");
			return outputObj;
		}
		Map<String, String> map = new HashMap<>();
		map.put("mgName", mgName);
		map.put("mgAccount", mgAccount);
		map.put("mgPassword", mgPassword);
		map.put("mgPhone", mgPhone);
		map.put("mgEmail", mgEmail);
		map.put("mgContact", mgContact);
		map.put("mgCode", mgCode);
		managerService.addAccount(map);
		outputObj.setReturnCode("0");
		outputObj.setReturnMessage("注册成功！");
		return outputObj;
	}
	
}
