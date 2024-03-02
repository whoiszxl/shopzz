package com.whoiszxl.taowu.feign;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.utils.ParamUtils;
import com.whoiszxl.taowu.dto.*;
import com.whoiszxl.taowu.entity.Sku;
import com.whoiszxl.taowu.entity.SkuStock;
import com.whoiszxl.taowu.entity.Spu;
import com.whoiszxl.taowu.service.SkuService;
import com.whoiszxl.taowu.service.SkuStockService;
import com.whoiszxl.taowu.service.SpuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品feign client 实现
 *
 * @author whoiszxl
 * @date 2022/3/31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductFeignClientImpl implements ProductFeignClient{

    private final SkuService skuService;

    private final SpuService spuService;

    private final SkuStockService skuStockService;

    @Override
    public ResponseResult<SpuFeignDTO> getSpuBySpuId(Long spuId) {
        Spu spu = spuService.getById(spuId);
        SpuFeignDTO spuFeignDTO = BeanUtil.copyProperties(spu, SpuFeignDTO.class);
        return ResponseResult.buildSuccess(spuFeignDTO);
    }

    @Override
    public ResponseResult<List<SpuFeignDTO>> getSpuListBySpuIdList(String spuIds) {
        List<Long> spuList = ParamUtils.str2Array(spuIds);
        List<Spu> list = spuService.listByIds(spuList);
        List<SpuFeignDTO> spuFeignDTOList = BeanUtil.copyToList(list, SpuFeignDTO.class);
        return ResponseResult.buildSuccess(spuFeignDTOList);
    }

    @Override
    public ResponseResult<List<SkuFeignDTO>> getSkuListBySkuIdList(String skuIds) {
        List<Long> skuIdList = ParamUtils.str2Array(skuIds);
        List<Sku> skuList = skuService.listByIds(skuIdList);
        List<SkuFeignDTO> skuFeignDTOList = BeanUtil.copyToList(skuList, SkuFeignDTO.class);
        return ResponseResult.buildSuccess(skuFeignDTOList);
    }


    @Override
    public ResponseResult<SkuFeignDTO> getSkuInfoBySkuId(Long skuId) {
        Sku sku = skuService.getById(skuId);
        SkuFeignDTO skuDTO = BeanUtil.copyProperties(sku, SkuFeignDTO.class);
        return ResponseResult.buildSuccess(skuDTO);
    }

    @Override
    public ResponseResult<SkuFeignDTO> getSkuInfoBySkuCode(String skuCode) {
        Sku sku = skuService.getOne(Wrappers.<Sku>lambdaQuery().eq(Sku::getSkuCode, skuCode));
        SkuFeignDTO skuDTO = BeanUtil.copyProperties(sku, SkuFeignDTO.class);
        return ResponseResult.buildSuccess(skuDTO);
    }

    @Override
    public ResponseResult<List<SkuStockFeignDTO>> getStockBySkuIdList(String skuIds) {
        List<SkuStock> stockList = skuStockService.list(Wrappers.<SkuStock>lambdaQuery().in(SkuStock::getSkuId, skuIds));
        List<SkuStockFeignDTO> skuStockFeignDTOList = BeanUtil.copyToList(stockList, SkuStockFeignDTO.class);
        return ResponseResult.buildSuccess(skuStockFeignDTOList);
    }

    @Override
    @Transactional
    public ResponseResult<Boolean> subSaleStockAndAddLockStockBySkuId(List<ReduceStockFeignDTO> reduceStockFeignList) {
        for (ReduceStockFeignDTO reduceStockFeignDTO : reduceStockFeignList) {
            int result = skuStockService.subSaleStockAndAddLockStockBySkuId(reduceStockFeignDTO.getSkuId(), reduceStockFeignDTO.getQuantity());
            if(result < 1) {
                return ResponseResult.buildError();
            }
        }
        return ResponseResult.buildSuccess();
    }

    @Override
    public ResponseResult paySuccessUpdateStock(OrderInfoDTO orderInfo) {
        List<OrderItemDTO> orderItemDTOList = orderInfo.getOrderItemDTOList();
        if(ObjectUtils.isNotEmpty(orderItemDTOList)) {
            for (OrderItemDTO orderItemDTO : orderItemDTOList) {
                Long skuId = orderItemDTO.getSkuId();
                Integer quantity = orderItemDTO.getQuantity();

                boolean updateFlag = skuStockService.subLockStockAndAddSaledStockBySkuId(quantity, skuId);
                if(updateFlag) {
                    return ResponseResult.buildError();
                }
            }
        }

        return ResponseResult.buildSuccess();
    }
}
