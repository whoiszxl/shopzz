package com.whoiszxl.taowu.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.file.cqrs.response.UploadResponse;
import com.whoiszxl.taowu.file.entity.FmsFile;
import com.whoiszxl.taowu.file.mapper.FileMapper;
import com.whoiszxl.taowu.file.service.FileService;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.utils.StrPoolUtil;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.whoiszxl.taowu.common.utils.DateUtils.DEFAULT_MONTH_FORMAT_SLASH;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-23
 */
@Service
@RequiredArgsConstructor
public class FileServiceImpl extends ServiceImpl<FileMapper, FmsFile> implements FileService {

    private final FileStorageService fileStorageService;

    private final TokenHelper tokenHelper;

    private static final String FILE_SPLIT = ".";

    private static final Integer MAX_SIZE = 5 * 1024 * 1024;

    @Override
    public UploadResponse upload(String objectId, String objectType, MultipartFile file) {
        // 基础校验
        if(!Objects.requireNonNull(file.getOriginalFilename()).contains(FILE_SPLIT)) {
            ExceptionCatcher.catchServiceEx("禁止上传无后缀文件");
        }
        if(file.getSize() >= MAX_SIZE) {
            ExceptionCatcher.catchServiceEx("禁止上传大于5MB的文件");
        }

        Long memberId = tokenHelper.getAppMemberId();
        Assert.isTrue(memberId != null, "请先登录");

        // 文件上传
        String relativePath = Paths.get(LocalDate.now().format(DateTimeFormatter.ofPattern(DEFAULT_MONTH_FORMAT_SLASH))).toString();
        String relativeFileName = relativePath + StrPoolUtil.SLASH;
        relativeFileName = StrUtil.replace(relativeFileName, "\\\\", StrPoolUtil.SLASH);
        relativeFileName = StrUtil.replace(relativeFileName, "\\", StrPoolUtil.SLASH);

        FileInfo fileInfo = null;
        try {
            fileInfo = fileStorageService.of(file, URLDecoder.decode(file.getOriginalFilename(), "UTF-8"))
                    .setPath(relativeFileName)
                    .setObjectId(objectId)
                    .setObjectType(objectType)
                    .upload();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        UploadResponse uploadResponse = BeanUtil.copyProperties(fileInfo, UploadResponse.class);
        return uploadResponse;
    }

    @Override
    public void delete(Long[] ids) {
        //TODO 对象存储文件批量删除
    }
}
