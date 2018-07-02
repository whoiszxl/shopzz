package com.whoiszxl.goods.data.protocol

data class GoodsList (

        val pageNum: Int,//第几页
        val pageSize: Int,//分页后每页的数量
        val size: Int,//实际的当前页数量
        val total: Int,//总数
        val pages: Int,//有多少页
        val list: List<Goods>//商品列表

)