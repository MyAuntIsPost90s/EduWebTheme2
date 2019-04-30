package com.edu.web2.service.news.enums;

/**
 * 新闻栏目枚举
 * 
 * @author caich
 *
 */
public class NewsColumnEnums {

	/**
	 * 栏目类型( 1.文章类别 2.文章 3.链接 )
	 *
	 */
	public enum Type {
		DOWLOAD_CENTER(-1, "下载中心"), INDEX(0, "首页"), ARTICLE_TYPE(1, "文章类别"), ARTICLE(2, "文章"), LINK(3, "链接");

		public Integer value;
		public String valueZh;

		Type(Integer value, String valueZh) {
			this.value = value;
			this.valueZh = valueZh;
		}
	}
}
