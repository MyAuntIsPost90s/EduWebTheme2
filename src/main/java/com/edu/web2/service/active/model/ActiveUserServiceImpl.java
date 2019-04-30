package com.edu.web2.service.active.model;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.web2.service.active.dao.ActiveUserMapper;
import com.edu.web2.service.active.dto.ActiveDto;
import com.edu.web2.service.active.dto.ActiveUserDto;
import com.edu.web2.service.common.util.BeanConvertUtil;
import com.edu.web2.service.common.util.ExcelUtil;
import com.edu.web2.service.common.util.UnidUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import lingshi.model.ServiceException;
import lingshi.utilities.DateUtil;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 参与活动用户接口实现类
 * 
 * @author caich
 *
 */
@Service
public class ActiveUserServiceImpl implements ActiveUserService {

	@Resource
	private ActiveUserMapper activeUserMapper;
	@Resource
	private ActiveService activeService;

	@Override
	public PageList<ActiveUserDto> list(ActiveUser activeUser, int page, int rows) throws ServiceException {
		PageList<ActiveUser> pageList = activeUserMapper.getListWithPage(activeUser, new PageBounds(page, rows));
		PageList<ActiveUserDto> result = new PageList<>(pageList.getPaginator());
		for (ActiveUser item : pageList) {
			ActiveUserDto activeUserDto = BeanConvertUtil.convert(item, ActiveUserDto.class);
			activeUserDto.setActiveDto(activeService.detail(activeUserDto.getActiveId()));
			result.add(activeUserDto);
		}
		return result;
	}

	@Override
	public void add(ActiveUser activeUser) throws ServiceException {
		if (StringValid.isNullOrEmpty(activeUser.getCreateUserId())) {
			throw new ServiceException("创建人id不能为空");
		}
		if (StringValid.isNullOrEmpty(activeUser.getUserRealname())) {
			throw new ServiceException("姓名不能为空");
		}
		if (StringValid.isNullOrEmpty(activeUser.getPhone())) {
			throw new ServiceException("联系方式不能为空");
		}
		if (StringValid.isNullOrEmpty(activeUser.getActiveId())) {
			throw new ServiceException("活动id不能为空");
		}
		if (StringValid.isNullOrEmpty(activeUser.getStudentCode())) {
			throw new ServiceException("学号不能为空");
		}
		ActiveUser activeUserQ = new ActiveUser();
		activeUserQ.setActiveId(activeUser.getActiveId());
		activeUserQ.setStudentCode(activeUser.getStudentCode());
		if (activeUserMapper.count(activeUserQ) > 0) {
			throw new ServiceException("您已参与本次活动，请勿重复参与");
		}
		Date date = new Date();
		activeUser.setId(UnidUtil.getId());
		activeUser.setCreateTime(date);
		activeUser.setUpdateTime(date);
		activeUser.setUpdateUserId(activeUser.getCreateUserId());
		activeUserMapper.insert(activeUser);
	}

	@Override
	public void delete(List<String> ids) throws ServiceException {
		if (ObjectValid.isEmpty(ids)) {
			return;
		}
		activeUserMapper.batchDelete(ids);
	}

	@Override
	public void exportExcel(OutputStream outputStream, ActiveUser activeUser) throws ServiceException, Exception {
		if (StringValid.isNullOrEmpty(activeUser.getActiveId())) {
			throw new ServiceException("招聘信息id不能为空");
		}
		ActiveDto active = activeService.detail(activeUser.getActiveId());
		if (ObjectValid.isNull(active)) {
			throw new ServiceException("该活动不存在");
		}
		String[] cols = { "姓名", "学号", "联系方式", "报名时间" };
		List<Map<String, String>> result = new ArrayList<>();
		List<ActiveUser> list = activeUserMapper.getList(activeUser);
		for (ActiveUser item : list) {
			Map<String, String> map = new HashMap<>();
			map.put("姓名", item.getUserRealname());
			map.put("学号", item.getStudentCode());
			map.put("联系方式", item.getPhone());
			map.put("报名时间", DateUtil.format(item.getCreateTime(), DateUtil.YYYYMMDDHHMMSSSpt));
			result.add(map);
		}
		ExcelUtil.exportExcel(outputStream, result, active.getTitle(), cols);
	}

}
