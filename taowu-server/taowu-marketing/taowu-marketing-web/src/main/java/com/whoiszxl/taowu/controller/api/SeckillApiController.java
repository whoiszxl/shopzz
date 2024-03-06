package com.whoiszxl.taowu.controller.api;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.utils.JsonUtil;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.cqrs.cache.SeckillItemListCache;
import com.whoiszxl.taowu.cqrs.cache.SeckillListCache;
import com.whoiszxl.taowu.cqrs.response.SeckillApiResponse;
import com.whoiszxl.taowu.cqrs.response.SeckillItemApiResponse;
import com.whoiszxl.taowu.cqrs.response.SeckillItemVO;
import com.whoiszxl.taowu.cqrs.response.SeckillVO;
import com.whoiszxl.taowu.entity.Seckill;
import com.whoiszxl.taowu.entity.SeckillItem;
import com.whoiszxl.taowu.service.SeckillCachedService;
import com.whoiszxl.taowu.service.SeckillItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * C端:秒杀活动相关接口
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/api/seckill")
@Tag(name = "C端:秒杀相关接口")
@RequiredArgsConstructor
public class SeckillApiController {

    private final SeckillCachedService seckillCachedService;

    private final SeckillItemService seckillItemService;

    private final RedisUtils redisUtils;

    @PostMapping("/list")
    @Operation(summary = "获取秒杀活动列表", description = "获取秒杀活动列表")
    public ResponseResult<SeckillApiResponse> seckillList() {
        SeckillListCache seckillListCache = seckillCachedService.getCachedSeckillList(null);
        if(seckillListCache.isLater()) {
            return ResponseResult.buildError("请稍后再试");
        }

        List<Seckill> seckillList = seckillListCache.getSeckillList();
        List<SeckillVO> seckillVOList = BeanUtil.copyToList(seckillList, SeckillVO.class);
        return ResponseResult.buildSuccess(SeckillApiResponse.build(seckillVOList));
    }


    @PostMapping("/item/listFromDb/{seckillId}")
    @Operation(summary = "从数据库获取秒杀活动下的商品列表", description = "从数据库获取秒杀活动下的商品列表")
    public ResponseResult<SeckillItemApiResponse> seckillItemListFromDb(@PathVariable Long seckillId) {
        List<SeckillItem> seckillItemList = seckillItemService.list(Wrappers.<SeckillItem>lambdaQuery()
                .eq(SeckillItem::getSeckillId, seckillId));
        List<SeckillItemVO> seckillItemVOList = BeanUtil.copyToList(seckillItemList, SeckillItemVO.class);
        return ResponseResult.buildSuccess(SeckillItemApiResponse.build(seckillItemVOList));
    }

    @PostMapping("/item/listFromRedis/{seckillId}")
    @Operation(summary = "从Redis获取秒杀活动下的商品列表", description = "从Redis获取秒杀活动下的商品列表")
    public ResponseResult<SeckillItemApiResponse> seckillItemListFromRedis(@PathVariable Long seckillId) {
        String key = "seckillItemList_testKey_" + seckillId;

        //1. 从Redis中加载列表数据，如果Redis中存在数据，那么直接返回
        String listJson = redisUtils.get(key);
        if(StringUtils.isNotBlank(listJson)) {
            SeckillItemApiResponse seckillItemApiResponse = JSONUtil.toBean(listJson, SeckillItemApiResponse.class);
            return ResponseResult.buildSuccess(seckillItemApiResponse);
        }

        //2. 如果Redis中没有数据，则从数据库中加载
        List<SeckillItem> seckillItemList = seckillItemService.list(Wrappers.<SeckillItem>lambdaQuery()
                .eq(SeckillItem::getSeckillId, seckillId));
        List<SeckillItemVO> seckillItemVOList = BeanUtil.copyToList(seckillItemList, SeckillItemVO.class);
        SeckillItemApiResponse response = SeckillItemApiResponse.build(seckillItemVOList);
        redisUtils.set(key, JsonUtil.toJson(response));

        return ResponseResult.buildSuccess(response);
    }

    //HashMap
    Cache<String, SeckillItemApiResponse> seckillItemListLocalCache = Caffeine.newBuilder()
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .maximumSize(100)
            .build();

    @PostMapping("/item/listFromLocal/{seckillId}")
    @Operation(summary = "从本地缓存获取秒杀活动下的商品列表", description = "从本地缓存获取秒杀活动下的商品列表")
    public ResponseResult<SeckillItemApiResponse> seckillItemListFromLocal(@PathVariable Long seckillId) {
        String key = "seckillItemList_testKey_" + seckillId;

        //0. 从本地缓存加载列表数据
        SeckillItemApiResponse localResponse = seckillItemListLocalCache.getIfPresent(key);
        if(localResponse != null) {
            return ResponseResult.buildSuccess(localResponse);
        }

        //1. 从Redis中加载列表数据，如果Redis中存在数据，那么直接返回
        String listJson = redisUtils.get(key);
        if(StringUtils.isNotBlank(listJson)) {
            //mapstruct
            SeckillItemApiResponse seckillItemApiResponse = JSONUtil.toBean(listJson, SeckillItemApiResponse.class);
            seckillItemListLocalCache.put(key, seckillItemApiResponse);
            return ResponseResult.buildSuccess(seckillItemApiResponse);
        }

        //2. 如果Redis中没有数据，则从数据库中加载
        List<SeckillItem> seckillItemList = seckillItemService.list(Wrappers.<SeckillItem>lambdaQuery()
                .eq(SeckillItem::getSeckillId, seckillId));
        List<SeckillItemVO> seckillItemVOList = BeanUtil.copyToList(seckillItemList, SeckillItemVO.class);
        SeckillItemApiResponse response = SeckillItemApiResponse.build(seckillItemVOList);
        redisUtils.set(key, JsonUtil.toJson(response));

        return ResponseResult.buildSuccess(response);
    }


    @PostMapping("/item/list/{seckillId}/{version}")
    @Operation(summary = "获取秒杀活动下的商品列表", description = "获取秒杀活动下的商品列表")
    public ResponseResult<SeckillItemApiResponse> seckillItemList(@PathVariable Long seckillId, @PathVariable Long version) {
        SeckillItemListCache seckillItemListCache = seckillCachedService.getCachedSeckillItemList(seckillId, version);
        if(seckillItemListCache.isLater()) {
            return ResponseResult.buildError("请稍后再试");
        }

        List<SeckillItem> seckillItemList = seckillItemListCache.getSeckillItemList();
        List<SeckillItemVO> seckillItemVOList = BeanUtil.copyToList(seckillItemList, SeckillItemVO.class);
        return ResponseResult.buildSuccess(SeckillItemApiResponse.build(seckillItemVOList));
    }
}

