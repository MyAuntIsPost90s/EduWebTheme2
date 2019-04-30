package com.edu.web2.service.config.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.web2.service.ServiceConstant;
import com.edu.web2.service.common.util.BeanConvertUtil;
import com.edu.web2.service.common.util.UnidUtil;
import com.edu.web2.service.config.ConfigConstant;
import com.edu.web2.service.config.dao.BannerMapper;
import com.edu.web2.service.config.dto.BannerDto;

import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 轮播图接口实现类
 * 
 * @author caich
 *
 */
@Service
public class BannerServiceImpl implements BannerService {

	@Resource
	private BannerMapper bannerMapper;

	@Override
	public List<BannerDto> list(Banner banner) throws ServiceException {
		List<Banner> banners = bannerMapper.getList(banner);
		List<BannerDto> result = BeanConvertUtil.convert(banners, BannerDto.class);
		String baseUrl = ServiceConstant.URL_PREFIX;
		for (BannerDto bannerDto : result) {
			bannerDto.setPicUrlWithOutPrefix(bannerDto.getPicUrl());
			bannerDto.setPicUrl(baseUrl + bannerDto.getPicUrl());
		}
		return result;
	}

	@Override
	public void add(Banner banner) throws ServiceException {
		if (StringValid.isNullOrEmpty(banner.getCreateUserId())) {
			throw new ServiceException("创建人id不能为空");
		}
		if (StringValid.isNullOrEmpty(banner.getPicUrl())) {
			throw new ServiceException("图片路径不能为空");
		}
		if (StringValid.isNullOrEmpty(banner.getName())) {
			throw new ServiceException("标题不能为空");
		}
		if (bannerMapper.count(new Banner()) >= ConfigConstant.MAX_BANNER_COUNT) {
			throw new ServiceException(String.format("轮播图最多只能设置 %s 张", ConfigConstant.MAX_BANNER_COUNT));
		}
		Date currDate = new Date();
		banner.setCreateTime(currDate);
		banner.setUpdateTime(currDate);
		banner.setUpdateUserId(banner.getUpdateUserId());
		banner.setId(UnidUtil.getId());
		bannerMapper.insert(banner);
	}

	@Override
	public BannerDto detail(String id) throws ServiceException {
		if (StringValid.isNullOrEmpty(id)) {
			throw new ServiceException("id不能为空");
		}
		Banner bannerQ = new Banner();
		bannerQ.setId(id);
		List<BannerDto> list = list(bannerQ);
		return ObjectValid.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public void update(Banner banner) throws ServiceException {
		if (StringValid.isNullOrEmpty(banner.getUpdateUserId())) {
			throw new ServiceException("修改人id不能为空");
		}
		if (StringValid.isNullOrEmpty(banner.getId())) {
			throw new ServiceException("id不能为空");
		}
		Date currDate = new Date();
		banner.setUpdateTime(currDate);
		bannerMapper.update(banner);
	}

	@Override
	public void batchDelete(List<String> ids) throws ServiceException {
		if (ObjectValid.isEmpty(ids)) {
			return;
		}
		bannerMapper.batchDelete(ids);
	}

}
