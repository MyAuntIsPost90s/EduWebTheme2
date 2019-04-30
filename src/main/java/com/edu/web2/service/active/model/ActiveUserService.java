package com.edu.web2.service.active.model;

import java.io.OutputStream;
import java.util.List;

import com.edu.web2.service.active.dto.ActiveUserDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;

/**
 * 参与活动用户接口
 * 
 * @author caich
 *
 */
public interface ActiveUserService {

	/**
	 * 查询参与活动用户列表
	 * 
	 * @param activeUser
	 * @param page
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	PageList<ActiveUserDto> list(ActiveUser activeUser, int page, int rows) throws ServiceException;

	/**
	 * 添加参与活动申请
	 * 
	 * @param activeUser
	 * @throws ServiceException
	 */
	void add(ActiveUser activeUser) throws ServiceException;

	/**
	 * 根据id集合批量删除
	 * 
	 * @param ids
	 * @throws ServiceException
	 */
	void delete(List<String> ids) throws ServiceException;

	/**
	 * 导出excel
	 * 
	 * @param resume
	 * @throws ServiceException
	 */
	void exportExcel(OutputStream outputStream, ActiveUser resume) throws ServiceException, Exception;
}
