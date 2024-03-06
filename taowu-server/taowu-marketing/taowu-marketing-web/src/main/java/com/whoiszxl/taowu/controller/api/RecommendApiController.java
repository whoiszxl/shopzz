package com.whoiszxl.taowu.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.taowu.common.entity.PageQuery;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.response.HomeRecommendApiResponse;
import com.whoiszxl.taowu.cqrs.response.HomeRecommendVO;
import com.whoiszxl.taowu.service.RecommendProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
@Tag(name = "C端:推荐相关接口")
@RequiredArgsConstructor
public class RecommendApiController {

    private final RecommendProductService recommendProductService;

    @SaCheckLogin
    @PostMapping("/product/list")
    @Operation(summary = "获取首页推荐列表", description = "获取首页推荐列表")
    public ResponseResult<HomeRecommendApiResponse> homeRecommendList(@RequestBody PageQuery query) {
        List<HomeRecommendVO> list = recommendProductService.getHomeRecommendList(query);
        HomeRecommendApiResponse response = new HomeRecommendApiResponse();
        response.setHomeRecommendList(list);
        return ResponseResult.buildSuccess(response);
    }


    @SaCheckLogin
    @PostMapping("/product/refresh")
    @Operation(summary = "刷新推荐商品列表", description = "刷新推荐商品列表")
    public ResponseResult<Boolean> homeRecommendRefresh() {
        recommendProductService.homeRecommendRefresh();
        return ResponseResult.buildSuccess();
    }


}

