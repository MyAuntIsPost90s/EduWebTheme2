package com.edu.web2.gateway.sys.active;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.gateway.sys.uimodel.SysPageList;
import com.edu.web2.service.active.dto.ActiveUserDto;
import com.edu.web2.service.active.model.ActiveUser;
import com.edu.web2.service.active.model.ActiveUserService;
import com.edu.web2.service.common.util.LogUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 用户活动接口
 * 
 * @author caich
 *
 */
@Controller("sysActiveUserController")
@RequestMapping("/sys/active/activeUser")
public class ActiveUserController {

	@Resource
	private ActiveUserService activeUserService;

	/**
	 * 查询申请参加活动的用户
	 * 
	 * @param request
	 * @param response
	 * @param activeUser
	 * @param page
	 * @param rows
	 */
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response, ActiveUser activeUser, int page,
			int rows) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			PageList<ActiveUserDto> pageList = activeUserService.list(activeUser, page, rows);
			requestHolder.success(new SysPageList<>(pageList.getPaginator().getTotalCount(), pageList));
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			activeUserService.delete(Arrays.asList(ids.split(",")));
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 导出学生报名名单
	 * 
	 * @param request
	 * @param response
	 * @param resume
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, ActiveUser activeUser) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			response.reset();
			response.setContentType("application/vnd.ms-excel; charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + java.net.URLEncoder.encode("活动报名清单" + ".xls", "UTF8"));
			activeUserService.exportExcel(response.getOutputStream(), activeUser);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}
}
