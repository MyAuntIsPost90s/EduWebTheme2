package com.edu.web2.gateway.app.news;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.gateway.sys.uimodel.SysPageList;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.news.dto.NewsDto;
import com.edu.web2.service.news.model.News;
import com.edu.web2.service.news.model.NewsService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 新闻栏目接口
 * 
 * @author caich
 *
 */
@Controller("appNewsController")
@RequestMapping("/app/news/news")
public class NewsController {

	@Resource
	private NewsService newsService;

	/**
	 * 获取新闻栏目列表
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response, News news, int page, int rows) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			PageList<NewsDto> pageList = newsService.list(news, page, rows);
			requestHolder.success(new SysPageList<>(pageList.getPaginator().getTotalCount(), pageList));
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 获取新闻栏目详情(并使浏览量+1)
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	@ResponseBody
	@RequestMapping("/readDetail")
	public void readDetail(HttpServletRequest request, HttpServletResponse response, String id) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			NewsDto newsDto = newsService.readDetail(id);
			requestHolder.success(newsDto);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}
}
