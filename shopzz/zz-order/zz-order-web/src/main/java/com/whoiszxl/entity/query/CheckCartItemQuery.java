package com.whoiszxl.entity.query;

import lombok.Data;

@Data
public class CheckCartItemQuery {

    private Integer isChecked;

    private Long skuId;
}
