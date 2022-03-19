package com.whoiszxl.controller;


import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dto.HomeBannerDTO;
import com.whoiszxl.entity.Recommend;
import com.whoiszxl.entity.query.RecommendListQuery;
import com.whoiszxl.entity.vo.HomeBannerVO;
import com.whoiszxl.entity.vo.IndexBannerVO;
import com.whoiszxl.entity.vo.RecommendVO;
import com.whoiszxl.enums.BannerTypeEnum;
import com.whoiszxl.service.HomeBannerService;
import com.whoiszxl.utils.BeanCopierUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeBannerService homeBannerService;

    @PostMapping("/app/index")
    @ApiOperation(value = "app主页Banner数据")
    public ResponseResult<IndexBannerVO> homeAppIndex() {
        List<HomeBannerDTO> appBanners = homeBannerService.getBannerByType(BannerTypeEnum.APP_BANNER);
        List<HomeBannerDTO> navigationBanners = homeBannerService.getBannerByType(BannerTypeEnum.APP_NAVIGATION);

        List<HomeBannerVO> homeBannerVOS = BeanCopierUtils.copyListProperties(appBanners, HomeBannerVO::new);
        List<HomeBannerVO> navigationBannerVOS = BeanCopierUtils.copyListProperties(navigationBanners, HomeBannerVO::new);

        return ResponseResult.buildSuccess(
                new IndexBannerVO(homeBannerVOS, navigationBannerVOS)
        );
    }

    @PostMapping("/recommend/list")
    @ApiOperation(value = "app主页Banner数据")
    public ResponseResult<List<RecommendVO>> recommendList(@RequestBody RecommendListQuery recommendListQuery) {
        List<Recommend> recommendList = homeBannerService.recommendList(recommendListQuery);
        List<RecommendVO> recommendVos = BeanCopierUtils.copyListProperties(recommendList, RecommendVO::new);
        return ResponseResult.buildSuccess(recommendVos);
    }

}

