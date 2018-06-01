package com.whoiszxl.oss;

import java.io.File;
import java.io.InputStream;

/**
 * 云存儲接口
 * @author whoiszxl
 *
 */
public interface CloudStorageService {
	
	/**
     * 上传文件到云存储, 返回图片HTTP地址
     * @param data 字节数据
     * @param path 云存储文件路径
     * @return 图片HTTP地址
     * @throws Exception
     */
    void upload(byte[] data, String path) throws Exception;

    /**
     * 上传文件到云存储, 返回图片HTTP地址
     * @param inputStream 字节流
     * @param path 云存储文件路径
     * @return 图片HTTP地址
     * @throws Exception
     */
    void upload(InputStream inputStream, String path) throws Exception;

    /**
     * 上传文件到云存储, 返回图片HTTP地址
     * @param file 文件
     * @param path 云存储文件路径
     * @return 图片HTTP地址
     * @throws Exception
     */
    void upload(File file, String path) throws Exception;

    String getBaseUrl();
}
