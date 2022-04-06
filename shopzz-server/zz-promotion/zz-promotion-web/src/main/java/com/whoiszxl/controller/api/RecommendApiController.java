package com.whoiszxl.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.response.HomeRecommendApiResponse;
import com.whoiszxl.cqrs.response.HomeRecommendVO;
import com.whoiszxl.service.RecommendProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
@Api(tags = "C端:推荐相关接口")
public class RecommendApiController {

    @Autowired
    private RecommendProductService recommendProductService;

    @SaCheckLogin
    @PostMapping("/product/list")
    @ApiOperation(value = "通过专栏ID获取专栏详情", notes = "通过专栏ID获取专栏详情", response = HomeRecommendApiResponse.class)
    public ResponseResult<HomeRecommendApiResponse> homeRecommendList(@RequestBody PageQuery query) {
        List<HomeRecommendVO> list = recommendProductService.getHomeRecommendList(query);
        HomeRecommendApiResponse response = new HomeRecommendApiResponse();
        response.setHomeRecommendList(list);
        return ResponseResult.buildSuccess(response);
    }


    @SaCheckLogin
    @PostMapping("/product/refresh")
    @ApiOperation(value = "刷新推荐商品列表", notes = "需要定时任务实现", response = Boolean.class)
    public ResponseResult<Boolean> homeRecommendRefresh() {
        recommendProductService.homeRecommendRefresh();
        return ResponseResult.buildSuccess();
    }


}

