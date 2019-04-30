package com.edu.web2.service.recruitment.model;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.web2.service.common.util.BeanConvertUtil;
import com.edu.web2.service.common.util.ExcelUtil;
import com.edu.web2.service.common.util.UnidUtil;
import com.edu.web2.service.recruitment.dao.ResumeMapper;
import com.edu.web2.service.recruitment.dto.RecruitmentDto;
import com.edu.web2.service.recruitment.dto.ResumeDto;
import com.edu.web2.service.recruitment.enums.ResumeEnums;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 用户投递的简历接口实现类
 * 
 * @author caich
 *
 */
@Service
public class ResumeServiceImpl implements ResumeService {

	@Resource
	private ResumeMapper resumeMapper;
	@Autowired
	private RecruitmentService recruitmentService;

	@Override
	public PageList<ResumeDto> list(Resume resume, int page, int rows) throws ServiceException {
		PageList<Resume> pageList = resumeMapper.getListWithPage(resume, new PageBounds(page, rows));
		PageList<ResumeDto> result = new PageList<>(pageList.getPaginator());
		for (Resume item : pageList) {
			ResumeDto resumeDto = BeanConvertUtil.convert(item, ResumeDto.class);
			resumeDto.setRecruitment(recruitmentService.detail(resumeDto.getRecruitmentId()));
			result.add(resumeDto);
		}
		return result;
	}

	@Override
	public void add(Resume resume) throws ServiceException {
		if (StringValid.isNullOrEmpty(resume.getCreateUserId())) {
			throw new ServiceException("创建人id不能为空");
		}
		if (StringValid.isNullOrEmpty(resume.getUserRealname())) {
			throw new ServiceException("姓名不能为空");
		}
		if (StringValid.isNullOrEmpty(resume.getRecruitmentId())) {
			throw new ServiceException("招聘id不能为空");
		}
		if (StringValid.isNullOrEmpty(resume.getPhone())) {
			throw new ServiceException("联系方式不能为空");
		}
		if (StringValid.isNullOrEmpty(resume.getStudentCode())) {
			throw new ServiceException("学号不能为空");
		}
		if (StringValid.isNullOrEmpty(resume.getContent())) {
			throw new ServiceException("简历内容不能为空");
		}
		Resume resumeQ = new Resume();
		resumeQ.setStudentCode(resume.getStudentCode());
		resumeQ.setRecruitmentId(resume.getRecruitmentId());
		if (resumeMapper.count(resumeQ) > 0) {
			throw new ServiceException("您已投递过简历，请耐心等待通知");
		}
		Date date = new Date();
		resume.setId(UnidUtil.getId());
		resume.setTitle(String.format("%s 投递的简历", resume.getUserRealname()));
		resume.setStatus(ResumeEnums.Status.WAIT_EXCUTE.value);
		resume.setCreateTime(date);
		resume.setUpdateTime(date);
		resume.setUpdateUserId(resume.getCreateUserId());
		resumeMapper.insert(resume);
	}

	@Override
	public void delete(List<String> ids) throws ServiceException {
		if (ObjectValid.isEmpty(ids)) {
			return;
		}
		resumeMapper.batchDelete(ids);
	}

	@Override
	public void update(Resume resume) throws ServiceException {
		if (StringValid.isNullOrEmpty(resume.getId())) {
			throw new ServiceException("id不能为空");
		}
		if (StringValid.isNullOrEmpty(resume.getUpdateUserId())) {
			throw new ServiceException("修改人id不能为空");
		}
		resume.setUpdateTime(new Date());
		resumeMapper.update(resume);
	}

	@Override
	public void exportExcel(OutputStream outputStream, Resume resume) throws ServiceException, Exception {
		if (StringValid.isNullOrEmpty(resume.getRecruitmentId())) {
			throw new ServiceException("招聘信息id不能为空");
		}
		RecruitmentDto recruitment = recruitmentService.detail(resume.getRecruitmentId());
		String[] cols = { "姓名", "学号", "联系方式", "是否标记处理" };
		List<Map<String, String>> result = new ArrayList<>();
		List<Resume> list = resumeMapper.getList(resume);
		for (Resume item : list) {
			Map<String, String> map = new HashMap<>();
			map.put("姓名", item.getUserRealname());
			map.put("学号", item.getStudentCode());
			map.put("联系方式", item.getPhone());
			map.put("是否标记处理", ResumeEnums.Status.WAIT_EXCUTE.value.equals(item.getStatus()) ? "否" : "是");
			result.add(map);
		}
		ExcelUtil.exportExcel(outputStream, result, recruitment.getTitle(), cols);
	}

}
