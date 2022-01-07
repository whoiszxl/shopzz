package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.HomeBannerDTO;
import com.whoiszxl.entity.BannerInfo;
import com.whoiszxl.entity.Recommend;
import com.whoiszxl.entity.query.RecommendListQuery;
import com.whoiszxl.entity.vo.HomeRecommendVO;
import com.whoiszxl.entity.vo.RecommendVO;
import com.whoiszxl.enums.BannerTypeEnum;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.mapper.BannerInfoMapper;
import com.whoiszxl.mapper.RecommendMapper;
import com.whoiszxl.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 营销推荐表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-12-15
 */
@Service
public class RecommendServiceImpl extends ServiceImpl<RecommendMapper, Recommend> implements RecommendService {

    @Autowired
    private RecommendMapper recommendMapper;

    @Autowired
    private BannerInfoMapper bannerInfoMapper;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public List<HomeBannerDTO> getBannerByType(BannerTypeEnum bannerType) {
        LambdaQueryWrapper<BannerInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BannerInfo::getType, bannerType.getCode());
        queryWrapper.eq(BannerInfo::getStatus, StatusEnum.OPEN.getCode());
        queryWrapper.orderByDesc(BannerInfo::getSort);
        List<BannerInfo> homeBannerList = bannerInfoMapper.selectList(queryWrapper);
        List<HomeBannerDTO> homeBannerDTOList = dozerUtils.mapList(homeBannerList, HomeBannerDTO.class);
        return homeBannerDTOList;
    }

    @Override
    public HomeRecommendVO recommendList() {
        LambdaQueryWrapper<Recommend> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Recommend::getSort);
        queryWrapper.eq(Recommend::getType, 1);
        List<Recommend> hotRecommendList = recommendMapper.selectList(queryWrapper);

        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Recommend::getSort);
        queryWrapper.eq(Recommend::getType, 2);
        List<Recommend> niceRecommendList = recommendMapper.selectList(queryWrapper);

        List<RecommendVO> hotRecommendVos = dozerUtils.mapList(hotRecommendList, RecommendVO.class);
        List<RecommendVO> niceRecommendVos = dozerUtils.mapList(niceRecommendList, RecommendVO.class);

        HomeRecommendVO homeRecommendVO = new HomeRecommendVO();
        homeRecommendVO.setHotRecommendList(hotRecommendVos);
        homeRecommendVO.setNiceRecommendList(niceRecommendVos);
        return homeRecommendVO;
    }
}
