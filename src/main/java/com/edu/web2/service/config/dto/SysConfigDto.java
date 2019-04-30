package com.edu.web2.service.config.dto;

public class SysConfigDto {

	/**
	 * 主键：id
	 */
	private String id;

	/**
	 * logo图片路径
	 */
	private String logoPicUrl;

	/**
	 * 顶部图片路径（不包含前缀）
	 */
	private String logoPicUrlWithOutPrefix;

	/**
	 * 版权
	 */
	private String copyright;

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

	public String getLogoPicUrlWithOutPrefix() {
		return logoPicUrlWithOutPrefix;
	}

	public void setLogoPicUrlWithOutPrefix(String logoPicUrlWithOutPrefix) {
		this.logoPicUrlWithOutPrefix = logoPicUrlWithOutPrefix;
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
