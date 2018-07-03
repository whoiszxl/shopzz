package com.whoiszxl.goods.data.protocol

/**
 * 商品数据类
 */
data class Goods(
        val id: Int,//商品ID
        val categoryId: Int,//分类ID
        val name: String,//商品名称
        val subtitle: String,//商品简要描述
        val mainImage: String,//主图
        val subImages: String,//副图
        val detail: String,//商详
        val price: Long,//默认价格
        val goodsSalesCount: Int = 0,//商品销量
        val stock: Int,//商品剩余量
        val imageHost: String,//商品图域名地址
        val goodsDefaultSku: String = "一件",//默认SKU
        val goodsBanner: String,//商品banner图
        val goodsSku:List<GoodsSku>,//商品SKU
        val maxPage:Int//最大页码
)