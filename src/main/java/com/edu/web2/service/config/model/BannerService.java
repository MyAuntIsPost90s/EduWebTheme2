package com.edu.web2.service.config.model;

import java.util.List;

import com.edu.web2.service.config.dto.BannerDto;

import lingshi.model.ServiceException;

/**
 * 轮播图服务列表
 * 
 * @author caich
 *
 */
public interface BannerService {
	
	/**
	 * 获取轮播图列表
	 * 
	 * @param banner
	 * @return
	 * @throws ServiceException
	 */
	List<BannerDto> list(Banner banner) throws ServiceException;

	/**
	 * 获取轮播图详情
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	BannerDto detail(String id) throws ServiceException;
	
	/**
	 * 添加轮播图
	 * 
	 * @param banner
	 * @throws ServiceException
	 */
	void add(Banner banner) throws ServiceException;
	
	/**
	 * 修改轮播图
	 * 
	 * @param banner
	 * @throws ServiceException
	 */
	void update(Banner banner) throws ServiceException;
	
	/**
	 * 批量删除轮播图
	 * 
	 * @param ids
	 * @throws ServiceException
	 */
	void batchDelete(List<String> ids) throws ServiceException;
	
}
