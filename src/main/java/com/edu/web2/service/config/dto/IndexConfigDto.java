package com.edu.web2.service.config.dto;

/**
 * 首页配置
 * 
 * @author caich
 *
 */
public class IndexConfigDto {

	/**
	 * 主键：id
	 */
	private String id;

	/**
	 * 首个新闻id
	 */
	private String firstNewsId;

	/**
	 * 首个新闻标题
	 */
	private String firstNewsTitle;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstNewsId() {
		return firstNewsId;
	}

	public void setFirstNewsId(String firstNewsId) {
		this.firstNewsId = firstNewsId;
	}

	public String getFirstNewsTitle() {
		return firstNewsTitle;
	}

	public void setFirstNewsTitle(String firstNewsTitle) {
		this.firstNewsTitle = firstNewsTitle;
	}

}
