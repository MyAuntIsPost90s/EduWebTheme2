package com.edu.web2.gateway.app.news;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.news.dto.NewsColumnDto;
import com.edu.web2.service.news.model.NewsColumnService;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 新闻栏目接口
 * 
 * @author caich
 *
 */
@Controller("appNewsColumn")
@RequestMapping("/app/news/newsColumn")
public class NewsColumnController {

	@Resource
	private NewsColumnService newsColumnService;

	/**
	 * 获取新闻栏目列表
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/listForApp")
	public void listForApp(HttpServletRequest request, HttpServletResponse response) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			List<NewsColumnDto> list = newsColumnService.listForApp();
			requestHolder.success(list);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			requestHolder.entity(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 获取新闻栏目详情
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
			NewsColumnDto newsColumnDto = newsColumnService.detail(id);
			requestHolder.success(newsColumnDto);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}
	
	/**
	 * 获取新闻栏目详情以及其父或子项（包含首页和下载中心）
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	@ResponseBody
	@RequestMapping("/detailForApp")
	public void detailForApp(HttpServletRequest request, HttpServletResponse response, String id) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			NewsColumnDto newsColumnDto = newsColumnService.detailForApp(id);
			requestHolder.success(newsColumnDto);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}
}
