package com.edu.web2.gateway.sys.recruitment;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.gateway.sys.uimodel.SysPageList;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.recruitment.dto.RecruitmentDto;
import com.edu.web2.service.recruitment.model.Recruitment;
import com.edu.web2.service.recruitment.model.RecruitmentService;
import com.edu.web2.service.user.dto.UserDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 招聘接口
 * 
 * @author caich
 *
 */
@Controller("sysRecruitmentController")
@RequestMapping("/sys/recruitment/recruitment")
public class RecruitmentController {

	@Resource
	private RecruitmentService recruitmentService;

	/**
	 * 获取招聘列表
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response, Recruitment recruitment, int page, int rows) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			PageList<RecruitmentDto> pageList = recruitmentService.list(recruitment, page, rows);
			requestHolder.success(new SysPageList<>(pageList.getPaginator().getTotalCount(), pageList));
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 获取招聘详情
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
			RecruitmentDto recruitmentDto = recruitmentService.detail(id);
			requestHolder.success(recruitmentDto);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 添加招聘信息
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, Recruitment recruitment) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			recruitment.setCreateUserId(userDto.getId());
			recruitmentService.add(recruitment);
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}

	/**
	 * 修改招聘信息
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, Recruitment recruitment) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			recruitment.setUpdateUserId(userDto.getId());
			recruitmentService.update(recruitment);
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
			recruitmentService.delete(Arrays.asList(strs));
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.OPER_FAILURE_CN);
		}
	}
}
