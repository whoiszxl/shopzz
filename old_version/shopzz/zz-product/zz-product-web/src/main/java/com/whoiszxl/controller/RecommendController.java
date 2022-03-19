package com.whoiszxl.controller;


import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.HomeBannerDTO;
import com.whoiszxl.entity.Recommend;
import com.whoiszxl.entity.query.RecommendListQuery;
import com.whoiszxl.entity.vo.HomeBannerVO;
import com.whoiszxl.entity.vo.HomeRecommendVO;
import com.whoiszxl.entity.vo.IndexBannerVO;
import com.whoiszxl.entity.vo.RecommendVO;
import com.whoiszxl.enums.BannerTypeEnum;
import com.whoiszxl.service.RecommendService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 营销推荐表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-12-15
 */
@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private DozerUtils dozerUtils;

    @PostMapping("/app/index")
    @ApiOperation(value = "app主页Banner数据")
    public ResponseResult<IndexBannerVO> homeAppIndex() {
        List<HomeBannerDTO> appBanners = recommendService.getBannerByType(BannerTypeEnum.APP_BANNER);
        List<HomeBannerDTO> navigationBanners = recommendService.getBannerByType(BannerTypeEnum.APP_NAVIGATION);

        List<HomeBannerVO> homeBannerVOS = dozerUtils.mapList(appBanners, HomeBannerVO.class);
        List<HomeBannerVO> navigationBannerVOS = dozerUtils.mapList(navigationBanners, HomeBannerVO.class);
        return ResponseResult.buildSuccess(
                new IndexBannerVO(homeBannerVOS, navigationBannerVOS)
        );
    }

    @PostMapping("/list")
    @ApiOperation(value = "app主页Banner数据")
    public ResponseResult<HomeRecommendVO> recommendList() {
        HomeRecommendVO homeRecommendVO = recommendService.recommendList();
        return ResponseResult.buildSuccess(homeRecommendVO);
    }


    @PostMapping("/banner/{type}")
    @ApiOperation(value = "根据banner类型获取banner列表")
    public ResponseResult<IndexBannerVO> banner(@PathVariable Integer type) {
        List<HomeBannerDTO> smallBanners = recommendService.getBannerByType(BannerTypeEnum.getType(type));
        List<HomeBannerVO> homeBannerVOS = dozerUtils.mapList(smallBanners, HomeBannerVO.class);
        return ResponseResult.buildSuccess(
                new IndexBannerVO(homeBannerVOS, null)
        );
    }
}

