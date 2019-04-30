package com.edu.web2.service.news.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class NewsDto {
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
	 * 发布时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
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
	 * 预览图路径（不包含前缀）
	 */
	private String previewPicUrlWithOutPrefix;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 栏目名称
	 */
	private String newsColumnName;

	/**
	 * 栏目id
	 */
	private String newsColumnId;
	
	/**
	 * 文章摘要
	 */
	private String shortContent;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPreviewPicUrlWithOutPrefix() {
		return previewPicUrlWithOutPrefix;
	}

	public void setPreviewPicUrlWithOutPrefix(String previewPicUrlWithOutPrefix) {
		this.previewPicUrlWithOutPrefix = previewPicUrlWithOutPrefix;
	}

	public String getNewsColumnId() {
		return newsColumnId;
	}
	
	public void setNewsColumnId(String newsColumnId) {
		this.newsColumnId = newsColumnId;
	}
	
	public String getNewsColumnName() {
		return newsColumnName;
	}
	
	public void setNewsColumnName(String newsColumnName) {
		this.newsColumnName = newsColumnName;
	}
	
	public String getShortContent() {
		return shortContent;
	}
	
	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}
}
