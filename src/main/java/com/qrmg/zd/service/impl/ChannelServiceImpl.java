package com.qrmg.zd.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
