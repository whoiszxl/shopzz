package com.whoiszxl.zero.service;

import com.whoiszxl.zero.bean.MyPage;
import com.whoiszxl.zero.entity.Recommend;
import com.whoiszxl.zero.entity.dto.HomeBannerDTO;
import com.whoiszxl.zero.entity.dto.RecommendDTO;
import com.whoiszxl.zero.entity.params.RecommendListParams;
import com.whoiszxl.zero.enums.BannerTypeEnum;

import java.util.List;

/**
 * 主页服务
 *
 * @author whoiszxl
 * @date 2021/4/9
 */
public interface HomeService {

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
    MyPage<Recommend> recommendList(RecommendListParams recommendListParams);
}
