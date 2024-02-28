package com.whoiszxl.taowu.file.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.entity.response.PageResponse;
import com.whoiszxl.taowu.file.cqrs.query.FmsFileQuery;
import com.whoiszxl.taowu.file.cqrs.response.FmsFileResponse;
import com.whoiszxl.taowu.file.cqrs.response.UploadResponse;
import com.whoiszxl.taowu.file.entity.FmsFile;
import com.whoiszxl.taowu.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-23
 */
@RestController
@RequestMapping("/api/file")
@Tag(name = "文件上传 API")
@SaIgnore
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @SaCheckLogin
    @PostMapping("/upload")
    @Operation(summary = "上传文件", description = "上传文件")
    public ResponseResult<UploadResponse> upload(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "objectId", required = false) String objectId,
            @RequestParam(value = "objectType", required = false) String objectType) {

        if(file.isEmpty()) {
            return ResponseResult.buildError("文件内容为空");
        }

        UploadResponse response = fileService.upload(objectId, objectType, file);
        return ResponseResult.buildSuccess(response);
    }

    @SaCheckLogin
    @PostMapping("/delete")
    @Operation(summary = "删除文件", description = "删除文件")
    public ResponseResult<Boolean> delete(@RequestParam(value = "ids[]") Long[] ids) {
        fileService.delete(ids);
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @PostMapping("/list")
    @Operation(summary = "分页获取文件列表", description = "分页获取文件列表")
    public ResponseResult<PageResponse<FmsFileResponse>> list(@RequestBody @Validated FmsFileQuery query) {
        LambdaQueryWrapper<FmsFile> wrapper = new LambdaQueryWrapper<>();
        if(query.getPlatform() != null) {
            wrapper.like(FmsFile::getPlatform, query.getPlatform());
        }
        if(StringUtils.isNotBlank(query.getObjectId())) {
            wrapper.eq(FmsFile::getObjectId, query.getObjectId());
        }
        if(query.getObjectType() != null) {
            wrapper.like(FmsFile::getObjectType, query.getObjectType());
        }

        IPage<FmsFile> result = fileService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        return ResponseResult.buildSuccess(PageResponse.convert(result, FmsFileResponse.class));
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @Operation(summary = "通过主键ID获取文件", description = "通过主键ID获取文件")
    public ResponseResult<FmsFileResponse> getSupplierById(@PathVariable Long id) {
        FmsFile file = fileService.getById(id);
        return file == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(BeanUtil.copyProperties(file, FmsFileResponse.class));
    }
    
}

