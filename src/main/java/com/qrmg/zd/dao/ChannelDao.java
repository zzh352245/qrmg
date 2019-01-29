package com.qrmg.zd.dao;

import java.util.List;
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
	
	/**
	 * @Description: 获取渠道列表
	 * @author zz
	 * @date 2019年1月23日 下午3:56:44
	 * @return 
	 * @param
	 */
	public List<Channel> queryChannelList(Map<String, String> map);
	
	/**
	 * @Description: 获取渠道总数，用于分页
	 * @author zz
	 * @date 2019年1月23日 下午4:29:43
	 * @return 
	 * @param
	 */
	public int getCountChannel(Map<String, String> map);
	
	/**
	 * @Description: 添加渠道
	 * @author zz
	 * @date 2019年1月24日 上午9:31:17
	 * @return 
	 * @param
	 */
	public void addChannel(Channel channel);
	
	/**
	 * @Description: 修改渠道信息
	 * @author zz
	 * @date 2019年1月24日 上午9:31:17
	 * @return 
	 * @param
	 */
	public void updateChannel(Channel channel);
	
	/**
	 * @Description: 获取渠道列表不分页
	 * @author zz
	 * @date 2019年1月24日 上午10:24:03
	 * @return 
	 * @param
	 */
	public List<Channel> queryChannel(Map<String, String> map);

}
