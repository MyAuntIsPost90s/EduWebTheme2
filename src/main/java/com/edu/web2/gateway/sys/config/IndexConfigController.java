package com.edu.web2.gateway.sys.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.config.dto.IndexConfigDto;
import com.edu.web2.service.config.model.IndexConfig;
import com.edu.web2.service.config.model.IndexConfigService;
import com.edu.web2.service.user.dto.UserDto;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 首页配置接口
 * 
 * @author caich
 *
 */
@Controller("sysIndexConfigController")
@RequestMapping("/sys/config/indexConfig")
public class IndexConfigController {

	@Resource
	private IndexConfigService indexConfigService;

	/**
	 * 获取首页配置信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			IndexConfigDto indexConfigDto = indexConfigService.detail();
			requestHolder.success(SysConstant.LOAD_SUCCESS_CN, indexConfigDto);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 编辑首页配置
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/edit")
	public void edit(HttpServletRequest request, HttpServletResponse response, IndexConfig indexConfig) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			indexConfig.setCreateUserId(userDto.getId());
			indexConfigService.edit(indexConfig);
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}

}
