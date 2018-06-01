package com.whoiszxl.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author whoiszxl
 *
 */
public interface FileService {

	String upload(MultipartFile file, String path);
	
	String uploadToQiniu(MultipartFile file, String path);
}
