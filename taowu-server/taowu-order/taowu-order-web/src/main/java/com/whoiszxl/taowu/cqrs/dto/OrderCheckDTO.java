package com.whoiszxl.taowu.cqrs.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单检查DTO
 *
 * @author whoiszxl
 * @date 2022/4/11
 */
@Data
public class OrderCheckDTO {

    private BigDecimal finalDiscountPrice;
    private BigDecimal finalPrice;
}
