package com.whoiszxl.order.common

/**
 * 订单常量
 */
class OrderConstant {
    companion object {
        //订单状态
        const val KEY_ORDER_STATUS = "order_status"
        //收货地址
        const val KEY_SHIP_ADDRESS = "ship_address"
        //选择收货地址请求码
        const val REQ_SELECT_ADDRESS = 1001
        //是否选择收货地址
        const val KEY_IS_SELECT_ADDRESS = "is_select_address"
        //是否编辑地址
        const val KEY_ADDRESS_IS_EDIT = "address_is_edit"
        //支付订单操作
        const val OPT_ORDER_PAY = 1
        //确认订单操作
        const val OPT_ORDER_CONFIRM = 2
        //取消订单操作
        const val OPT_ORDER_CANCEL = 3
    }


}
