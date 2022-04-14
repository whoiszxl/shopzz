package com.whoiszxl.cqrs.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/4/11
 */
@Data
public class OrderCheckDTO {

    private BigDecimal finalDiscountPrice;
    private BigDecimal finalPrice;
}
