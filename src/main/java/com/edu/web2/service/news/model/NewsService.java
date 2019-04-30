package com.edu.web2.service.news.model;

import java.util.List;
import java.util.Map;

import com.edu.web2.service.news.dto.NewsDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;

/**
 * 新闻服务接口
 * 
 * @author caich
 *
 */
public interface NewsService {

	/**
	 * 查询新闻列表
	 * 
	 * @param news
	 * @param page
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	PageList<NewsDto> list(News news, int page, int rows) throws ServiceException;

	/**
	 * 获取新闻哈希表
	 * 
	 * @param ids
	 * @return
	 * @throws ServiceException
	 */
	Map<String, NewsDto> map(List<String> ids) throws ServiceException;

	/**
	 * 查询新闻详情
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	NewsDto detail(String id) throws ServiceException;

	/**
	 * 查询新闻详情(并登记阅读数量)
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	NewsDto readDetail(String id) throws ServiceException;

	/**
	 * 添加新闻
	 * 
	 * @param news
	 * @throws ServiceException
	 */
	void add(News news) throws ServiceException;

	/**
	 * 修改新闻
	 * 
	 * @param news
	 * @throws ServiceException
	 */
	void update(News news) throws ServiceException;

	/**
	 * 批量修改新闻
	 * 
	 * @param news
	 * @throws ServiceException
	 */
	void batchUpdate(List<News> list) throws ServiceException;

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @throws ServiceException
	 */
	void batchDelete(List<String> ids) throws ServiceException;
}
