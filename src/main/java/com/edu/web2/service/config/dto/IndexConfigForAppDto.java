package com.edu.web2.service.config.dto;

import java.util.List;

import com.edu.web2.service.active.dto.ActiveDto;
import com.edu.web2.service.news.dto.NewsDto;
import com.edu.web2.service.recruitment.dto.RecruitmentDto;

/**
 * 首页配置
 * 
 * @author caich
 *
 */
public class IndexConfigForAppDto {

	/**
	 * 主键：id
	 */
	private String id;
	
	/**
	 * 轮播图
	 */
	private List<BannerDto> banners;

	/**
	 * 首个新闻
	 */
	private NewsDto firstNews;
	
	/**
	 * 新闻动态
	 */
	private List<NewsDto> news;
	
	/**
	 * 通知公告
	 */
	private List<NewsDto> notices;
	
	/**
	 * 部门活动
	 */
	private List<ActiveDto> actives;
	
	/**
	 * 招聘信息
	 */
	private List<RecruitmentDto> recruitments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<BannerDto> getBanners() {
		return banners;
	}

	public void setBanners(List<BannerDto> banners) {
		this.banners = banners;
	}

	public NewsDto getFirstNews() {
		return firstNews;
	}

	public void setFirstNews(NewsDto firstNews) {
		this.firstNews = firstNews;
	}

	public List<NewsDto> getNews() {
		return news;
	}

	public void setNews(List<NewsDto> news) {
		this.news = news;
	}

	public List<NewsDto> getNotices() {
		return notices;
	}

	public void setNotices(List<NewsDto> notices) {
		this.notices = notices;
	}

	public List<ActiveDto> getActives() {
		return actives;
	}

	public void setActives(List<ActiveDto> actives) {
		this.actives = actives;
	}

	public List<RecruitmentDto> getRecruitments() {
		return recruitments;
	}

	public void setRecruitments(List<RecruitmentDto> recruitments) {
		this.recruitments = recruitments;
	}
}
