package com.whoiszxl.service;

import com.whoiszxl.dto.HomeBannerDTO;
import com.whoiszxl.entity.HomeBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.Recommend;
import com.whoiszxl.entity.query.RecommendListQuery;
import com.whoiszxl.enums.BannerTypeEnum;

import java.util.List;

/**
 * <p>
 * 首页轮播表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
public interface HomeBannerService extends IService<HomeBanner> {

    /**
     * 通过banner类型获取banner列表
     * @param bannerType banner类型枚举
     * @return
     */
    List<HomeBannerDTO> getBannerByType(BannerTypeEnum bannerType);

    /**
     * 分页查询推荐列表
     * @param recommendListParams
     * @return
     */
    List<Recommend> recommendList(RecommendListQuery recommendListQuery);

}
