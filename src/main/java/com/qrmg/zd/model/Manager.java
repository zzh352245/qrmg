package com.qrmg.zd.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Description: 账号管理
 * @ClassName: Manager  
 * @author zz
 * @date 2019年1月22日 下午2:35:33
 */
public class Manager {

	private int id;
	private String mgName;
	private String mgAccount;
	private String mgPassword;
	private String mgPhone;
	private String mgEmail;
	private String mgContact;
	private String mgSalt;
	private String mgCode;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:dd", timezone = "GMT+8")
	private Date createTime;
	public String getMgAccount() {
		return mgAccount;
	}
	public void setMgAccount(String mgAccount) {
		this.mgAccount = mgAccount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMgName() {
		return mgName;
	}
	public void setMgName(String mgName) {
		this.mgName = mgName;
	}
	public String getMgPassword() {
		return mgPassword;
	}
	public void setMgPassword(String mgPassword) {
		this.mgPassword = mgPassword;
	}
	public String getMgPhone() {
		return mgPhone;
	}
	public void setMgPhone(String mgPhone) {
		this.mgPhone = mgPhone;
	}
	public String getMgEmail() {
		return mgEmail;
	}
	public void setMgEmail(String mgEmail) {
		this.mgEmail = mgEmail;
	}
	public String getMgContact() {
		return mgContact;
	}
	public void setMgContact(String mgContact) {
		this.mgContact = mgContact;
	}
	public String getMgSalt() {
		return mgSalt;
	}
	public void setMgSalt(String mgSalt) {
		this.mgSalt = mgSalt;
	}
	public String getMgCode() {
		return mgCode;
	}
	public void setMgCode(String mgCode) {
		this.mgCode = mgCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
