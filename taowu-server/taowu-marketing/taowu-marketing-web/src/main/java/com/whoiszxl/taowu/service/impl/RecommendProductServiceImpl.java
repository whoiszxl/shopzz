package com.whoiszxl.taowu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.entity.PageQuery;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.utils.BeanUtil;
import com.whoiszxl.taowu.common.utils.JsonUtil;
import com.whoiszxl.taowu.common.utils.ParamUtils;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.cqrs.response.HomeRecommendVO;
import com.whoiszxl.taowu.dto.SpuFeignDTO;
import com.whoiszxl.taowu.entity.RecommendProduct;
import com.whoiszxl.taowu.feign.ProductFeignClient;
import com.whoiszxl.taowu.mapper.RecommendProductMapper;
import com.whoiszxl.taowu.service.RecommendProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 首页通用推荐商品表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendProductServiceImpl extends ServiceImpl<RecommendProductMapper, RecommendProduct> implements RecommendProductService {

    private final RedisUtils redisUtils;

    private final RecommendProductMapper recommendProductMapper;

    private final ProductFeignClient productFeignClient;

    @Override
    public List<HomeRecommendVO> getHomeRecommendList(PageQuery query) {
        List<String> responseList = null;
        Integer page = query.getPage();
        Integer size = query.getSize();
        int start = (page - 1) * size;
        int end = start + size - 1;

        try{
            responseList = redisUtils.lRange(RedisPrefixConstants.Marketing.HOME_RECOMMEND_A, start, end);
            if(CollectionUtils.isEmpty(responseList)) {
                log.info("HOME_RECOMMEND_A 缓存失效，查询HOME_RECOMMEND_B缓存");
                responseList = redisUtils.lRange(RedisPrefixConstants.Marketing.HOME_RECOMMEND_B, start, end);
            }

        }catch (Exception e) {
            log.error("获取首页推荐商品列表失败", e);
        }

        if(responseList.isEmpty()) {
            return CollectionUtil.newArrayList();
        }

        List<HomeRecommendVO> resultList = responseList.stream().map(e -> JsonUtil.fromJson(e, HomeRecommendVO.class)).collect(Collectors.toList());

        return resultList;
    }

    @Override
    public void homeRecommendRefresh() {
        List<RecommendProduct> recommendProductList = recommendProductMapper.selectList(null);

        List<Long> spuIds = recommendProductList.stream().map(RecommendProduct::getSpuId).collect(Collectors.toList());
        String feignParams = ParamUtils.array2Str(spuIds);
        ResponseResult<List<SpuFeignDTO>> feignResponse = productFeignClient.getSpuListBySpuIdList(feignParams);
        if(!feignResponse.isOk()) {
            ExceptionCatcher.catchServiceEx(feignResponse.getMessage());
        }

        List<SpuFeignDTO> spuFeignDTOList = feignResponse.getData();
        List<HomeRecommendVO> homeRecommendList = BeanUtil.copyToList(spuFeignDTOList, HomeRecommendVO.class);

        List<String> jsonList = homeRecommendList.stream().map(JsonUtil::toJson).collect(Collectors.toList());

        redisUtils.delete(RedisPrefixConstants.Marketing.HOME_RECOMMEND_B);
        redisUtils.lLeftPushAll(RedisPrefixConstants.Marketing.HOME_RECOMMEND_B, jsonList);
        redisUtils.expire(RedisPrefixConstants.Marketing.HOME_RECOMMEND_B, 10, TimeUnit.DAYS);

        redisUtils.delete(RedisPrefixConstants.Marketing.HOME_RECOMMEND_A);
        redisUtils.lLeftPushAll(RedisPrefixConstants.Marketing.HOME_RECOMMEND_A, jsonList);
        redisUtils.expire(RedisPrefixConstants.Marketing.HOME_RECOMMEND_A, 10, TimeUnit.DAYS);
    }
}
