package com.edu.web2.service.news.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.web2.service.ServiceConstant;
import com.edu.web2.service.common.util.BeanConvertUtil;
import com.edu.web2.service.common.util.UnidUtil;
import com.edu.web2.service.news.dao.NewsMapper;
import com.edu.web2.service.news.dto.NewsColumnDto;
import com.edu.web2.service.news.dto.NewsDto;
import com.edu.web2.service.news.enums.NewsEnums;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 新闻服务接口实现类
 * 
 * @author caich
 *
 */
@Service
public class NewsServiceImpl implements NewsService {

	@Resource
	private NewsMapper newsMapper;
	@Resource
	private NewsColumnService newsColumnService;

	@Override
	public PageList<NewsDto> list(News news, int page, int rows) throws ServiceException {
		PageList<News> pageList = newsMapper.getListWithPage(news, new PageBounds(page, rows));
		PageList<NewsDto> result = new PageList<>(pageList.getPaginator());
		String baseUrl = ServiceConstant.URL_PREFIX;
		Map<String, NewsColumnDto> columnMap = newsColumnService.map();
		for (News item : pageList) {
			NewsDto newsDto = BeanConvertUtil.convert(item, NewsDto.class);
			if (StringValid.isNotNullOrEmpty(newsDto.getPreviewPicUrl())) {
				newsDto.setPreviewPicUrlWithOutPrefix(newsDto.getPreviewPicUrl());
				newsDto.setPreviewPicUrl(baseUrl + newsDto.getPreviewPicUrl());
			}
			newsDto.setContent(null);
			if (columnMap.containsKey(newsDto.getNewsColumnId())) {
				newsDto.setNewsColumnName(columnMap.get(newsDto.getNewsColumnId()).getName());
			}
			result.add(newsDto);
		}
		return result;
	}

	@Override
	public Map<String, NewsDto> map(List<String> ids) throws ServiceException {
		Map<String, NewsDto> map = new HashMap<>();
		if (ObjectValid.isEmpty(ids)) {
			return null;
		}
		String baseUrl = ServiceConstant.URL_PREFIX;
		List<News> news = newsMapper.listByIds(ids);
		for (News item : news) {
			NewsDto newsDto = BeanConvertUtil.convert(item, NewsDto.class);
			if (StringValid.isNotNullOrEmpty(newsDto.getPreviewPicUrl())) {
				newsDto.setPreviewPicUrlWithOutPrefix(newsDto.getPreviewPicUrl());
				newsDto.setPreviewPicUrl(baseUrl + newsDto.getPreviewPicUrl());
			}
			map.put(newsDto.getId(), newsDto);
		}
		return map;
	}

	@Override
	public NewsDto detail(String id) throws ServiceException {
		if (StringValid.isNullOrEmpty(id)) {
			throw new ServiceException("id不能为空");
		}
		News news = newsMapper.getSingle(id);
		NewsDto newsDto = BeanConvertUtil.convert(news, NewsDto.class);
		if (ObjectValid.isNotNull(newsDto)) {
			if (StringValid.isNotNullOrEmpty(newsDto.getPreviewPicUrl())) {
				String baseUrl = ServiceConstant.URL_PREFIX;
				newsDto.setPreviewPicUrlWithOutPrefix(newsDto.getPreviewPicUrl());
				newsDto.setPreviewPicUrl(baseUrl + newsDto.getPreviewPicUrl());
			}
			Map<String, NewsColumnDto> columnMap = newsColumnService.map();
			if (columnMap.containsKey(newsDto.getNewsColumnId())) {
				newsDto.setNewsColumnName(columnMap.get(newsDto.getNewsColumnId()).getName());
			}
		}
		return newsDto;
	}

	@Override
	public void add(News news) throws ServiceException {
		if (StringValid.isNullOrEmpty(news.getCreateUserId())) {
			throw new ServiceException("创建人id不能为空");
		}
		if (StringValid.isNullOrEmpty(news.getTitle())) {
			throw new ServiceException("标题不能为空");
		}
		if (ObjectValid.isNull(news.getType())) {
			throw new ServiceException("类型不能为空");
		}
		if (ObjectValid.isNull(news.getPublishTime())) {
			throw new ServiceException("发布时间不能为空");
		}
		if (NewsEnums.Type.LINK.value.equals(news.getType())) {
			if (ObjectValid.isNull(news.getLink())) {
				throw new ServiceException("新闻链接不能为空");
			}
		}
		if (NewsEnums.Type.ARTICLE.value.equals(news.getType())) {
			if (ObjectValid.isNull(news.getContent())) {
				throw new ServiceException("新闻内容不能为空");
			}
			if (ObjectValid.isNull(news.getAuthor())) {
				throw new ServiceException("作者不能为空");
			}
		}
		Date currDate = new Date();
		news.setCreateTime(currDate);
		news.setUpdateTime(currDate);
		news.setUpdateUserId(news.getCreateUserId());
		news.setId(UnidUtil.getId());
		newsMapper.insert(news);
	}

	@Override
	public void update(News news) throws ServiceException {
		if (StringValid.isNullOrEmpty(news.getUpdateUserId())) {
			throw new ServiceException("修改人id不能为空");
		}
		news.setUpdateTime(new Date());
		newsMapper.update(news);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void batchUpdate(List<News> list) throws ServiceException {
		if (ObjectValid.isEmpty(list)) {
			return;
		}
		Date currDate = new Date();
		for (News news : list) {
			if (StringValid.isNullOrEmpty(news.getUpdateUserId())) {
				throw new ServiceException("修改人id不能为空");
			}
			news.setUpdateTime(currDate);
		}
		newsMapper.batchUpdate(list);
	}

	@Override
	public void batchDelete(List<String> ids) throws ServiceException {
		if (ObjectValid.isEmpty(ids)) {
			return;
		}
		newsMapper.batchDelete(ids);
	}

	@Override
	public synchronized NewsDto readDetail(String id) throws ServiceException {
		NewsDto result = detail(id);
		News updateNews = new News();
		updateNews.setId(result.getId());
		updateNews.setScanCount(result.getScanCount() + 1);
		newsMapper.update(updateNews);
		return result;
	}

}
