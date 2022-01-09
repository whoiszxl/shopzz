package com.whoiszxl.entity.query;

import lombok.Data;

@Data
public class SaveCartQuery {

    private Long skuId;

    private Integer quantity;
}
