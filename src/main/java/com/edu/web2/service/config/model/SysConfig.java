package com.edu.web2.service.config.model;

import java.util.Date;

public class SysConfig {
	/**
	 * 主键：id
	 */
	private String id;

	/**
	 * logo图片路径
	 */
	private String logoPicUrl;

	/**
	 * 版权
	 */
	private String copyright;

	/**
	 * 创建人id
	 */
	private String createUserId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人id
	 */
	private String updateUserId;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 邮编
	 */
	private String zipCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogoPicUrl() {
		return logoPicUrl;
	}

	public void setLogoPicUrl(String logoPicUrl) {
		this.logoPicUrl = logoPicUrl;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}