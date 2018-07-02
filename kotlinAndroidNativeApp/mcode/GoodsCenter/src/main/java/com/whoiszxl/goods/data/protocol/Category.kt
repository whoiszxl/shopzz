package com.whoiszxl.goods.data.protocol

/**
 * 商品分类
 */
data class Category(
        val id: Int, //分类ID
        val parentId: Int, //分类 父级ID
        val name: String, //分类名称
        val img: String = "", //分类图标url
        val imgHost: String = "", //分类图标host
        var isSelected: Boolean = false//是否被选中
)