package com.whoiszxl.oss;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UUID唯一值上傳文件工具
 * @author whoiszxl
 *
 */
@Component
public class UniqueFileUploader {

	@Autowired
	private CloudStorageService cloudStorageService;
	
	public String getFormattedBaseUrl() {
        String httpBase = cloudStorageService.getBaseUrl();
        if(!httpBase.endsWith("/")) {
            httpBase += "/";
        }
        return httpBase;
    }

    /**
     * 上传字节数组
     * @param data 字节数组
     * @param format 文件扩展名
     * @return 云存储HTTP地址
     * @throws Exception
     */
    public String upload(byte[] data, String format) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String target = uuid + "." + format;
        String httpBase = getFormattedBaseUrl();
        cloudStorageService.upload(data, target);
        return httpBase + target;
    }

    /**
     * 上传字节流
     * @param inputStream 字节流
     * @param format 文件扩展名
     * @return 云存储HTTP地址
     * @throws Exception
     */
    public String upload(InputStream inputStream, String format) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String target = uuid + "." + format;
        String httpBase = getFormattedBaseUrl();
        cloudStorageService.upload(inputStream, target);
        return httpBase + target;
    }

    /**
     * 上传本地文件
     * @param file 待上传的本地文件
     * @param format 文件扩展名
     * @return 云存储HTTP地址
     * @throws Exception
     */
    public String upload(File file, String format, boolean keepName) throws Exception {
    	String target = null;
    	if(keepName) {
    		target = file.getName();
    	}else {
    		String uuid = UUID.randomUUID().toString();
            target = uuid + "." + format;
    	}
    	
        cloudStorageService.upload(file, target);
        return target;
    }
}
