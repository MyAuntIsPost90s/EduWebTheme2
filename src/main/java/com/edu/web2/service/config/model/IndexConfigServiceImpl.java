package com.edu.web2.service.config.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.web2.service.active.model.Active;
import com.edu.web2.service.active.model.ActiveService;
import com.edu.web2.service.common.util.BeanConvertUtil;
import com.edu.web2.service.common.util.UnidUtil;
import com.edu.web2.service.config.ConfigConstant;
import com.edu.web2.service.config.dao.IndexConfigMapper;
import com.edu.web2.service.config.dto.IndexConfigDto;
import com.edu.web2.service.config.dto.IndexConfigForAppDto;
import com.edu.web2.service.news.NewsConstant;
import com.edu.web2.service.news.dto.NewsDto;
import com.edu.web2.service.news.model.News;
import com.edu.web2.service.news.model.NewsService;
import com.edu.web2.service.recruitment.enums.RecruitmentEnums;
import com.edu.web2.service.recruitment.model.Recruitment;
import com.edu.web2.service.recruitment.model.RecruitmentService;

import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 首页配置接口实现类
 * 
 * @author caich
 *
 */
@Service
public class IndexConfigServiceImpl implements IndexConfigService {

	@Resource
	private IndexConfigMapper indexConfigMapper;
	@Resource
	private NewsService newsService;
	@Resource
	private BannerService bannerService;
	@Resource
	private ActiveService activeService;
	@Resource
	private RecruitmentService recruitmentService;

	@Override
	public synchronized void edit(IndexConfig indexConfig) throws ServiceException {
		if (StringValid.isNullOrEmpty(indexConfig.getCreateUserId())) {
			throw new ServiceException("创建人id不能为空");
		}
		IndexConfigDto indexConfigDto = detail();
		Date currDate = new Date();
		if (ObjectValid.isNull(indexConfigDto)) { // 添加
			indexConfig.setUpdateUserId(indexConfig.getUpdateUserId());
			indexConfig.setUpdateTime(currDate);
			indexConfig.setCreateTime(currDate);
			indexConfig.setId(UnidUtil.getId());
			indexConfigMapper.insert(indexConfig);
		} else { // 修改
			indexConfig.setId(indexConfigDto.getId());
			indexConfig.setUpdateUserId(indexConfig.getCreateUserId());
			indexConfig.setUpdateTime(currDate);
			indexConfigMapper.update(indexConfig);
		}
	}

	@Override
	public IndexConfigDto detail() throws ServiceException {
		List<IndexConfig> list = indexConfigMapper.getList(new IndexConfig());
		if (ObjectValid.isEmpty(list)) {
			return null;
		}
		IndexConfigDto indexConfigDto = BeanConvertUtil.convert(list.get(0), IndexConfigDto.class);
		if (StringValid.isNotNullOrEmpty(indexConfigDto.getFirstNewsId())) {
			NewsDto newsDto = newsService.detail(indexConfigDto.getFirstNewsId());
			if (ObjectValid.isNotNull(newsDto)) {
				indexConfigDto.setFirstNewsTitle(newsDto.getTitle());
			}
		}
		return indexConfigDto;
	}

	@Override
	public IndexConfigForAppDto detailForApp() throws ServiceException {
		IndexConfigDto currConfig = detail();
		if (ObjectValid.isNull(currConfig)) {
			throw new ServiceException("当前首页配置为空，请先进行首页配置");
		}
		IndexConfigForAppDto config = new IndexConfigForAppDto();
		config.setFirstNews(newsService.detail(currConfig.getFirstNewsId())); // 首个新闻
		config.setBanners(bannerService.list(new Banner())); // 轮播图
		News newsQ = new News();
		newsQ.setNewsColumnId(NewsConstant.NEWS_COLUMN_ID);
		config.setNews(newsService.list(newsQ, 1, ConfigConstant.ACTIVE_NEWS_SIZE)); // 新闻动态
		newsQ.setNewsColumnId(NewsConstant.NOTICE_COLUMN_ID);
		config.setNotices(newsService.list(newsQ, 1, ConfigConstant.NOTICE_NEWS_SIZE)); // 通知
		Active activeQ = new Active();
		//activeQ.setStatus(ActiveEnums.Status.NORMAL.value);
		config.setActives(activeService.list(activeQ, 1, ConfigConstant.ACTIVE_SIZE)); // 活动
		Recruitment recruitmentQ = new Recruitment();
		recruitmentQ.setStatus(RecruitmentEnums.Status.NORMAL.value);
		config.setRecruitments(recruitmentService.list(recruitmentQ, 1, ConfigConstant.RECRUITMENT_SIZE)); // 招聘信息
		return config;
	}

}
