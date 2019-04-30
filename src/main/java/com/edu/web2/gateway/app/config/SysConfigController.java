package com.edu.web2.gateway.app.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.config.dto.SysConfigDto;
import com.edu.web2.service.config.model.SysConfigService;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 系统配置接口
 * 
 * @author caich
 *
 */
@Controller("appSysConfig")
@RequestMapping("/app/config/sysConfig")
public class SysConfigController {

	@Resource
	private SysConfigService sysConfigService;

	/**
	 * 获取系统配置信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			SysConfigDto sysConfigDto = sysConfigService.detail();
			requestHolder.success(SysConstant.LOAD_SUCCESS_CN, sysConfigDto);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

}
