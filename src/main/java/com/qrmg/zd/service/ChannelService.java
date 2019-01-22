package com.qrmg.zd.service;

import java.util.Map;

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
	
}
