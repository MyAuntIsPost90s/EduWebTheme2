package com.edu.web2.service.user.model;

import com.edu.web2.service.user.dto.UserDto;

import lingshi.model.ServiceException;

/**
 * 用户服务接口
 * 
 * @author caich
 *
 */
public interface UserService {

	/**
	 * 登陆
	 * 
	 * @return
	 * @throws ServiceException
	 */
	UserDto login(String username, String password) throws ServiceException;

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @throws ServiceException
	 */
	UserDto update(User user) throws ServiceException;
}
