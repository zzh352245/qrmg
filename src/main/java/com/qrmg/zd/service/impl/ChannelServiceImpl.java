package com.qrmg.zd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ai.frame.bean.OutputObject;
import com.qrmg.zd.dao.ChannelDao;
import com.qrmg.zd.model.Channel;
import com.qrmg.zd.service.ChannelService;
import com.qrmg.zd.util.StringUtil;
/**
 * @Description: 渠道管理
 * @ClassName: ChannelServiceImpl  
 * @author zz
 * @date 2019年1月22日 下午4:44:40
 */
@Service("channelService")
public class ChannelServiceImpl implements ChannelService {

	@Autowired  
    private ChannelDao channelDao;
	
	@Override
	public Map<String, String> getChannel(Map<String, String> map) {
		Channel channel = channelDao.getOneChannelByTwoCode(map);
		Map<String, String> returnMap = new HashMap<>();
		if(StringUtil.isNotEmpty(channel.getChannelLinkUrl())){
			returnMap.put("url", channel.getChannelLinkUrl());
		}
		return returnMap;
	}

	/**
	 * @Description: 获取渠道列表
	 * @author zz
	 * @date 2019年1月23日 下午3:56:44
	 * @return 
	 * @param
	 */
	@Override
	public OutputObject queryChannelList(Map<String, String> map) {
		OutputObject outputObj = new OutputObject();
		int count = channelDao.getCountChannel(map);
		if(count < 1){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("没有获取到对应的渠道列表！");
			return outputObj;
		}
		List<Channel> list = channelDao.queryChannelList(map);
		if(CollectionUtils.isEmpty(list)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("没有获取到对应的渠道列表！");
			return outputObj;
		}
		outputObj.getBean().put("total", String.valueOf(count));
		outputObj.setObject(list);
		outputObj.setReturnCode("0");
		outputObj.setReturnMessage("渠道列表获取成功！");
		return outputObj;
	}

	/**
	 * @Description: 获取渠道列表不分页
	 * @author zz
	 * @date 2019年1月24日 上午10:24:03
	 * @return 
	 * @param
	 */
	@Override
	public OutputObject queryChannel(Map<String, String> map) {
		OutputObject outputObj = new OutputObject();
		List<Channel> list = channelDao.queryChannel(map);
		if(CollectionUtils.isEmpty(list)){
			outputObj.setReturnCode("9999");
			outputObj.setReturnMessage("没有获取到对应的渠道列表！");
			return outputObj;
		}
		outputObj.setObject(list);
		outputObj.setReturnCode("0");
		outputObj.setReturnMessage("渠道列表获取成功！");
		return outputObj;
	}
	
	/**
	 * @Description: 修改渠道信息
	 * @author zz
	 * @date 2019年1月24日 上午9:31:17
	 * @return 
	 * @param
	 */
	@Override
	public void updateChannel(Channel channel){
		channelDao.updateChannel(channel);
	}
	
	/**
	 * @Description: 添加渠道
	 * @author zz
	 * @date 2019年1月24日 上午9:31:17
	 * @return 
	 * @param
	 */
	@Override
	public void addChannel(Channel channel){
		channelDao.addChannel(channel);
	}

}
