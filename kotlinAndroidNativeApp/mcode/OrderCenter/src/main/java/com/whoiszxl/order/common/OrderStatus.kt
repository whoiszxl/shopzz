package com.whoiszxl.order.common

/**
 * 订单状态
 */
class OrderStatus {
    companion object {
        const val ORDER_ALL = 0//全部
        const val ORDER_WAIT_PAY = 1//待支付
        const val ORDER_WAIT_CONFIRM = 2//待收货
        const val ORDER_COMPLETED = 3//已完成
        const val ORDER_CANCELED = 4//已取消
    }
}
