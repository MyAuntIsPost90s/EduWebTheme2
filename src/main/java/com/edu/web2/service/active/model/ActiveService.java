package com.edu.web2.service.active.model;

import java.util.List;

import com.edu.web2.service.active.dto.ActiveDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;

/**
 * 活动业务接口
 * 
 * @author caich
 *
 */
public interface ActiveService {
	
	/**
	 * 查询活动列表
	 * 
	 * @param active
	 * @param page
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	PageList<ActiveDto> list(Active active,int page,int rows) throws ServiceException;
	
	/**
	 * 通过id查询活动
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	ActiveDto detail(String id) throws ServiceException;

	/**
	 * 添加
	 * 
	 * @param active
	 * @throws ServiceException
	 */
	void add(Active active) throws ServiceException;
	
	/**
	 * 修改
	 * 
	 * @param active
	 * @throws ServiceException
	 */
	void update(Active active) throws ServiceException;
	
	/**
	 * 通过id批量删除
	 * 
	 * @param ids
	 * @throws ServiceException
	 */
	void delete(List<String> ids) throws ServiceException;
}
