package com.edu.web2.gateway.base;

import javax.servlet.http.HttpServletRequest;

import lingshi.valid.StringValid;

public class BaseConstant {

	public static final String OPER_SUCCESS_CN = "操作成功";
	public static final String OPER_FAILURE_CN = "操作失败";

	public static final String UPLOADER_BASE_PATH = "/Uploader";

	public static final String UPLOADER_Edit_PATH = "/Uploader/edit";

	public static String getExt(String name) {
		if (StringValid.isNullOrWhiteSpace(name)) {
			return "";
		}
		String ext = name.substring(name.lastIndexOf(".") + 1);
		return ext;
	}

	public static String getBaseUrl(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}
}
