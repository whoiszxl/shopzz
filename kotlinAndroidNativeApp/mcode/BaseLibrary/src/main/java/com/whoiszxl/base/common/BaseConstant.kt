package com.whoiszxl.base.common

class BaseConstant {

    companion object {

        /**
         * 七牛服务地址
         */
        const val IMAGE_SERVER_ADDRESS = "http://qiniu.whoiszxl.com/"

        /**
         * 本地服务器地址
         */
        const val SERVER_ADDRESS = "http://118.126.92.128:10101"

        /**
         * SP表名
         */
        const val TABLE_PREFS = "xlmall"

        /**
         * Token Key
         */
        const val KEY_SP_TOKEN = "token"

        /**
         * 收货地址显示数量
         */
        const val ADDRESS_SHOW_COUNT = 20

        /**
         * TODO 暂时没有分页的订单显示数量
         */
        const val ORDER_SHOW_COUNT = 100000
    }
}