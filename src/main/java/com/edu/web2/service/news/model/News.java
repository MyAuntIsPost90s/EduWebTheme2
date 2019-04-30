package com.edu.web2.service.news.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class News {
	/**
	 * 主键：id
	 */
	private String id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 作者
	 */
	private String author;

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
	 * 发布时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date publishTime;

	/**
	 * 浏览次数
	 */
	private Long scanCount;

	/**
	 * 类型 ( 0 文章 1 链接 )
	 */
	private Integer type;

	/**
	 * 链接 （当type为1时存在）
	 */
	private String link;

	/**
	 * 预览图路径
	 */
	private String previewPicUrl;

	/**
	 * 摘要
	 */
	private String shortContent;

	/**
	 * 栏目id
	 */
	private String newsColumnId;

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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Long getScanCount() {
		return scanCount;
	}

	public void setScanCount(Long scanCount) {
		this.scanCount = scanCount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPreviewPicUrl() {
		return previewPicUrl;
	}

	public void setPreviewPicUrl(String previewPicUrl) {
		this.previewPicUrl = previewPicUrl;
	}

	public String getShortContent() {
		return shortContent;
	}

	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

	public String getNewsColumnId() {
		return newsColumnId;
	}

	public void setNewsColumnId(String newsColumnId) {
		this.newsColumnId = newsColumnId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}