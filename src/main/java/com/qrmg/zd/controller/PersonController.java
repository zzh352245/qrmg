package com.qrmg.zd.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.frame.bean.OutputObject;
import com.qrmg.zd.model.Person;
import com.qrmg.zd.service.impl.ChannelServiceImpl;
import com.qrmg.zd.service.impl.PersonServiceImpl;
import com.qrmg.zd.util.StringUtil;

/**
 * @Description: 用户信息
 * @ClassName: PersonController  
 * @author zz
 * @date 2019年1月22日 下午2:50:55
 */
@RestController
@RequestMapping(value="/person")
public class PersonController {

	@Resource(name="personService")
	private PersonServiceImpl personService;
	@Resource(name="channelService")
	private ChannelServiceImpl channelService;
	
	/**
	 * @Description: 二维码地址，重定向到登记页面
	 * @author zz
	 * @date 2019年2月12日 下午4:10:26
	 * @return 
	 * @param
	 */
	@RequestMapping(value="/resChannelCode", method=RequestMethod.GET)
	public String resChannelCode(HttpServletRequest request, HttpServletResponse response){
		String channelCode=request.getParameter("channelCode");
		try {
			if(StringUtil.isEmpty(channelCode)){
				response.sendError(404);
				return null;
			}
			Map<String, String> map = new HashMap<>();
			map.put("channelCode", channelCode);
			map.put("channelQrcodeType", "0");
			OutputObject outputObj = channelService.queryChannel(map);
			if(StringUtils.equals(outputObj.getReturnCode(), "0")){
				String u = request.getScheme() +"://" + request.getServerName() + ":" 
						+ request.getServerPort();
				response.sendRedirect(u+"/qrmg/module/h5Login/h5_login.html?ChannelCode="+channelCode);
			}else{
				response.sendError(404);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Description: 用户登记
	 * @author zz
	 * @date 2019年1月22日 下午2:54:01
	 * @return 
	 * @param
	 */
	@RequestMapping(value="/addPerson", method=RequestMethod.GET)
	public OutputObject addPerson(HttpServletRequest request, HttpServletResponse response){
		OutputObject output = new OutputObject();
		String name = request.getParameter("userName");
		String phone = request.getParameter("userPhone");
		String channel = request.getParameter("channelCode");
		if(StringUtil.isEmpty(channel)){
			output.setReturnCode("9999");
			output.setReturnMessage("参数不符合规范！");
			return output;
		}
		try {
			Person person = new Person();
			person.setUserName(name);
			person.setUserPhone(phone);
			person.setChannelCode(channel);
			personService.addPersonRegister(person);
		} catch (Exception e) {
			output.setReturnCode("9999");
			output.setReturnMessage("登记失败！");
		}finally{
			Map<String, String> map = new HashMap<>();
			map.put("channelCode", channel);
			Map<String, String> map1 = channelService.getChannel(map);
			if(CollectionUtils.isEmpty(map1)){
				try {
					response.sendError(404);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}else{
				output.setBean(map1);
				output.setReturnCode("0");
			}
		}
		return output;
	}
	
	/**
	 * @Description: 获取用户列表
	 * @author zz
	 * @date 2019年1月24日 上午9:42:27
	 * @return 
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value="/queryPersonList", method=RequestMethod.GET)
	public OutputObject queryPersonList(HttpServletRequest request){
		OutputObject output = new OutputObject();
		String name = request.getParameter("userName");
		String channel = request.getParameter("channelCode");
		Map<String, String> map = new HashMap<>();
		map.put("channelCode", channel);
		if(StringUtil.isNotEmpty(name)){
			map.put("userName", "%" + name + "%");
		}
		output = personService.queryPerson(map);
		return output;
	}
	
}
