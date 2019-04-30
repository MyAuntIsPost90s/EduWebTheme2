package com.edu.web2.service.active.enums;

/**
 * 活动枚举
 * 
 * @author caich
 *
 */
public class ActiveEnums {

	public enum Status {
		NORMAL(1, "正常"), END(2, "结束");

		public Integer value;
		public String valueZh;

		Status(Integer value, String valueZh) {
			this.value = value;
			this.valueZh = valueZh;
		}

	}

}
