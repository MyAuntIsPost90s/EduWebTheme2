package com.edu.web2.gateway.app.recruitment;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.web2.gateway.sys.SysConstant;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.recruitment.model.Resume;
import com.edu.web2.service.recruitment.model.ResumeService;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.ServiceException;

/**
 * 投递简历
 * 
 * @author caich
 *
 */
@Controller("appResumeController")
@RequestMapping("/app/recruitment/resume")
public class ResumeController {

	@Resource
	private ResumeService resumeService;
	
	/**
	 * 投递简历
	 * 
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, Resume resume) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		try {
			resume.setCreateUserId(resume.getStudentCode());
			resumeService.add(resume);
			requestHolder.success(SysConstant.OPER_SUCCESS_CN);
		} catch (ServiceException e) {
			requestHolder.fail(e.getMessage());
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(SysConstant.LOAD_FAILURE_CN);
		}
	}
}
