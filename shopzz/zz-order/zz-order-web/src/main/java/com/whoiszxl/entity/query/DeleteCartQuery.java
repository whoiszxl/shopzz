package com.whoiszxl.entity.query;

import lombok.Data;

import java.util.List;

@Data
public class DeleteCartQuery {

    private List<Long> skuIdList;
}
