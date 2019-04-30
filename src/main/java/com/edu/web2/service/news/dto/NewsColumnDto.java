package com.edu.web2.service.news.dto;

import java.util.List;

public class NewsColumnDto {

	/**
	 * 主键：id
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 等级
	 */
	private Integer level;

	/**
	 * 兑奖地址
	 */
	private String parentId;

	/**
	 * 类型( 1.文章类别 2.文章 3.链接 )
	 */
	private Integer type;

	/**
	 * 链接
	 */
	private String link;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 排序权重
	 */
	private Integer sortIndex;

	/**
	 * 子栏目
	 */
	private List<NewsColumnDto> childs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public List<NewsColumnDto> getChilds() {
		return childs;
	}

	public void setChilds(List<NewsColumnDto> childs) {
		this.childs = childs;
	}
}
