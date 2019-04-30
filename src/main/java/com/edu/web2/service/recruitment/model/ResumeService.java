package com.edu.web2.service.recruitment.model;

import java.io.OutputStream;
import java.util.List;

import com.edu.web2.service.recruitment.dto.ResumeDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;

/**
 * 用户投递的简历接口
 * 
 * @author caich
 *
 */
public interface ResumeService {

	/**
	 * 查询用户投递的简历列表
	 * 
	 * @param activeUser
	 * @param page
	 * @param rows
	 * @return
	 * @throws ServiceException
	 */
	PageList<ResumeDto> list(Resume resume, int page, int rows) throws ServiceException;

	/**
	 * 投递简历
	 * 
	 * @param activeUser
	 * @throws ServiceException
	 */
	void add(Resume resume) throws ServiceException;

	/**
	 * 修改信息
	 * 
	 * @param activeUser
	 * @throws ServiceException
	 */
	void update(Resume resume) throws ServiceException;

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
	void exportExcel(OutputStream outputStream, Resume resume) throws ServiceException, Exception;
}
