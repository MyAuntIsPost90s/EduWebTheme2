package com.edu.web2.service.news.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edu.web2.service.news.model.News;
import lingshi.mybaties.mapperextend.BaseMapper;

public interface NewsMapper extends BaseMapper<News> {
	
	/**
	 * 批量修改数据
	 * 
	 * @param list
	 */
	void batchUpdate(@Param("list") List<News> list);
	
	/**
	 * 通过id集合获取列表
	 * 
	 * @param ids
	 */
	List<News> listByIds(@Param("ids")List<String> ids);

}