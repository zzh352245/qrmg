package com.qrmg.zd.dao;

import java.util.Map;

import com.qrmg.zd.model.Channel;

/**
 * @Description: 渠道管理
 * @ClassName: ChannelDao  
 * @author zz
 * @date 2019年1月22日 下午2:45:10
 */
public interface ChannelDao {
	
	/**
	 * @Description: 根据二级渠道编码获取对应一级渠道信息
	 * @author zz
	 * @date 2019年1月22日 下午4:42:02
	 * @return 
	 * @param
	 */
	public Channel getOneChannelByTwoCode(Map<String, String> map);

}
