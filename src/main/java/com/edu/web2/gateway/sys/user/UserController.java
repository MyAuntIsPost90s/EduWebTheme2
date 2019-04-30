package com.edu.web2.gateway.sys.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.user.dto.UserDto;
import com.edu.web2.service.user.enums.UserEnum;
import com.edu.web2.service.user.model.User;
import com.edu.web2.service.user.model.UserService;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 用户接口
 * 
 * @author caich
 *
 */
@RestController("sysUserController")
@RequestMapping("/sys/user/user")
public class UserController {

	@Resource
	private UserService userService;

	/**
	 * 登陆
	 * 
	 * @param userName
	 * @param password
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = userService.login(username, password);
			if (!UserEnum.Type.ADMIN.value.equals(userDto.getType())) {
				requestHolder.fail("当前用户权限不足");
				return;
			}
			String token = requestHolder.addClientUser(userDto);
			requestHolder.success(SysConstant.OPER_SUCCESS_CN, token);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}

	/**
	 * 获取当前用户信息
	 * 
	 */
	@RequestMapping("/currUser")
	public void currUser(HttpServletRequest request, HttpServletResponse response) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			requestHolder.success(SysConstant.OPER_SUCCESS_CN, userDto);
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, User user) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto currUser = (UserDto) requestHolder.getClientUser();
			user.setUpdateUserId(currUser.getId());
			currUser = userService.update(user);
			requestHolder.updateClientUser(currUser);
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}

}
