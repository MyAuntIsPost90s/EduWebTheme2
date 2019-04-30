package com.edu.web2.service.user.enums;

public class UserEnum {

	/**
	 * 用户类型
	 *
	 */
	public enum Type {
		ADMIN(-1, "管理员"), USER(0, "普通用户");

		public Integer value;
		public String valueZh;

		Type(Integer value, String valueZh) {
			this.value = value;
			this.valueZh = valueZh;
		}
	}

}
