package com.whoiszxl.cqrs.command;

import lombok.Data;

@Data
public class CartAddCommand {

    private Long skuId;

    private String skuCode;

    private Integer quantity;
}
