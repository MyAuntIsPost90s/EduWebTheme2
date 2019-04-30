package com.edu.web2.service.news.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.web2.service.common.util.BeanConvertUtil;
import com.edu.web2.service.common.util.UnidUtil;
import com.edu.web2.service.config.model.SysConfigService;
import com.edu.web2.service.news.NewsConstant;
import com.edu.web2.service.news.dao.NewsColumnMapper;
import com.edu.web2.service.news.dto.NewsColumnDto;
import com.edu.web2.service.news.enums.NewsColumnEnums;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import lingshi.model.ServiceException;
import lingshi.valid.ObjectValid;
import lingshi.valid.StringValid;

/**
 * 新闻栏目服务接口实现类
 * 
 * @author caich
 *
 */
@Service
public class NewsColumnServiceImpl implements NewsColumnService {

	@Resource
	private NewsColumnMapper newsColumnMapper;
	@Resource
	private SysConfigService sysConfigService;

	@Override
	public List<NewsColumnDto> listForApp() throws ServiceException {
		List<NewsColumnDto> result = new ArrayList<>();
		NewsColumnDto indexColumn = new NewsColumnDto();
		indexColumn.setName("首页");
		indexColumn.setType(NewsColumnEnums.Type.INDEX.value);
		result.add(indexColumn);
		NewsColumn newsColumnQ = new NewsColumn();
		newsColumnQ.setLevel(1);
		result.addAll(list(newsColumnQ, 1, Integer.MAX_VALUE));
		for (NewsColumnDto newsColumnDto : result) {
			newsColumnDto.setContent(null);
		}
		return result;
	}

	@Override
	public List<NewsColumnDto> tree() throws ServiceException {
		List<NewsColumnDto> result = new ArrayList<>();
		// 设置全部栏目项
		NewsColumnDto allColumn = new NewsColumnDto();
		allColumn.setName("全部");
		result.add(allColumn);

		// 设置首页栏目
		NewsColumnDto indexColumn = new NewsColumnDto();
		indexColumn.setName("首页");
		indexColumn.setId("-1");
		indexColumn.setType(NewsColumnEnums.Type.INDEX.value);
		List<NewsColumnDto> childs = new ArrayList<>();
		// 通知公告
		NewsColumnDto noticeColumn = new NewsColumnDto();
		noticeColumn.setName("通知公告");
		noticeColumn.setId(NewsConstant.NOTICE_COLUMN_ID);
		noticeColumn.setType(NewsColumnEnums.Type.ARTICLE_TYPE.value);
		childs.add(noticeColumn);
		// 新闻动态
		NewsColumnDto newsColumn = new NewsColumnDto();
		newsColumn.setName("新闻动态");
		newsColumn.setId(NewsConstant.NEWS_COLUMN_ID);
		newsColumn.setType(NewsColumnEnums.Type.ARTICLE_TYPE.value);
		childs.add(newsColumn);
		indexColumn.setChilds(childs);

		result.add(indexColumn);
		// 设置新闻栏目
		NewsColumn newsColumnQ = new NewsColumn();
		newsColumnQ.setLevel(1);
		result.addAll(list(newsColumnQ, 1, Integer.MAX_VALUE));
		return result;
	}

	@Override
	public PageList<NewsColumnDto> list(NewsColumn newsColumn, int page, int rows) throws ServiceException {
		PageList<NewsColumn> pageList = newsColumnMapper.getListWithPage(newsColumn, new PageBounds(page, rows));
		PageList<NewsColumnDto> result = new PageList<>(pageList.getPaginator());
		for (NewsColumn item : pageList) {
			NewsColumnDto newsColumnDto = BeanConvertUtil.convert(item, NewsColumnDto.class);
			// 获取子节点
			NewsColumn chlidQ = new NewsColumn();
			chlidQ.setParentId(newsColumnDto.getId());
			List<NewsColumnDto> childs = BeanConvertUtil.convert(newsColumnMapper.getList(chlidQ), NewsColumnDto.class);
			newsColumnDto.setChilds(childs);
			result.add(newsColumnDto);
		}
		return result;
	}

