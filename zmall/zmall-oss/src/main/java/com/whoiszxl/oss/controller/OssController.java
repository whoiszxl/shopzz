package com.whoiszxl.oss.controller;

import com.whoiszxl.common.entity.Result;
import com.whoiszxl.oss.utils.OSSClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @description: Oss上传控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Slf4j
@RestController
@RequestMapping("/oss")
public class OssController {

    @Value("${aliyun.oss.maxSize}")
    private Integer uploadMaxSize;

    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) {
        try {
            //判断文件是否存在
            if(file == null) {
                throw new RuntimeException("文件不存在");
            }
            //获取文件完整名称
            String originalFilename = file.getOriginalFilename();
            if(StringUtils.isEmpty(originalFilename)) {
                throw new RuntimeException("文件不存在");
            }

            //获取文件的扩展名称 .jpg
            String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            //获取文件内容
            File uploadFile = File.createTempFile(UUID.randomUUID().toString().replaceAll("-",""), extName);
            file.transferTo(uploadFile);

            if(uploadFile.length() > uploadMaxSize)  {
                return Result.fail("文件大于" + uploadMaxSize);
            }

            String pathUrl = OSSClientUtil.uploadImage2OSS(uploadFile);
            return Result.success(pathUrl);
        }catch (Exception e) {
            log.error("上传文件出错", e);
        }

        return Result.fail("文件上传失败");

    }

}
