package com.edu.web2.gateway.sys.active;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.gateway.sys.uimodel.SysPageList;
import com.edu.web2.service.active.dto.ActiveDto;
import com.edu.web2.service.active.model.Active;
import com.edu.web2.service.active.model.ActiveService;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.user.dto.UserDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 活动接口
 * 
 * @author caich
 *
 */
@Controller("sysActiveController")
@RequestMapping("/sys/active/active")
public class ActiveController {

	@Resource
	private ActiveService activeService;

	/**
	 * 获取活动列表
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response, Active active, int page, int rows) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			PageList<ActiveDto> pageList = activeService.list(active, page, rows);
			requestHolder.success(new SysPageList<>(pageList.getPaginator().getTotalCount(), pageList));
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 获取活动详情
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	@ResponseBody
	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response, String id) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			ActiveDto activeDto = activeService.detail(id);
			requestHolder.success(activeDto);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 添加活动
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, Active active) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			active.setCreateUserId(userDto.getId());
			activeService.add(active);
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}

	/**
	 * 修改活动
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, Active active) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			active.setUpdateUserId(userDto.getId());
			activeService.update(active);
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, String ids) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			String[] strs = ids.split(",");
			activeService.delete(Arrays.asList(strs));
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}
}
