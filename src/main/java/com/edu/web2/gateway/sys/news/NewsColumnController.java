package com.edu.web2.gateway.sys.news;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.gateway.sys.uimodel.EUITree;
import com.edu.web2.gateway.sys.uimodel.SysPageList;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.news.dto.NewsColumnDto;
import com.edu.web2.service.news.model.NewsColumn;
import com.edu.web2.service.news.model.NewsColumnService;
import com.edu.web2.service.user.dto.UserDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;

/**
 * 新闻栏目接口
 * 
 * @author caich
 *
 */
@Controller("sysNewsColumn")
@RequestMapping("/sys/news/newsColumn")
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
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response, NewsColumn newsColumn, int page,
			int rows) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			PageList<NewsColumnDto> pageList = newsColumnService.list(newsColumn, page, rows);
			requestHolder.success(new SysPageList<>(pageList.getPaginator().getTotalCount(), pageList));
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			requestHolder.entity(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 获取树状列表
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/tree")
	public void tree(HttpServletRequest request, HttpServletResponse response) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			List<NewsColumnDto> list = newsColumnService.tree();
			List<EUITree> trees = new ArrayList<>();
			for (NewsColumnDto newsColumnDto : list) {
				EUITree tree = new EUITree();
				tree.setId(newsColumnDto.getId());
				tree.setText(newsColumnDto.getName());
				List<EUITree> childs = new ArrayList<>();
				if (ObjectValid.isNotEmpty(newsColumnDto.getChilds())) {
					for (NewsColumnDto item : newsColumnDto.getChilds()) {
						EUITree child = new EUITree();
						child.setId(item.getId());
						child.setText(item.getName());
						childs.add(child);
					}
				}
				tree.setChildren(childs);
				trees.add(tree);
			}
			requestHolder.success(trees);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
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
	 * 添加新闻栏目
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, NewsColumn newsColumn) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			newsColumn.setCreateUserId(userDto.getId());
			newsColumnService.add(newsColumn);
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}

	/**
	 * 修改新闻栏目
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, NewsColumn newsColumn) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			newsColumn.setUpdateUserId(userDto.getId());
			newsColumnService.update(newsColumn);
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
	@RequestMapping("/batchDelete")
	public void batchDelete(HttpServletRequest request, HttpServletResponse response, String ids) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			String[] strs = ids.split(",");
			newsColumnService.batchDelete(Arrays.asList(strs));
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}
}
