package com.whoiszxl.taowu.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.response.IndexBannerResponse;
import com.whoiszxl.taowu.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/banner")
@Tag(name = "C端:轮播图相关接口")
@RequiredArgsConstructor
public class BannerApiController {

    private final BannerService bannerService;

    @SaCheckLogin
    @PostMapping("/app/index")
    @Operation(summary = "获取app主页Banner数据", description = "获取app主页Banner数据")
    public ResponseResult<IndexBannerResponse> appIndex() {
        IndexBannerResponse indexBannerResponse = bannerService.getIndexBanner();
        return ResponseResult.buildSuccess(indexBannerResponse);

    }

}

