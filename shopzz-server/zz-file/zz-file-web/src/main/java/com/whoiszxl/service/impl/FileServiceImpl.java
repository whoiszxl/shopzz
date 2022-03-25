package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.FileDeleteCommand;
import com.whoiszxl.entity.FmsFile;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.mapper.FileMapper;
import com.whoiszxl.service.FileService;
import com.whoiszxl.strategy.FileStrategy;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-23
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FmsFile> implements FileService {

    @Autowired
    private FileStrategy fileStrategy;

    @Override
    public String upload(Long id, String bizId, Integer bizType, MultipartFile file) {
        FmsFile fmsFile = fileStrategy.upload(file);

        fmsFile.setBizId(bizId);
        fmsFile.setBizType(bizType);

        if(id != null && id > 0) {
            fmsFile.setId(id);
            updateById(fmsFile);
        }else {
            save(fmsFile);
        }

        return fmsFile.getUrl();
    }

    @Override
    public void delete(Long[] ids) {
        if(ArrayUtils.isEmpty(ids)) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("传参无效"));
        }

        List<FmsFile> fileList = list(Wrappers.<FmsFile>lambdaQuery().in(FmsFile::getId, ids));
        if(fileList.isEmpty()) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("传参无效"));
        }

        removeByIds(Arrays.asList(ids));

        List<FileDeleteCommand> fileDeleteCommandList = fileList.stream().map(file -> {
            FileDeleteCommand deleteCommand = new FileDeleteCommand();
            deleteCommand.setRelativePath(file.getRelativePath());
            deleteCommand.setFinalFileName(file.getFinalFileName());
            deleteCommand.setId(file.getId());
            return deleteCommand;
        }).collect(Collectors.toList());

        fileStrategy.delete(fileDeleteCommandList);
    }
}
