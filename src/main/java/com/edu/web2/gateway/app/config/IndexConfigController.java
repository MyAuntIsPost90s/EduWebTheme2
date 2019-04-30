package com.edu.web2.gateway.app.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.config.dto.IndexConfigForAppDto;
import com.edu.web2.service.config.model.IndexConfigService;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 首页配置接口
 * 
 * @author caich
 *
 */
@Controller("appIndexConfigController")
@RequestMapping("/app/config/indexConfig")
public class IndexConfigController {

	@Resource
	private IndexConfigService indexConfigService;

	/**
	 * 获取首页配置信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/detailForApp")
	public void detail(HttpServletRequest request, HttpServletResponse response) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			IndexConfigForAppDto config = indexConfigService.detailForApp();
			requestHolder.success(SysConstant.LOAD_SUCCESS_CN, config);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

}
