package com.whoiszxl.service;

import com.whoiszxl.entity.FmsFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-23
 */
public interface FileService extends IService<FmsFile> {

    /**
     * 上传文件
     * @param bizId
     * @param bizType
     * @param file
     * @return
     */
    String upload(Long id, String bizId, Integer bizType, MultipartFile file);

    /**
     * 批量删除文件
     * @param ids
     */
    void delete(Long[] ids);
}
