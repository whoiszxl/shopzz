package com.whoiszxl.service;

import com.whoiszxl.dto.HomeBannerDTO;
import com.whoiszxl.entity.Recommend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.query.RecommendListQuery;
import com.whoiszxl.entity.vo.HomeRecommendVO;
import com.whoiszxl.enums.BannerTypeEnum;

import java.util.List;

/**
 * <p>
 * 营销推荐表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-12-15
 */
public interface RecommendService extends IService<Recommend> {

    /**
     * 通过banner类型获取banner列表
     * @param bannerType banner类型枚举
     * @return
     */
    List<HomeBannerDTO> getBannerByType(BannerTypeEnum bannerType);

    /**
     * 查询推荐列表
     * @return
     */
    HomeRecommendVO recommendList();
}
