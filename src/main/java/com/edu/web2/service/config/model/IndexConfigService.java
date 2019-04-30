package com.edu.web2.service.config.model;

import com.edu.web2.service.config.dto.IndexConfigDto;
import com.edu.web2.service.config.dto.IndexConfigForAppDto;

import lingshi.model.ServiceException;

/**
 * 首页配置接口
 * 
 * @author caich
 *
 */
public interface IndexConfigService {

	/**
	 * 编辑首页配置
	 * 
	 * @param indexConfig
	 * @throws ServiceException
	 */
	void edit(IndexConfig indexConfig) throws ServiceException;
	
	/**
	 * 获取首页配置详情
	 * 
	 * @return
	 * @throws ServiceException
	 */
	IndexConfigDto detail() throws ServiceException;
	
	/**
	 * 客户端获取首页配置详情
	 * 
	 * @return
	 * @throws ServiceException
	 */
	IndexConfigForAppDto detailForApp() throws ServiceException;
}
