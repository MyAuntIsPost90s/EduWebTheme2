package com.edu.web2.gateway.base.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.web2.gateway.base.BaseConstant;
import com.edu.web2.service.common.util.LogUtil;

import lingshi.gateway.model.RequestHolder;
import lingshi.model.LingShiConfig;
import lingshi.valid.StringValid;

@Controller
@RequestMapping("/base/file/download")
public class DownloadController {

	/**
	 * 下载文件
	 * 
	 * @param request
	 * @param response
	 * @param appKey
	 * @param relatePath
	 */
	@ResponseBody
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response, String appKey, String relatePath) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		if (!LingShiConfig.getInstance().getAppKey().equals(appKey)) {
			requestHolder.fail("密钥错误");
			return;
		}
		if (StringValid.isNullOrEmpty(relatePath)) {
			requestHolder.fail("相对路径不能为空");
			return;
		}
		try {
			String relaPath = requestHolder.getRealPathPath(relatePath);
			File file = new File(relaPath);
			if (!file.exists()) {
				requestHolder.fail("该文件不存在");
				return;
			}
			response.reset();
			response.addHeader("Content-type", "applocallication/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
			try (OutputStream outputStream = response.getOutputStream();
					InputStream inputStream = new FileInputStream(file)) {
				byte[] buffer = new byte[1024 * 1024];
				int len;
				while ((len = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
				}
			}
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(BaseConstant.OPER_FAILURE_CN);
		}
	}

}
