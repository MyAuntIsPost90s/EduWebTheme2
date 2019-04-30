package com.edu.web2.service.recruitment.enums;

/**
 * 招聘枚举
 * 
 * @author caich
 *
 */
public class RecruitmentEnums {

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
