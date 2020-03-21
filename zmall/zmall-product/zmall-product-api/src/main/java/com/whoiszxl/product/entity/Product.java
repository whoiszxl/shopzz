package com.whoiszxl.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 商品组合实体
 * @author: whoiszxl
 * @create: 2020-03-21
 **/
@Data
public class Product implements Serializable {

    private Spu spu;

    private List<Sku> skuList;
}
