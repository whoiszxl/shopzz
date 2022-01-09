package com.whoiszxl.entity.query;

import lombok.Data;

@Data
public class AddCartQuery {

    private Long skuId;

    private Integer quantity;
}
