package com.edu.web2.service.config.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.web2.service.ServiceConstant;
import com.edu.web2.service.common.util.BeanConvertUtil;
import com.edu.web2.service.common.util.UnidUtil;
import com.edu.web2.service.config.dao.SysConfigMapper;
import com.edu.web2.service.config.dto.SysConfigDto;

import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 系统配置接口实现类
 * 
 * @author caich
 *
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

	@Resource
	private SysConfigMapper sysConfigMapper;

	@Override
	public synchronized void edit(SysConfig sysConfig) throws ServiceException {
		if (StringValid.isNullOrEmpty(sysConfig.getCreateUserId())) {
			throw new ServiceException("创建人id不能为空");
		}
		if (StringValid.isNullOrEmpty(sysConfig.getCopyright())) {
			throw new ServiceException("版权信息不能为空");
		}
		if (StringValid.isNullOrEmpty(sysConfig.getLogoPicUrl())) {
			throw new ServiceException("logo图片不能为空");
		}
		SysConfigDto sysConfigDto = detail();
		Date currDate = new Date();
		if (ObjectValid.isNull(sysConfigDto)) { // 添加
			sysConfig.setId(UnidUtil.getId());
			sysConfig.setCreateTime(currDate);
			sysConfig.setUpdateTime(currDate);
			sysConfig.setUpdateUserId(sysConfig.getCreateUserId());
			sysConfigMapper.insert(sysConfig);
		} else { // 修改
			sysConfig.setId(sysConfigDto.getId());
			sysConfig.setUpdateTime(currDate);
			sysConfigMapper.update(sysConfig);
		}
	}

	@Override
	public SysConfigDto detail() throws ServiceException {
		List<SysConfig> list = sysConfigMapper.getList(new SysConfig());
		if (ObjectValid.isEmpty(list)) {
			return null;
		}
		String baseUrl = ServiceConstant.URL_PREFIX;
		SysConfigDto sysConfigDto = BeanConvertUtil.convert(list.get(0), SysConfigDto.class);
		sysConfigDto.setLogoPicUrlWithOutPrefix(sysConfigDto.getLogoPicUrl());
		sysConfigDto.setLogoPicUrl(baseUrl + sysConfigDto.getLogoPicUrl());
		return sysConfigDto;
	}

//	@Override
//	public SysConfigForAppDto detailForApp() throws ServiceException {
//		SysConfigDto sysConfigDto = detail();
//		if (ObjectValid.isNull(sysConfigDto)) {
//			throw new ServiceException("获取系统配置失败，系统配置尚未设置");
//		}
//		SysConfigForAppDto sysConfigForAppDto = BeanConvertUtil.convert(sysConfigDto, SysConfigForAppDto.class);
//		return sysConfigForAppDto;
//	}

}
