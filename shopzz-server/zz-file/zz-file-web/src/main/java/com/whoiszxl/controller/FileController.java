package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.query.FmsFileQuery;
import com.whoiszxl.cqrs.response.FmsFileResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.FmsFile;
import com.whoiszxl.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/file")
@Api(tags = "文件上传相关接口")
public class FileController {

    @Autowired
    private FileService fileService;
    
    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "上传文件", response = String.class)
    public ResponseResult<String> upload(
            @RequestParam(value = "file", required = true) MultipartFile file,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "bizId", required = false) String bizId,
            @RequestParam(value = "bizType", required = false) Integer bizType
            ) {

        if(file.isEmpty()) {
            return ResponseResult.buildError("文件内容为空");
        }

        String url = fileService.upload(id, bizId, bizType, file);
        return ResponseResult.buildSuccess(url);
    }

    @SaCheckLogin
    @PostMapping("/delete")
    @ApiOperation(value = "删除文件", notes = "删除文件", response = String.class)
    public ResponseResult<Boolean> delete(@RequestParam(value = "ids[]") Long[] ids) {
        fileService.delete(ids);
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取文件列表", notes = "分页获取文件列表", response = FmsFileResponse.class)
    public ResponseResult<IPage<FmsFileResponse>> list(@RequestBody @Validated FmsFileQuery query) {
        LambdaQueryWrapper<FmsFile> wrapper = new LambdaQueryWrapper<>();
        if(query.getPlatformType() != null) {
            wrapper.like(FmsFile::getPlatformType, query.getPlatformType());
        }
        if(StringUtils.isNotBlank(query.getBizId())) {
            wrapper.eq(FmsFile::getBizId, query.getBizId());
        }
        if(query.getBizType() != null) {
            wrapper.like(FmsFile::getBizType, query.getBizType());
        }
        if(query.getDataType() != null) {
            wrapper.like(FmsFile::getDataType, query.getDataType());
        }

        IPage<FmsFile> result = fileService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<FmsFileResponse> finalResult = result.convert(e -> dozerUtils.map(e, FmsFileResponse.class));
        return ResponseResult.buildSuccess(finalResult);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取文件", notes = "通过主键ID获取文件", response = FmsFileResponse.class)
    public ResponseResult<FmsFileResponse> getSupplierById(@PathVariable Long id) {
        FmsFile warehouse = fileService.getById(id);
        return warehouse == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(warehouse, FmsFileResponse.class));
    }
    
}

