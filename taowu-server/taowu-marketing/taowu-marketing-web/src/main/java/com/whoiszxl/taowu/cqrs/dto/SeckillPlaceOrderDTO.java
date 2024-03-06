package com.whoiszxl.taowu.cqrs.dto;


import lombok.Data;

/**
 * 下单MQ流转DTO
 *
 * @author whoiszxl
 * @date 2022/5/19
 */
@Data
public class SeckillPlaceOrderDTO {

    private Long memberId;

    private Long seckillItemId;

    private Long seckillId;

    private Integer quantity;

    private String taskKey;
}
