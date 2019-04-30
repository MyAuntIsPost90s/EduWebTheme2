package com.edu.web2.service.recruitment.enums;

/**
 * 简历枚举
 * 
 * @author caich
 *
 */
public class ResumeEnums {

	public enum Status {
		WAIT_EXCUTE(1, "正常"), EXCUTED(2, "已处理");

		public Integer value;
		public String valueZh;

		Status(Integer value, String valueZh) {
			this.value = value;
			this.valueZh = valueZh;
		}

	}

}
