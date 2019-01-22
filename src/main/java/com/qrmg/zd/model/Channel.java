package com.qrmg.zd.model;

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
	private String createTime;
	private String createMg;
	private String updateMg;
	private String updateTime;
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
	public String getCreateTime() {
		return createTime.replace(".0", "");
	}
	public void setCreateTime(String createTime) {
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
	public String getUpdateTime() {
		return updateTime.replace(".0", "");
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
