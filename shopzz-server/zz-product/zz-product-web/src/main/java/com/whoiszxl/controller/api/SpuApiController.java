package com.whoiszxl.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.SpuSaveCommand;
import com.whoiszxl.cqrs.command.SpuUpdateCommand;
import com.whoiszxl.cqrs.query.SpuQuery;
import com.whoiszxl.cqrs.response.SpuDetailResponse;
import com.whoiszxl.entity.Spu;
import com.whoiszxl.service.SpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品基础信息表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@RestController
@RequestMapping("/api/spu")
@Api(tags = "C端:SPU相关接口")
public class SpuApiController {

    @Autowired
    private SpuService spuService;

    @PostMapping("/detail/{spuId}")
    @ApiOperation(value = "通过SPU ID获取商品详情", notes = "通过SPU ID获取商品详情", response = SpuDetailResponse.class)
    public ResponseResult<SpuDetailResponse> detail(@PathVariable Long spuId) {
        SpuDetailResponse spuDetailResponse = spuService.detail(spuId);
        return ResponseResult.buildSuccess(spuDetailResponse);
    }
}

