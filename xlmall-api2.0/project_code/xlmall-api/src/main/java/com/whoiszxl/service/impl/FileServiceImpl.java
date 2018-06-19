package com.whoiszxl.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.whoiszxl.oss.UniqueFileUploader;
import com.whoiszxl.service.FileService;
import com.whoiszxl.utils.FTPUtil;

/**
 * 
 * @author whoiszxl
 *
 */
@Service
public class FileServiceImpl implements FileService {

	private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

	@Autowired
	private UniqueFileUploader uniqueFileUploader;

	public String uploadToQiniu(MultipartFile file, String path) {

		String fileName = file.getOriginalFilename();// 获取原始文件名

		String fileExtsionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		logger.info("开始上传文件到七牛云,上传文件的文件名:{}", fileName);
		String qiniuFileName = null;
		File targetFile = null;
		try {
			targetFile = File.createTempFile("tmp", null);
			file.transferTo(targetFile);
			// 開始執行上傳到七牛云操作
			qiniuFileName = uniqueFileUploader.upload(targetFile, fileExtsionName, false);
			targetFile.delete();
		} catch (Exception e) {
			logger.error("七牛云上傳文件異常", e);
			e.printStackTrace();
		}
		return qiniuFileName;
	}

	public String uploadToQiniu(File file, String path) {

		String fileName = file.getName();// 获取原始文件名

		String fileExtsionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		logger.info("开始上传文件到七牛云,上传文件的文件名:{}", fileName);
		String qiniuFileName = null;
		try {
			// 開始執行上傳到七牛云操作
			qiniuFileName = uniqueFileUploader.upload(file, fileExtsionName, true);
		} catch (Exception e) {
			logger.error("七牛云上傳文件異常", e);
			e.printStackTrace();
		}
		return qiniuFileName;
	}

	@Override
	public String upload(MultipartFile file, String path) {
		String fileName = file.getOriginalFilename();// 获取原始文件名

		String fileExtsionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		String uploadFileName = UUID.randomUUID().toString() + "." + fileExtsionName;
		logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

		File fileDir = new File(path);
		if (!fileDir.exists()) {
			fileDir.setWritable(true);
			fileDir.mkdirs();
		}

		File targetFile = new File(path, uploadFileName);

		try {
			file.transferTo(targetFile);
			// 文件已经上传成功了
			FTPUtil.uploadFile(Lists.newArrayList(targetFile));
			// 已经上传到ftp服务器上

			targetFile.delete();
		} catch (IOException e) {
			logger.error("上传文件异常", e);
			return null;
		}
		// A:abc.jpg
		// B:abc.jpg
		return targetFile.getName();
	}
}
