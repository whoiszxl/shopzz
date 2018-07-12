package com.whoiszxl.order.utils

import com.whoiszxl.order.common.OrderStatus

/**
 * API訂單狀態轉APP訂單狀態 相互轉換工具
 */
object OrderStatusConverter {

    fun Api2App(status : Int):Int {
        return when(status){
            OrderStatus.ORDER_ALL ->  0
            OrderStatus.ORDER_WAIT_PAY ->  1
            OrderStatus.ORDER_WAIT_CONFIRM ->  2
            OrderStatus.ORDER_COMPLETED ->  3
            OrderStatus.ORDER_CANCELED ->  4
            else -> 0
        }
    }

    fun App2Api(status : Int):Int {
        return when(status){
            0 -> OrderStatus.ORDER_ALL
            1 -> OrderStatus.ORDER_WAIT_PAY
            2 -> OrderStatus.ORDER_WAIT_CONFIRM
            3 -> OrderStatus.ORDER_COMPLETED
            4 -> OrderStatus.ORDER_CANCELED
            else -> OrderStatus.ORDER_ALL
        }
    }

}
