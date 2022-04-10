package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.response.HomeRecommendVO;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.SpuFeignDTO;
import com.whoiszxl.entity.RecommendProduct;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.feign.ProductFeignClient;
import com.whoiszxl.mapper.RecommendProductMapper;
import com.whoiszxl.service.RecommendProductService;
import com.whoiszxl.utils.AssertUtils;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.ParamUtils;
import com.whoiszxl.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RecommendProductServiceImpl extends ServiceImpl<RecommendProductMapper, RecommendProduct> implements RecommendProductService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RecommendProductMapper recommendProductMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public List<HomeRecommendVO> getHomeRecommendList(PageQuery query) {
        List<String> responseList = null;
        Integer page = query.getPage();
        Integer size = query.getSize();
        int start = (page - 1) * size;
        int end = start + size - 1;

        try{
            responseList = redisUtils.lRange(RedisKeyPrefixConstants.HOME_RECOMMEND_A, start, end);
            if(CollectionUtils.isEmpty(responseList)) {
                log.info("HOME_RECOMMEND_A 缓存失效，查询HOME_RECOMMEND_B缓存");
                responseList = redisUtils.lRange(RedisKeyPrefixConstants.HOME_RECOMMEND_B, start, end);
            }

        }catch (Exception e) {
            log.error("获取首页推荐商品列表失败", e);
        }

        AssertUtils.isNotNull(responseList, "推荐商品列表为空");
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
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError(feignResponse.getMessage()));
        }

        List<SpuFeignDTO> spuFeignDTOList = feignResponse.getData();
        List<HomeRecommendVO> homeRecommendList = dozerUtils.mapList(spuFeignDTOList, HomeRecommendVO.class);

        List<String> jsonList = homeRecommendList.stream().map(JsonUtil::toJson).collect(Collectors.toList());

        redisUtils.delete(RedisKeyPrefixConstants.HOME_RECOMMEND_B);
        redisUtils.lLeftPushAll(RedisKeyPrefixConstants.HOME_RECOMMEND_B, jsonList);
        redisUtils.expire(RedisKeyPrefixConstants.HOME_RECOMMEND_B, 10, TimeUnit.DAYS);

        redisUtils.delete(RedisKeyPrefixConstants.HOME_RECOMMEND_A);
        redisUtils.lLeftPushAll(RedisKeyPrefixConstants.HOME_RECOMMEND_A, jsonList);
        redisUtils.expire(RedisKeyPrefixConstants.HOME_RECOMMEND_A, 10, TimeUnit.DAYS);
    }
}
