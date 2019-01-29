package com.qrmg.zd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.frame.bean.OutputObject;
import com.qrmg.zd.model.Manager;
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
		map.put("mgAccount", userName);
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
		String mgAccount = request.getParameter("userName");
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
		Manager manager = new Manager();
		manager.setMgAccount(mgAccount);
		manager.setMgCode(mgCode);
		manager.setMgContact(mgContact);
		manager.setMgEmail(mgEmail);
		manager.setMgName(mgName);
		manager.setMgPassword(mgPassword);
		manager.setMgPhone(mgPhone);
		managerService.addAccount(manager);
		outputObj.setReturnCode("0");
		outputObj.setReturnMessage("注册成功！");
		return outputObj;
	}
	
	/**
	 * @Description: 修改账号信息 0-修改密码 其他-修改账号信息
	 * @author zz
	 * @date 2019年1月24日 下午4:28:05
	 * @return 
	 * @param
	 */
	@RequestMapping(value="/updateAccount", method=RequestMethod.GET)
	public OutputObject updateAccount(HttpServletRequest request){
		OutputObject outputObj = new OutputObject();
		String type = request.getParameter("type");
		String username = request.getParameter("userName");
		if(StringUtil.isEmpty(type) || StringUtil.isEmpty(username)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("修改类型不可为空！");
			return outputObj;
		}
		Manager manager = new Manager();
		try {
			if(StringUtils.equals("0", type)){
				String newpassword = request.getParameter("newpassword");
				String oldpassword = request.getParameter("oldpassword");
				if(StringUtil.isEmpty(newpassword) || StringUtil.isEmpty(oldpassword)
						|| StringUtil.isEmpty(username)){
					outputObj.setReturnCode("9999");
					outputObj.setReturnMessage("密码不可为空！");
					return outputObj;
				}
				Map<String, String> map = new HashMap<>();
				map.put("mgAccount", username);
				map.put("mgPassword", oldpassword);
				Map<String, String> returnMap = managerService.loginManager(map);
				if(!CollectionUtils.isEmpty(returnMap)){
					manager.setMgPassword(newpassword);
					managerService.updateAccount(manager);
				}else{
					outputObj.setReturnCode("9999");
					outputObj.setReturnMessage("原密码错误！");
				}
			}else{
				String name = request.getParameter("name");
				String phone = request.getParameter("phone");
				String code = request.getParameter("code");
				String email = request.getParameter("email");
				String contact = request.getParameter("contact");
				manager.setMgCode(code);
				manager.setMgContact(contact);
				manager.setMgEmail(email);
				manager.setMgName(name);
				manager.setMgAccount(username);
				manager.setMgPhone(phone);
				managerService.updateAccount(manager);
			}
			outputObj.setReturnCode("0");
			outputObj.setReturnMessage("修改成功！");
		} catch (Exception e) {
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("修改失败！");
		}
		return outputObj;
	}
	
	/**
	 * @Description: 获取账号列表
	 * @author zz
	 * @date 2019年1月24日 下午5:50:35
	 * @return 
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value="/queryManager", method=RequestMethod.GET)
	public OutputObject queryManager(HttpServletRequest request){
		OutputObject outputObj = new OutputObject();
		String start = request.getParameter("start");
		String length = request.getParameter("length");
		if(StringUtil.isEmpty(start) || StringUtil.isEmpty(length)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("参数格式不正确！");
			return outputObj;
		}
		try {
			Integer.parseInt(start);
			Integer.parseInt(length);
		} catch (NumberFormatException e) {
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("分页格式不正确！");
			return outputObj;
		}
		String mgCode = request.getParameter("code");
		String mgName = request.getParameter("name");
		Map<String, String> map = new HashMap<>();
		map.put("mgCode", mgCode);
		map.put("start", start);
		map.put("length", length);
		if(StringUtil.isNotEmpty(mgName)){
			map.put("mgName", "%" + mgName + "%");
		}
		outputObj = managerService.queryManagerList(map);
		return outputObj;
	}
	
	/**
	 * @Description: 账号是否被注册
	 * @author zz
	 * @date 2019年1月24日 下午5:57:57
	 * @return 
	 * @param
	 */
	@RequestMapping(value="/getIsReg", method=RequestMethod.GET)
	public OutputObject getIsReg(HttpServletRequest request){
		OutputObject outputObj = new OutputObject();
		String userName = request.getParameter("userName");
		if(StringUtil.isEmpty(userName)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("参数格式不符合规范！");
			return outputObj;
		}
		boolean isreg = managerService.loginManagerName(userName);
		outputObj.setObject(String.valueOf(isreg));
		outputObj.setReturnCode("0");
		outputObj.setReturnMessage("校验成功！");
		return outputObj;
	}
	
}
