package com.edu.web2.gateway.app.active;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.service.active.model.ActiveUser;
import com.edu.web2.service.active.model.ActiveUserService;
import com.edu.web2.service.common.util.LogUtil;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 用户活动接口
 * 
 * @author caich
 *
 */
@Controller("appActiveUserController")
@RequestMapping("/app/active/activeUser")
public class ActiveUserController {

	@Resource
	private ActiveUserService activeUserService;

	/**
	 * 提交活动申请
	 * 
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, ActiveUser activeUser) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			activeUser.setCreateUserId(activeUser.getStudentCode());
			activeUserService.add(activeUser);
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}
}
