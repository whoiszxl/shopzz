package com.whoiszxl.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author whoiszxl
 *
 */
public interface FileService {

	String upload(MultipartFile file, String path);
	
	String uploadToQiniu(MultipartFile file, String path);
	
	String uploadToQiniu(File file, String path);
}
