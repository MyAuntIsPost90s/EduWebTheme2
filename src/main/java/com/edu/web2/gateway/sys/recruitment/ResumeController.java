package com.edu.web2.gateway.sys.recruitment;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.gateway.sys.uimodel.SysPageList;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.recruitment.dto.ResumeDto;
import com.edu.web2.service.recruitment.model.Resume;
import com.edu.web2.service.recruitment.model.ResumeService;
import com.edu.web2.service.user.dto.UserDto;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 投递简历
 * 
 * @author caich
 *
 */
@Controller("sysResumeController")
@RequestMapping("/sys/recruitment/resume")
public class ResumeController {

	@Resource
	private ResumeService resumeService;

	/**
	 * 查询投递简历的用户
	 * 
	 * @param request
	 * @param response
	 * @param activeUser
	 * @param page
	 * @param rows
	 */
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response, Resume resume, int page, int rows) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			PageList<ResumeDto> pageList = resumeService.list(resume, page, rows);
			requestHolder.success(new SysPageList<>(pageList.getPaginator().getTotalCount(), pageList));
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 修改简历信息
	 * 
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, Resume resume) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			UserDto userDto = (UserDto) requestHolder.getClientUser();
			resume.setUpdateUserId(userDto.getId());
			resumeService.update(resume);
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}

	/**
	 * 导出学生投递名单
	 * 
	 * @param request
	 * @param response
	 * @param resume
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, Resume resume) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			response.reset();
			response.setContentType("application/vnd.ms-excel; charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + java.net.URLEncoder.encode("人员清单" + ".xls", "UTF8"));
			resumeService.exportExcel(response.getOutputStream(), resume);
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
			resumeService.delete(Arrays.asList(ids.split(",")));
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}
}
