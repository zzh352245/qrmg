package com.qrmg.zd.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Description: 渠道
 * @ClassName: Channel  
 * @author zz
 * @date 2019年1月22日 上午10:34:43
 */
public class Channel {

	private int id;
	private String channelLevel;
	private String channelParentCode;
	private String channelCode;
	private String channelName;
	private String channelQrcode;
	private String channelQrcodeType;
	private String channelLinkUrl;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:dd", timezone = "GMT+8")
	private Date createTime;
	private String createMg;
	private String updateMg;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:dd", timezone = "GMT+8")
	private Date updateTime;
	public String getChannelQrcodeType() {
		return channelQrcodeType;
	}
	public void setChannelQrcodeType(String channelQrcodeType) {
		this.channelQrcodeType = channelQrcodeType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChannelLevel() {
		return channelLevel;
	}
	public void setChannelLevel(String channelLevel) {
		this.channelLevel = channelLevel;
	}
	public String getChannelParentCode() {
		return channelParentCode;
	}
	public void setChannelParentCode(String channelParentCode) {
		this.channelParentCode = channelParentCode;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelQrcode() {
		return channelQrcode;
	}
	public void setChannelQrcode(String channelQrcode) {
		this.channelQrcode = channelQrcode;
	}
	public String getChannelLinkUrl() {
		return channelLinkUrl;
	}
	public void setChannelLinkUrl(String channelLinkUrl) {
		this.channelLinkUrl = channelLinkUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateMg() {
		return createMg;
	}
	public void setCreateMg(String createMg) {
		this.createMg = createMg;
	}
	public String getUpdateMg() {
		return updateMg;
	}
	public void setUpdateMg(String updateMg) {
		this.updateMg = updateMg;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
