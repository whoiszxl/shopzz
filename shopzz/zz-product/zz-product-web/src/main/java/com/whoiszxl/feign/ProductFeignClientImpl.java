package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.OrderInfoDTO;
import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.dto.SkuDTO;
import com.whoiszxl.dto.SkuStockDTO;
import com.whoiszxl.entity.ProductStock;
import com.whoiszxl.entity.Sku;
import com.whoiszxl.service.ProductStockService;
import com.whoiszxl.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class ProductFeignClientImpl implements ProductFeignClient{

    @Autowired
    private SkuService skuService;

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public ResponseResult<SkuDTO> getSkuInfoBySkuId(Long skuId) {
        Sku sku = skuService.getById(skuId);
        SkuDTO skuDTO = dozerUtils.map(sku, SkuDTO.class);
        return ResponseResult.buildSuccess(skuDTO);
    }

    @Override
    public ResponseResult<List<SkuStockDTO>> getSaleStockQuantity(List<Long> skuIds) {
        List<SkuStockDTO> results = new ArrayList<>();
        for (Long skuId : skuIds) {
            ProductStock productStock = productStockService.getProductStockBySkuId(skuId);
            if(productStock == null) {
                results.add(new SkuStockDTO(skuId, 0));
            }else {
                results.add(new SkuStockDTO(skuId, productStock.getSaleStockQuantity()));
            }
        }
        return ResponseResult.buildSuccess(results);
    }

    @Override
    public ResponseResult<Boolean> checkStock(List<Long> skuIds) {
        for (Long skuId : skuIds) {
            ProductStock productStock = productStockService.getProductStockBySkuId(skuId);
            if(productStock == null || productStock.getSaleStockQuantity() == 0) {
                return ResponseResult.buildSuccess(Boolean.FALSE);
            }
        }
        return ResponseResult.buildSuccess(Boolean.TRUE);
    }

    @Override
    public ResponseResult paySuccessUpdateStock(OrderInfoDTO orderInfo) {
        List<OrderItemDTO> orderItemDTOList = orderInfo.getOrderItemDTOList();
        if(ObjectUtils.isNotEmpty(orderItemDTOList)) {
            for (OrderItemDTO orderItemDTO : orderItemDTOList) {
                Long skuId = orderItemDTO.getSkuId();
                Integer quantity = orderItemDTO.getQuantity();

                boolean updateFlag = productStockService.subLockStockAndAddSaledStockBySkuId(quantity, skuId);
                if(updateFlag) {
                    return ResponseResult.buildError();
                }
            }
        }

        return ResponseResult.buildSuccess();
    }
}
