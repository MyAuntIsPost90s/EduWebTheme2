package com.edu.web2.gateway.base.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.edu.web2.gateway.base.BaseConstant;
import com.edu.web2.gateway.base.uimodel.MinFile;
import com.edu.web2.service.common.util.FileUtil;
import com.edu.web2.service.common.util.LogUtil;
import com.edu.web2.service.common.util.UnidUtil;

import lingshi.gateway.model.RequestFile;
import lingshi.gateway.model.RequestHolder;
import lingshi.model.LingShiConfig;
import lingshi.utilities.DateUtil;
import lingshi.valid.StringValid;
import net.coobird.thumbnailator.Thumbnails;
@Controller
@RequestMapping("/base/file/uploader")
public class UploaderController {

	/**
	 * 上传文件接口
	 * 
	 * @param appKey
	 * @param basePath
	 */
	@ResponseBody
	@RequestMapping("/uploadForMinFile")
	public void uploadForMinFile(HttpServletRequest request, HttpServletResponse response, String appKey,
			String basePath) {
		RequestHolder requestHolder = RequestHolder.get(request, response);
		if (!LingShiConfig.getInstance().getAppKey().equals(appKey)) {
			requestHolder.fail("密钥错误");
			return;
		}
		if (StringValid.isNullOrWhiteSpace(basePath)) {
			requestHolder.fail("basePath不能为空");
			return;
		}
		if (!basePath.startsWith("/")) {
			basePath = "/" + basePath;
		}
		try {
			RequestFile requestFile = requestHolder.getRequestFile();
			if (requestFile.isEmpty()) {
				requestHolder.fail("上传文件不能为空");
				return;
			}
			basePath = BaseConstant.UPLOADER_BASE_PATH + '/' + DateUtil.format(DateUtil.YYYYMMDD) + basePath;
			List<MultipartFile> files = requestFile.getFiles();
			List<MinFile> result = new ArrayList<MinFile>();
			for (MultipartFile file : files) {
				MinFile minFile = new MinFile();
				minFile.setName(file.getOriginalFilename());
				minFile.setSize(file.getSize());
				minFile.setExt(BaseConstant.getExt(file.getOriginalFilename()));
				String path = basePath + "/" + UnidUtil.getId() + "." + minFile.getExt();
				minFile.setPath(path);
				minFile.setUrl(BaseConstant.getBaseUrl(request) + path);
				File saveFile = new File(requestHolder.getRealPathPath(minFile.getPath()));
				if (!saveFile.getParentFile().exists()) {
					saveFile.getParentFile().mkdirs();
				}
				if (FileUtil.isImage(minFile.getName())) {
					Thumbnails.of(requestFile.getFile().getInputStream()).scale(1F).outputQuality(0.7).toFile(saveFile);
				} else {
					file.transferTo(saveFile);
				}
				result.add(minFile);
			}
			requestHolder.success(result);
		} catch (Exception e) {
			LogUtil.e(e);
			requestHolder.fail(BaseConstant.OPER_FAILURE_CN);
		}
	}

}
