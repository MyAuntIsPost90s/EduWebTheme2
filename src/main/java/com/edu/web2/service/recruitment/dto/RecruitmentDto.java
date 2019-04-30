package com.edu.web2.service.recruitment.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class RecruitmentDto {
	/**
	 * 主键：id
	 */
	private String id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 创建时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 招聘状态（1进行中 2已下架）
	 */
	private Integer status;

	/**
	 * 内容
	 */
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}