	@Override
	public NewsColumnDto detail(String id) throws ServiceException {
		if (StringValid.isNullOrEmpty(id)) {
			throw new ServiceException("id不能为空");
		}
		NewsColumn newsColumnQ = new NewsColumn();
		newsColumnQ.setId(id);
		List<NewsColumnDto> list = list(newsColumnQ, 1, 1);
		return ObjectValid.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public void add(NewsColumn newsColumn) throws ServiceException {
		if (StringValid.isNullOrEmpty(newsColumn.getCreateUserId())) {
			throw new ServiceException("创建人id不能为空");
		}
		if (StringValid.isNullOrEmpty(newsColumn.getName())) {
			throw new ServiceException("名称不能为空");
		}
		if (ObjectValid.isNull(newsColumn.getLevel())) {
			throw new ServiceException("栏目层级不能为空");
		}
		if (ObjectValid.isNull(newsColumn.getType())) {
			throw new ServiceException("栏目类型不能为空");
		}
		Date currDate = new Date();
		newsColumn.setId(UnidUtil.getId());
		newsColumn.setCreateTime(currDate);
		newsColumn.setUpdateUserId(newsColumn.getCreateUserId());
		newsColumn.setUpdateTime(currDate);
		newsColumnMapper.insert(newsColumn);
	}

	@Override
	public void update(NewsColumn newsColumn) throws ServiceException {
		if (StringValid.isNullOrEmpty(newsColumn.getUpdateUserId())) {
			throw new ServiceException("修改人id不能为空");
		}
		if (StringValid.isNullOrEmpty(newsColumn.getId())) {
			throw new ServiceException("id不能为空");
		}
		newsColumn.setUpdateTime(new Date());
		newsColumnMapper.update(newsColumn);
	}

	@Override
	public void batchDelete(List<String> ids) throws ServiceException {
		if (ObjectValid.isEmpty(ids)) {
			return;
		}
		newsColumnMapper.batchDelete(ids);
	}

	@Override
	public Map<String, NewsColumnDto> map() throws ServiceException {
		Map<String, NewsColumnDto> result = new HashMap<>();
		// 添加新闻栏目
		List<NewsColumn> newsColumns = newsColumnMapper.getList(new NewsColumn());
		for (NewsColumn newsColumn : newsColumns) {
			result.put(newsColumn.getId(), BeanConvertUtil.convert(newsColumn, NewsColumnDto.class));
		}
		// 添加首页栏目
		NewsColumnDto indexColumn = new NewsColumnDto();
		indexColumn.setName("首页");
		indexColumn.setId("-1");
		indexColumn.setType(NewsColumnEnums.Type.INDEX.value);
		result.put(indexColumn.getId(), indexColumn);
		// 添加新闻动态
		NewsColumnDto newsColumn = new NewsColumnDto();
		newsColumn.setName("新闻动态");
		newsColumn.setId(NewsConstant.NEWS_COLUMN_ID);
		newsColumn.setType(NewsColumnEnums.Type.ARTICLE_TYPE.value);
		result.put(newsColumn.getId(), newsColumn);
		// 添加通知公告
		NewsColumnDto noticeColumn = new NewsColumnDto();
		noticeColumn.setName("通知公告");
		noticeColumn.setId(NewsConstant.NOTICE_COLUMN_ID);
		noticeColumn.setType(NewsColumnEnums.Type.ARTICLE_TYPE.value);
		result.put(noticeColumn.getId(), noticeColumn);
		return result;
	}

	@Override
	public NewsColumnDto detailForApp(String id) throws ServiceException {
		if (StringValid.isNullOrEmpty(id)) {
			throw new ServiceException("id不能为空");
		}
		List<NewsColumnDto> list = tree();
		for (NewsColumnDto item : list) {
			if (id.equals(item.getId())) {
				return item;
			}
			if (ObjectValid.isNotEmpty(item.getChilds())) {
				for (NewsColumnDto child : item.getChilds()) {
					if (id.equals(child.getId())) {
						return item;
					}
				}
			}
		}
		return null;
	}

	@Override
	public NewsColumnDto columnContent(String id) throws ServiceException {
		if (StringValid.isNullOrEmpty(id)) {
			throw new ServiceException("id不能为空");
		}
		NewsColumn newsColumn = newsColumnMapper.getSingle(id);
		NewsColumnDto newsColumnDto = BeanConvertUtil.convert(newsColumn, NewsColumnDto.class);
		return newsColumnDto;
	}

}
