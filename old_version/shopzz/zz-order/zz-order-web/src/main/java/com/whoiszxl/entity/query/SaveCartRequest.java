package com.whoiszxl.entity.query;

import lombok.Data;

@Data
public class SaveCartRequest {

    private Long skuId;

    private Integer quantity;
}
