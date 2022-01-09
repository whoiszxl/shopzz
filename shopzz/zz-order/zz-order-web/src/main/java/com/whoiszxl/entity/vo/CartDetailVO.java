package com.whoiszxl.entity.vo;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDetailVO {

    private List<CartItemVO> cartItemVOList;

    private Integer skuCount;

    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        if(ObjectUtils.isNotEmpty(cartItemVOList)) {
            return cartItemVOList.stream().map(CartItemVO::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return BigDecimal.ZERO;
    }

    public Integer getSkuCount() {
        if(ObjectUtils.isNotEmpty(cartItemVOList)) {
            return cartItemVOList.stream().mapToInt(CartItemVO::getQuantity).sum();
        }
        return 0;
    }
}
