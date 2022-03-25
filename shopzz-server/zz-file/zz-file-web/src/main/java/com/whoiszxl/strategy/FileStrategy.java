package com.whoiszxl.strategy;

import com.whoiszxl.cqrs.command.FileDeleteCommand;
import com.whoiszxl.entity.FmsFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件策略
 *
 * @author whoiszxl
 * @date 2022/3/23
 */
public interface FileStrategy {

    /**
     * 上传文件
     * @param file 文件
     * @return
     */
    FmsFile upload(MultipartFile file);

    /**
     * 批量删除文件
     * @param commandList 文件命令集合
     * @return
     */
    boolean delete(List<FileDeleteCommand> commandList);

}
