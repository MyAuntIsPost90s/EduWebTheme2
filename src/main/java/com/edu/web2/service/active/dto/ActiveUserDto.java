package com.edu.web2.service.active.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class ActiveUserDto {
	/**
	 * 主键：id
	 */
	private String id;

	/**
	 * 用户姓名
	 */
	private String userRealname;

	/**
	 * 活动id
	 */
	private String activeId;

	/**
	 * 创建时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 联系方式
	 */
	private String phone;

	/**
	 * 学号
	 */
	private String studentCode;

	/**
	 * 活动
	 */
	private ActiveDto activeDto;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public ActiveDto getActiveDto() {
		return activeDto;
	}

	public void setActiveDto(ActiveDto activeDto) {
		this.activeDto = activeDto;
	}
}