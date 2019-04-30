package com.edu.web2.gateway.sys.config;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.gateway.sys.uimodel.SysPageList;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.config.dto.BannerDto;
import com.edu.web2.service.config.model.Banner;
import com.edu.web2.service.config.model.BannerService;
import com.edu.web2.service.user.dto.UserDto;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 轮播图接口
 * 
 * @author caich
 *
 */
@Controller("sysBannerController")
@RequestMapping("/sys/config/banner")
public class BannerController {

	@Resource
	private BannerService bannerService;

	/**
	 * 查询轮播图列表
	 * 
	 * @param request
	 * @param response
	 * @param banner
	 */
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response, Banner banner) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			List<BannerDto> list = bannerService.list(banner);
			requestHolder.success(SysConstant.LOAD_SUCCESS_CN, new SysPageList<>(list.size(), list));
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 查询轮播图列表
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response, String id) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			BannerDto bannerDto = bannerService.detail(id);
			requestHolder.success(SysConstant.LOAD_SUCCESS_CN, bannerDto);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 添加轮播图
	 * 
	 * @param request
	 * @param response
	 * @param banner
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, Banner banner) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			banner.setCreateUserId(userDto.getId());
			bannerService.add(banner);
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}

	/**
	 * 修改轮播图
	 * 
	 * @param request
	 * @param response
	 * @param banner
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, Banner banner) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			banner.setUpdateUserId(userDto.getId());
			bannerService.update(banner);
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}

	/**
	 * 批量删除轮播图
	 * 
	 * @param request
	 * @param response
	 * @param banner
	 */
	@RequestMapping("/batchDelete")
	public void batchDelete(HttpServletRequest request, HttpServletResponse response, String ids) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			String[] strs = ids.split(",");
			bannerService.batchDelete(Arrays.asList(strs));
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}

}
