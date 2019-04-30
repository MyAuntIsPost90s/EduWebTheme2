package com.edu.web2.service.active.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.web2.service.ServiceConstant;
import com.edu.web2.service.active.dao.ActiveMapper;
import com.edu.web2.service.active.dto.ActiveDto;
import com.edu.web2.service.common.util.BeanConvertUtil;
import com.edu.web2.service.common.util.UnidUtil;
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
public class ActiveServiceImpl implements ActiveService {

	@Resource
	private ActiveMapper activeMapper;
	@Resource
	private ActiveUserService activeUserService;

	@Override
	public PageList<ActiveDto> list(Active active, int page, int rows) throws ServiceException {
		PageList<Active> pageList = activeMapper.getListWithPage(active, new PageBounds(page, rows));
		PageList<ActiveDto> result = new PageList<>(pageList.getPaginator());
		for (Active item : pageList) {
			ActiveDto activeDto = BeanConvertUtil.convert(item, ActiveDto.class);
			activeDto.setContent(null);
			if (StringValid.isNotNullOrEmpty(activeDto.getPicUrl())) {
				activeDto.setPicUrlNoPrefix(activeDto.getPicUrl());
				activeDto.setPicUrl(ServiceConstant.URL_PREFIX + activeDto.getPicUrl());
			}
			result.add(activeDto);
		}
		return result;
	}

	@Override
	public ActiveDto detail(String id) throws ServiceException {
		if (StringValid.isNullOrEmpty(id)) {
			throw new ServiceException("id不能为空");
		}
		Active active = activeMapper.getSingle(id);
		ActiveDto activeDto = BeanConvertUtil.convert(active, ActiveDto.class);
		if (StringValid.isNotNullOrEmpty(activeDto.getPicUrl())) {
			activeDto.setPicUrlNoPrefix(activeDto.getPicUrl());
			activeDto.setPicUrl(ServiceConstant.URL_PREFIX + activeDto.getPicUrl());
		}
		return activeDto;
	}

	@Override
	public void add(Active active) throws ServiceException {
		if (StringValid.isNullOrEmpty(active.getCreateUserId())) {
			throw new ServiceException("创建人id不能为空");
		}
		if (StringValid.isNullOrEmpty(active.getTitle())) {
			throw new ServiceException("标题不能为空");
		}
		if (StringValid.isNullOrEmpty(active.getContent())) {
			throw new ServiceException("内容不能为空");
		}
		Date date = new Date();
		active.setId(UnidUtil.getId());
		active.setCreateTime(date);
		active.setUpdateTime(date);
		active.setUpdateUserId(active.getCreateUserId());
		activeMapper.insert(active);
	}

	@Override
	public void update(Active active) throws ServiceException {
		if (StringValid.isNullOrEmpty(active.getUpdateUserId())) {
			throw new ServiceException("修改人id不能为空");
		}
		if (StringValid.isNullOrEmpty(active.getId())) {
			throw new ServiceException("id不能为空");
		}
		active.setUpdateTime(new Date());
		activeMapper.update(active);
	}

	@Override
	public void delete(List<String> ids) throws ServiceException {
		if (ObjectValid.isEmpty(ids)) {
			return;
		}
		activeMapper.batchDelete(ids);
	}

}
