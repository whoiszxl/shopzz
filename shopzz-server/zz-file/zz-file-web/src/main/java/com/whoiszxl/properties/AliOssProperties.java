package com.whoiszxl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS配置
 *
 * @author whoiszxl
 * @date 2022/3/24
 */
@Data
@Component
@ConfigurationProperties(prefix = "zz-file.alioss")
public class AliOssProperties {

    private String domainUrl;

    private String accessKey;

    private String secretKey;

    private String endpoint;

    private String bucketName;

}
