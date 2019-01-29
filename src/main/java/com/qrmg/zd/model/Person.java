package com.qrmg.zd.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @Description: 用户登记
 * @ClassName: Person  
 * @author zz
 * @date 2019年1月22日 上午11:02:36
 */
public class Person {

	private int id;
	private String userName;
	private String userPhone;
	private String channelCode;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:dd", timezone = "GMT+8")
	private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
