package com.whoiszxl.zero.controller;

import com.whoiszxl.zero.bean.MyPage;
import com.whoiszxl.zero.bean.Result;
import com.whoiszxl.zero.entity.dto.HomeBannerDTO;
import com.whoiszxl.zero.entity.params.RecommendListParams;
import com.whoiszxl.zero.entity.vo.HomeBannerVO;
import com.whoiszxl.zero.entity.vo.IndexBannerVO;
import com.whoiszxl.zero.entity.vo.RecommendVO;
import com.whoiszxl.zero.enums.BannerTypeEnum;
import com.whoiszxl.zero.service.HomeService;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 主页接口
 *
 * @author whoiszxl
 * @date 2021/4/9
 */
@Api(tags = "主页接口")
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @PostMapping("/app/index")
    @ApiOperation(value = "app主页Banner数据")
    public Result<IndexBannerVO> homeAppIndex() {
        List<HomeBannerDTO> appBanners = homeService.getBannerByType(BannerTypeEnum.APP_BANNER);
        List<HomeBannerDTO> navigationBanners = homeService.getBannerByType(BannerTypeEnum.APP_NAVIGATION);

        List<HomeBannerVO> homeBannerVOS = BeanCopierUtils.copyListProperties(appBanners, HomeBannerVO::new);
        List<HomeBannerVO> navigationBannerVOS = BeanCopierUtils.copyListProperties(navigationBanners, HomeBannerVO::new);

        return Result.buildSuccess(
                new IndexBannerVO(homeBannerVOS, navigationBannerVOS)
        );
    }

    @PostMapping("/recommend/list")
    @ApiOperation(value = "app主页Banner数据")
    public Result<MyPage> recommendList(@RequestBody RecommendListParams recommendListParams) {
        MyPage result = homeService.recommendList(recommendListParams);
        List<RecommendVO> recommendVos = BeanCopierUtils.copyListProperties(result.getContent(), RecommendVO::new);
        result.setContent(recommendVos);
        return Result.buildSuccess(result);
    }

}
