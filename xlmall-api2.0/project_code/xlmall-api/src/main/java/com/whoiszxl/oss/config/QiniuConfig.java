package com.whoiszxl.oss.config;

import com.whoiszxl.utils.PropertiesUtil;

/**
 * 七牛云配置
 * @author whoiszxl
 *
 */
public class QiniuConfig extends BaseConfig {

	private String accessKey = PropertiesUtil.getProperty("qiniu.accessKey");
    private String secretKey = PropertiesUtil.getProperty("qiniu.secretKey");
    private String bucket = PropertiesUtil.getProperty("qiniu.bucket");

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
