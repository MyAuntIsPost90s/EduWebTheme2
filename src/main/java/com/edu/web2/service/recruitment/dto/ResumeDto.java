package com.edu.web2.service.recruitment.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class ResumeDto {

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
	 * 状态（0未处理 1已处理）
	 */
	private Integer status;

	/**
	 * 招聘id
	 */
	private String recruitmentId;

	/**
	 * 用户姓名
	 */
	private String userRealname;

	/**
	 * 联系方式
	 */
	private String phone;

	/**
	 * 学号
	 */
	private String studentCode;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 招聘信息
	 */
	private RecruitmentDto recruitment;

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

	public String getRecruitmentId() {
		return recruitmentId;
	}

	public void setRecruitmentId(String recruitmentId) {
		this.recruitmentId = recruitmentId;
	}

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public RecruitmentDto getRecruitment() {
		return recruitment;
	}

	public void setRecruitment(RecruitmentDto recruitment) {
		this.recruitment = recruitment;
	}

}
