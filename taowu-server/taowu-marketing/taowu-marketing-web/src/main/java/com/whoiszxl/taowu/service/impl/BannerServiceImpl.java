package com.whoiszxl.taowu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.enums.StatusEnum;
import com.whoiszxl.taowu.common.utils.BeanUtil;
import com.whoiszxl.taowu.common.utils.JsonUtil;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.cqrs.response.HomeBannerVO;
import com.whoiszxl.taowu.cqrs.response.IndexBannerResponse;
import com.whoiszxl.taowu.entity.Banner;
import com.whoiszxl.taowu.enums.BannerTypeEnum;
import com.whoiszxl.taowu.mapper.BannerMapper;
import com.whoiszxl.taowu.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 轮播表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Service
@RequiredArgsConstructor
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    private final RedisUtils redisUtils;

    @Override
    public List<HomeBannerVO> getBannerByType(BannerTypeEnum bannerType) {
        LambdaQueryWrapper<Banner> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Banner::getType, bannerType.getCode());
        queryWrapper.eq(Banner::getStatus, StatusEnum.OPEN.getCode());
        queryWrapper.orderByDesc(Banner::getSort);
        List<Banner> homeBannerList = this.list(queryWrapper);
        List<HomeBannerVO> homeBannerVOList = BeanUtil.copyToList(homeBannerList, HomeBannerVO.class);
        return homeBannerVOList;
    }

    @Override
    public IndexBannerResponse getIndexBanner() {

        //1. 先从Redis查询，如果存在直接反序列化返回
        String bannerJson = redisUtils.get(RedisPrefixConstants.Marketing.APP_BANNER);

        if(StringUtils.isBlank(bannerJson)) {

            //2. 加锁，防止瞬时大量请求进来导致缓存击穿
            synchronized (BannerServiceImpl.class) {

                //3. 二次查询Redis，防止第二步排队中的请求再次查询数据库
                bannerJson = redisUtils.get(RedisPrefixConstants.Marketing.APP_BANNER);
                if(StringUtils.isBlank(bannerJson)) {

                    //4. 从数据库中查询数据，通过上述步骤保证瞬时只有一个请求能够查询到数据库
                    List<HomeBannerVO> appBanners = getBannerByType(BannerTypeEnum.APP_BANNER);
                    IndexBannerResponse indexBannerResponse = new IndexBannerResponse(appBanners);
                    if(ObjectUtil.isEmpty(appBanners)) {
                        return null;
                    }
                    bannerJson = JsonUtil.toJson(indexBannerResponse);

                    //5. 将从数据库查询出来的数据保存到Redis中
                    redisUtils.set(RedisPrefixConstants.Marketing.APP_BANNER, bannerJson);
                }
            }
        }
        return JsonUtil.fromJson(bannerJson, IndexBannerResponse.class);
    }
}
