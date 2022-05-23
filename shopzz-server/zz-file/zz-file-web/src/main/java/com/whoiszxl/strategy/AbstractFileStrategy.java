package com.whoiszxl.strategy;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.FileDeleteCommand;
import com.whoiszxl.entity.FmsFile;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.utils.DateUtils;
import com.whoiszxl.utils.FileTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 抽象文件策略
 *
 * @author whoiszxl
 * @date 2022/3/23
 */
@Slf4j
public abstract class AbstractFileStrategy implements FileStrategy {

    private static final String FILE_SPLIT = ".";

    private static final Integer MAX_SIZE = 5 * 1024 * 1024;


    @Override
    public FmsFile upload(MultipartFile file) {
        try{
            if(!file.getOriginalFilename().contains(FILE_SPLIT)) {
                ExceptionCatcher.catchValidateEx(ResponseResult.buildError("不允许上传无后缀文件"));
            }

            if(file.getSize() >= MAX_SIZE) {
                ExceptionCatcher.catchValidateEx(ResponseResult.buildError("不允许上传大于5MB的文件"));
            }

            FmsFile fmsFile = new FmsFile();
            fmsFile.setDataType(FileTypeUtil.getFileType(file.getContentType()).getNum());
            fmsFile.setOriginalFileName(file.getOriginalFilename());
            fmsFile.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
            fmsFile.setSize(file.getSize());

            LocalDateTime now = LocalDateTime.now();
            fmsFile.setCreatedYear(DateUtils.formatAsYear3(now));
            fmsFile.setCreatedMonth(DateUtils.formatAsYearMonth(now));
            fmsFile.setCreatedDay(DateUtils.formatAsDate(now));
            fmsFile.setCreatedAt(now);
            fmsFile.setUpdatedAt(now);

            uploadFile(fmsFile, file);
            return fmsFile;
        }catch (Exception e) {
            log.error("文件上传失败", e);
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("文件上传失败"));
        }
        return null;
    }


    @Override
    public boolean delete(List<FileDeleteCommand> commandList) {
        if(commandList.isEmpty()) {
            return true;
        }

        boolean flag = false;

        for (FileDeleteCommand fileDeleteCommand : commandList) {
            try {
                delete(fileDeleteCommand);
                flag = true;
            }catch (Exception e) {
                log.error("删除文件失败", e);
            }
        }

        return flag;
    }

    /**
     * 实际执行上传文件的方法
     * @param fmsFile 数据库文件实体
     * @param multipartFile 文件
     */
    protected abstract void uploadFile(FmsFile fmsFile, MultipartFile multipartFile);


    /**
     * 实际执行文件删除的方法
     * @param deleteCommand 删除命令参数
     */
    protected abstract void delete(FileDeleteCommand deleteCommand);
}
