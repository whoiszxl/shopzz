package com.whoiszxl.taowu.controller.api;


import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.response.IndexSpuResponse;
import com.whoiszxl.taowu.cqrs.response.SpuDetailResponse;
import com.whoiszxl.taowu.service.SpuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@Tag(name = "C端:SPU相关接口")
@RequiredArgsConstructor
public class SpuApiController {

    private final SpuService spuService;


    @GetMapping("/index/list/{page}")
    @Operation(summary = "获取首页最新商品SPU的列表", description = "获取首页最新商品SPU的列表")
    public ResponseResult<List<IndexSpuResponse>> indexSpuList(@PathVariable("page") Integer page) {
        List<IndexSpuResponse> result = spuService.indexSpuList(page, 10);
        return ResponseResult.buildSuccess(result);
    }

    @PostMapping("/detail/{spuId}")
    @Operation(summary = "通过SPU ID获取商品详情", description = "通过SPU ID获取商品详情")
    public ResponseResult<SpuDetailResponse> detail(@PathVariable Long spuId) {
        SpuDetailResponse spuDetailResponse = spuService.detail(spuId);
        return ResponseResult.buildSuccess(spuDetailResponse);
    }
}

