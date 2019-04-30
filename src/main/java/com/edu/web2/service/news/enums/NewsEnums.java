package com.edu.web2.service.news.enums;

/**
 * 新闻枚举
 * 
 * @author caich
 *
 */
public class NewsEnums {

	public enum Type {
		ARTICLE(0, "文章"), LINK(1, "链接");

		public Integer value;
		public String valueZh;

		Type(Integer value, String valueZh) {
			this.value = value;
			this.valueZh = valueZh;
		}

	}

}
