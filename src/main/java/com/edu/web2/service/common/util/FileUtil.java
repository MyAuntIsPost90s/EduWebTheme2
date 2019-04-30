package com.edu.web2.service.common.util;

/**
 * 文件工具类
 * 
 * @author caich
 *
 */
public class FileUtil {

	/**
	 * 判断文件是否为图片
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isImage(String fileName) {
		String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
		switch (ext) {
		case "jpge":
			return true;
		case "jpg":
			return true;
		case "png":
			return true;
		case "gif":
			return true;
		default:
			return false;
		}
	}

}
