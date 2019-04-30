package com.edu.web2.service.config.model;

import com.edu.web2.service.config.dto.SysConfigDto;

import lingshi.model.ServiceException;

/**
 * 系统配置
 * 
 * @author caich
 *
 */
public interface SysConfigService {

	/**
	 * 编辑系统配置
	 * 
	 * @param indexConfig
	 * @throws ServiceException
	 */
	void edit(SysConfig sysConfig) throws ServiceException;

	/**
	 * 获取系统配置详情
	 * 
	 * @return
	 * @throws ServiceException
	 */
	SysConfigDto detail() throws ServiceException;
}
