package com.whoiszxl.cqrs.response;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDetailApiResponse {

    private List<CartItemVO> cartItemVOList;

    private Integer skuCount;

    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        BigDecimal result = BigDecimal.ZERO;
        if(ObjectUtils.isNotEmpty(cartItemVOList)) {
            for (CartItemVO cartItemVO : cartItemVOList) {
                BigDecimal singleItemAmount = cartItemVO.getPrice().multiply(BigDecimal.valueOf(cartItemVO.getQuantity()));
                result = result.add(singleItemAmount);
            }
        }

        return result;
    }

    public Integer getSkuCount() {
        if(ObjectUtils.isNotEmpty(cartItemVOList)) {
            return cartItemVOList.stream().mapToInt(CartItemVO::getQuantity).sum();
        }
        return 0;
    }
}
