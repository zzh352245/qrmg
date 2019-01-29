package com.qrmg.zd.service;

import java.util.Map;

import com.ai.frame.bean.OutputObject;
import com.qrmg.zd.model.Channel;

/**
 * @Description: 渠道管理
 * @ClassName: ChannelService  
 * @author zz
 * @date 2019年1月22日 下午4:43:16
 */
public interface ChannelService {

	/**
	 * @Description: 根据二级渠道编码获取一级渠道信息
	 * @author zz
	 * @date 2019年1月22日 下午4:43:59
	 * @return 
	 * @param
	 */
	Map<String, String> getChannel(Map<String, String> map);
	
	/**
	 * @Description: 获取渠道列表
	 * @author zz
	 * @date 2019年1月23日 下午3:56:44
	 * @return 
	 * @param
	 */
	OutputObject queryChannelList(Map<String, String> map);
	
	/**
	 * @Description: 获取渠道列表不分页
	 * @author zz
	 * @date 2019年1月24日 上午10:24:03
	 * @return 
	 * @param
	 */
	OutputObject queryChannel(Map<String, String> map);
	
	/**
	 * @Description: 添加渠道
	 * @author zz
	 * @date 2019年1月24日 上午9:31:17
	 * @return 
	 * @param
	 */
	void addChannel(Channel channel);
	
	/**
	 * @Description: 修改渠道信息
	 * @author zz
	 * @date 2019年1月24日 上午9:31:17
	 * @return 
	 * @param
	 */
	void updateChannel(Channel channel);
	
}
