package com.edu.web2.service.news.model;

import java.util.List;
import java.util.Map;

import com.edu.web2.service.news.dto.NewsColumnDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;

/**
 * 新闻栏目服务接口
 * 
 * @author caich
 *
 */
public interface NewsColumnService {

	/**
	 * 获取新闻栏目列表（包含首页和下载中心）
	 * 
	 * @return
	 * @throws ServiceException
	 */
	List<NewsColumnDto> listForApp() throws ServiceException;

	/**
	 * 获取新闻栏目详情以及其父或子项（包含首页和下载中心）
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	NewsColumnDto detailForApp(String id) throws ServiceException;

	/**
	 * 获取文章内容
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	NewsColumnDto columnContent(String id) throws ServiceException;

	/**
	 * 获取新闻栏目树级结构（包含首页和下载中心）
	 * 
	 * @param newsColumn
	 * @return
	 * @throws ServiceException
	 */
	List<NewsColumnDto> tree() throws ServiceException;

	/**
	 * 查询栏目列表
	 * 
	 * @param newsColumnDto
	 * @param page
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	PageList<NewsColumnDto> list(NewsColumn newsColumn, int page, int rows) throws ServiceException;

	/**
	 * 获取栏目查找表
	 * 
	 * @return
	 * @throws ServiceException
	 */
	Map<String, NewsColumnDto> map() throws ServiceException;

	/**
	 * 查询栏目详情
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	NewsColumnDto detail(String id) throws ServiceException;

	/**
	 * 添加新闻栏目
	 * 
	 * @param newsColumnDto
	 * @throws ServiceException
	 */
	void add(NewsColumn newsColumn) throws ServiceException;

	/**
	 * 修正新闻栏目
	 * 
	 * @param newsColumnDto
	 * @throws ServiceException
	 */
	void update(NewsColumn newsColumn) throws ServiceException;

	/**
	 * 批量删除栏目
	 * 
	 * @param newsColumnDto
	 * @throws ServiceException
	 */
	void batchDelete(List<String> ids) throws ServiceException;
}
