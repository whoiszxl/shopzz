package com.whoiszxl.order.common

/**
 * 订单状态
 */
class OrderStatus {
    companion object {
        const val ORDER_ALL = -1//全部
        const val ORDER_WAIT_PAY = 10//待支付
        const val ORDER_WAIT_CONFIRM = 20//待收货
        const val ORDER_CONFIRM_TRUE = 40//已发货
        const val ORDER_COMPLETED = 50//已完成
        const val ORDER_CANCELED = 0//已取消
    }
}
