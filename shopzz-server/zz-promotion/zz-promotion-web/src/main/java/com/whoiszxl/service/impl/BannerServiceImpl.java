package com.whoiszxl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.constants.BannerTypeEnum;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.response.HomeBannerVO;
import com.whoiszxl.cqrs.response.IndexBannerResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Banner;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.mapper.BannerMapper;
import com.whoiszxl.service.BannerService;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<HomeBannerVO> getBannerByType(BannerTypeEnum bannerType) {
        LambdaQueryWrapper<Banner> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Banner::getType, bannerType.getCode());
        queryWrapper.eq(Banner::getStatus, StatusEnum.OPEN.getCode());
        queryWrapper.orderByDesc(Banner::getSort);
        List<Banner> homeBannerList = this.list(queryWrapper);
        List<HomeBannerVO> homeBannerVOList = dozerUtils.mapList(homeBannerList, HomeBannerVO.class);
        return homeBannerVOList;
    }

    @Override
    public IndexBannerResponse getIndexBanner() {

        //1. 先从Redis查询，如果存在直接反序列化返回
        String bannerJson = redisUtils.get(RedisKeyPrefixConstants.APP_BANNER);

        if(StringUtils.isBlank(bannerJson)) {

            //2. 加锁，防止瞬时大量请求进来导致缓存击穿
            synchronized (BannerServiceImpl.class) {

                //3. 二次查询Redis，防止第二步排队中的请求再次查询数据库
                bannerJson = redisUtils.get(RedisKeyPrefixConstants.APP_BANNER);
                if(StringUtils.isBlank(bannerJson)) {

                    //4. 从数据库中查询数据，通过上述步骤保证瞬时只有一个请求能够查询到数据库
                    List<HomeBannerVO> appBanners = getBannerByType(BannerTypeEnum.APP_BANNER);
                    List<HomeBannerVO> navBanners = getBannerByType(BannerTypeEnum.APP_NAVIGATION);
                    IndexBannerResponse indexBannerResponse = new IndexBannerResponse(appBanners, navBanners);
                    if(ObjectUtil.isEmpty(appBanners) || ObjectUtil.isEmpty(navBanners)) {
                        return null;
                    }
                    bannerJson = JsonUtil.toJson(indexBannerResponse);

                    //5. 将从数据库查询出来的数据保存到Redis中
                    redisUtils.set(RedisKeyPrefixConstants.APP_BANNER, bannerJson);
                }
            }
        }
        return JsonUtil.fromJson(bannerJson, IndexBannerResponse.class);
    }
}
