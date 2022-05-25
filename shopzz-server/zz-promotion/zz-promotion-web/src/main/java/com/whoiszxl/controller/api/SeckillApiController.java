package com.whoiszxl.controller.api;


import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.cache.SeckillItemListCache;
import com.whoiszxl.cqrs.cache.SeckillListCache;
import com.whoiszxl.cqrs.response.SeckillApiResponse;
import com.whoiszxl.cqrs.response.SeckillItemApiResponse;
import com.whoiszxl.cqrs.response.SeckillItemVO;
import com.whoiszxl.cqrs.response.SeckillVO;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Seckill;
import com.whoiszxl.entity.SeckillItem;
import com.whoiszxl.service.SeckillCachedService;
import com.whoiszxl.service.SeckillItemService;
import com.whoiszxl.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
@Api(tags = "C端:秒杀相关接口")
public class SeckillApiController {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private SeckillItemService seckillItemService;

    @Autowired
    private SeckillCachedService seckillCachedService;

    @Autowired
    private DozerUtils dozerUtils;

    @PostMapping("/list")
    @ApiOperation(value = "获取秒杀活动列表", notes = "获取秒杀活动列表", response = SeckillApiResponse.class)
    public ResponseResult<SeckillApiResponse> seckillList() {
        SeckillListCache seckillListCache = seckillCachedService.getCachedSeckillList(null);
        if(seckillListCache.isLater()) {
            return ResponseResult.buildError("请稍后再试");
        }

        List<Seckill> seckillList = seckillListCache.getSeckillList();
        List<SeckillVO> seckillVOList = dozerUtils.mapList(seckillList, SeckillVO.class);
        return ResponseResult.buildSuccess(SeckillApiResponse.build(seckillVOList));
    }

    @PostMapping("/item/list/{seckillId}")
    @ApiOperation(value = "获取秒杀活动下的商品列表", notes = "获取秒杀活动下的商品列表", response = SeckillItemApiResponse.class)
    public ResponseResult<SeckillItemApiResponse> seckillItemList(@PathVariable Long seckillId) {
        SeckillItemListCache seckillItemListCache = seckillCachedService.getCachedSeckillItemList(seckillId, null);
        if(seckillItemListCache.isLater()) {
            return ResponseResult.buildError("请稍后再试");
        }

        List<SeckillItem> seckillItemList = seckillItemListCache.getSeckillItemList();
        List<SeckillItemVO> seckillItemVOList = dozerUtils.mapList(seckillItemList, SeckillItemVO.class);
        return ResponseResult.buildSuccess(SeckillItemApiResponse.build(seckillItemVOList));
    }
}

