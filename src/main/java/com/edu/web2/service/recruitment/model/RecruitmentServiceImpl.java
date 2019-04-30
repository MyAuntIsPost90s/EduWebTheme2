package com.edu.web2.service.recruitment.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.web2.service.common.util.BeanConvertUtil;
import com.edu.web2.service.common.util.UnidUtil;
import com.edu.web2.service.recruitment.dao.RecruitmentMapper;
import com.edu.web2.service.recruitment.dto.RecruitmentDto;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 活动业务接口实现类
 * 
 * @author caich
 *
 */
@Service
public class RecruitmentServiceImpl implements RecruitmentService {

	@Resource
	private RecruitmentMapper recruitmentMapper;
	
	@Override
	public PageList<RecruitmentDto> list(Recruitment recruitment, int page, int rows) throws ServiceException {
		PageList<Recruitment> pageList = recruitmentMapper.getListWithPage(recruitment, new PageBounds(page,rows));
		PageList<RecruitmentDto> result = new PageList<>(pageList.getPaginator());
		for (Recruitment item : pageList) {
			RecruitmentDto recruitmentDto = BeanConvertUtil.convert(item, RecruitmentDto.class);
			recruitmentDto.setContent(null);
			result.add(recruitmentDto);
		}
		return result;
	}

	@Override
	public RecruitmentDto detail(String id) throws ServiceException {
		if(StringValid.isNullOrEmpty(id)){
			throw new ServiceException("id不能为空");
		}
		Recruitment recruitment = recruitmentMapper.getSingle(id);
		RecruitmentDto recruitmentDto = BeanConvertUtil.convert(recruitment, RecruitmentDto.class);
		return recruitmentDto;
	}

	@Override
	public void add(Recruitment recruitment) throws ServiceException {
		if(StringValid.isNullOrEmpty(recruitment.getCreateUserId())){
			throw new ServiceException("创建人id不能为空");
		}
		if(StringValid.isNullOrEmpty(recruitment.getTitle())){
			throw new ServiceException("标题不能为空");
		}
		if(StringValid.isNullOrEmpty(recruitment.getContent())){
			throw new ServiceException("内容不能为空");
		}
		Date date = new Date();
		recruitment.setId(UnidUtil.getId());
		recruitment.setCreateTime(date);
		recruitment.setUpdateTime(date);
		recruitment.setUpdateUserId(recruitment.getCreateUserId());
		recruitmentMapper.insert(recruitment);
	}

	@Override
	public void update(Recruitment recruitment) throws ServiceException {
		if(StringValid.isNullOrEmpty(recruitment.getUpdateUserId())){
			throw new ServiceException("修改人id不能为空");
		}
		if(StringValid.isNullOrEmpty(recruitment.getId())){
			throw new ServiceException("id不能为空");
		}
		recruitment.setUpdateTime(new Date());
		recruitmentMapper.update(recruitment);
	}

	@Override
	public void delete(List<String> ids) throws ServiceException {
		if(ObjectValid.isEmpty(ids)){
			return;
		}
		recruitmentMapper.batchDelete(ids);
	}

}
