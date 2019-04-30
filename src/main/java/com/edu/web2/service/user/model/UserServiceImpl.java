package com.edu.web2.service.user.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.web2.service.common.util.BeanConvertUtil;
import com.edu.web2.service.user.dao.UserMapper;
import com.edu.web2.service.user.dto.UserDto;

import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public UserDto login(String username, String password) throws ServiceException {
		if (StringValid.isNullOrEmpty(username)) {
			throw new ServiceException("用户名不能为空");
		}
		if (StringValid.isNullOrEmpty(password)) {
			throw new ServiceException("密码不能为空");
		}
		User userQ = new User();
		userQ.setUsername(username);
		List<User> users = userMapper.getList(userQ);
		if (ObjectValid.isEmpty(users)) {
			throw new ServiceException("该用户不存在");
		}
		User user = users.get(0);
		if (!password.equals(user.getPassword())) {
			throw new ServiceException("用户名或密码错误");
		}
		UserDto userDto = BeanConvertUtil.convert(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto update(User user) throws ServiceException {
		if (StringValid.isNullOrEmpty(user.getUpdateUserId())) {
			throw new ServiceException("修改用户id不能为空");
		}
		if (StringValid.isNullOrEmpty(user.getId())) {
			throw new ServiceException("用户id不能为空");
		}
		user.setUpdateTime(new Date());
		userMapper.update(user);
		user = userMapper.getSingle(user.getId());
		return BeanConvertUtil.convert(user, UserDto.class);
	}

}
