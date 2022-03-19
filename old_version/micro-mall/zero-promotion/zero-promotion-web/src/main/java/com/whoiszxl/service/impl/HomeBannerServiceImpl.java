package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.dto.HomeBannerDTO;
import com.whoiszxl.entity.HomeBanner;
import com.whoiszxl.entity.Recommend;
import com.whoiszxl.entity.query.RecommendListQuery;
import com.whoiszxl.enums.BannerTypeEnum;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.mapper.HomeBannerMapper;
import com.whoiszxl.service.HomeBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.service.RecommendService;
import com.whoiszxl.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页轮播表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Service
public class HomeBannerServiceImpl extends ServiceImpl<HomeBannerMapper, HomeBanner> implements HomeBannerService {

    @Autowired
    private RecommendService recommendService;

    @Override
    public List<HomeBannerDTO> getBannerByType(BannerTypeEnum bannerType) {
        LambdaQueryWrapper<HomeBanner> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HomeBanner::getType, bannerType.getCode());
        queryWrapper.eq(HomeBanner::getStatus, StatusEnum.OPEN.getCode());
        queryWrapper.orderByDesc(HomeBanner::getSort);
        List<HomeBanner> homeBannerList = this.list(queryWrapper);
        List<HomeBannerDTO> homeBannerDTOList = BeanCopierUtils.copyListProperties(homeBannerList, HomeBannerDTO::new);
        return homeBannerDTOList;
    }

    @Override
    public List<Recommend> recommendList(RecommendListQuery recommendListQuery) {
        LambdaQueryWrapper<Recommend> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Recommend::getSort);
        queryWrapper.eq(Recommend::getType, recommendListQuery.getRecommendType());
        List<Recommend> recommendList = recommendService.list(queryWrapper);
        return recommendList;
    }
}
