package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.constants.BannerTypeEnum;
import com.whoiszxl.cqrs.response.HomeBannerVO;
import com.whoiszxl.cqrs.response.IndexBannerResponse;
import com.whoiszxl.entity.Banner;

import java.util.List;

/**
 * <p>
 * 轮播表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
public interface BannerService extends IService<Banner> {

    /**
     * 通过banner类型获取banner列表
     * @param bannerType banner类型枚举
     * @return
     */
    List<HomeBannerVO> getBannerByType(BannerTypeEnum bannerType);

    /**
     * 获取app首页banner与nav
     * @return
     */
    IndexBannerResponse getIndexBanner();

}
