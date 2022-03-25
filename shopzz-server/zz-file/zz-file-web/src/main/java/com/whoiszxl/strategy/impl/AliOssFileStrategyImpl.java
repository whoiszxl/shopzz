package com.whoiszxl.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.whoiszxl.cqrs.command.FileDeleteCommand;
import com.whoiszxl.entity.FmsFile;
import com.whoiszxl.properties.AliOssProperties;
import com.whoiszxl.strategy.AbstractFileStrategy;
import com.whoiszxl.utils.StrPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.whoiszxl.utils.DateUtils.DEFAULT_MONTH_FORMAT_SLASH;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/3/24
 */
@Slf4j
@Service
public class AliOssFileStrategyImpl extends AbstractFileStrategy {

    @Autowired
    private AliOssProperties aliOssProperties;

    private OSS buildClient() {
        return new OSSClientBuilder()
                .build(aliOssProperties.getEndpoint(),
                        aliOssProperties.getAccessKey(),
                        aliOssProperties.getSecretKey());
    }

    protected String getDomainUrl() {
        if(StringUtils.isNotBlank(aliOssProperties.getDomainUrl())) {
            return aliOssProperties.getDomainUrl();
        }

        String prefix = aliOssProperties.getEndpoint().startsWith("https://") ? "https://" : "http://";
        return prefix + aliOssProperties.getBucketName() + "." + aliOssProperties.getEndpoint().replaceFirst(prefix, "");
    }




    @Override
    protected void uploadFile(FmsFile fmsFile, MultipartFile multipartFile) {
        OSS client = buildClient();
        String bucketName = aliOssProperties.getBucketName();
        if(!client.doesBucketExist(bucketName)) {
            client.createBucket(bucketName);
        }

        //生成文件名
        String fileName = UUID.randomUUID().toString() + "." + fmsFile.getExt();

        //生成日期格式的相对路径
        String relativePath = Paths.get(LocalDate.now().format(DateTimeFormatter.ofPattern(DEFAULT_MONTH_FORMAT_SLASH))).toString();

        String relativeFileName = relativePath + StrPoolUtil.SLASH + fileName;
        relativeFileName = StrUtil.replace(relativeFileName, "\\\\", StrPoolUtil.SLASH);
        relativeFileName = StrUtil.replace(relativeFileName, "\\", StrPoolUtil.SLASH);

        //配置元数据
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentDisposition("attachment;fileName=" + fmsFile.getOriginalFileName());
        metadata.setContentType(fmsFile.getContentType());

        try {
            PutObjectRequest request = new PutObjectRequest(bucketName, relativeFileName, multipartFile.getInputStream(), metadata);
            PutObjectResult result = client.putObject(request);
            log.info("上传文件了，结果：{}", result);

            String url = getDomainUrl() + StrPoolUtil.SLASH + relativeFileName;
            url = StrUtil.replace(url, "\\\\", StrPoolUtil.SLASH);
            url = StrUtil.replace(url, "\\", StrPoolUtil.SLASH);

            fmsFile.setUrl(url);
            fmsFile.setFinalFileName(fileName);
            fmsFile.setRelativePath(relativePath);
            fmsFile.setPlatformType(1);

            client.shutdown();

        } catch (IOException e) {
            log.error("文件上传失败", e);
        }

    }

    @Override
    protected void delete(FileDeleteCommand deleteCommand) {
        OSS client = buildClient();

        String bucketName = aliOssProperties.getBucketName();

        String url = deleteCommand.getRelativePath() + StrPoolUtil.SLASH + deleteCommand.getFinalFileName();
        url = StrUtil.replace(url, "\\\\", StrPoolUtil.SLASH);
        url = StrUtil.replace(url, "\\", StrPoolUtil.SLASH);
        client.deleteObject(bucketName, url);

        client.shutdown();
    }
}
