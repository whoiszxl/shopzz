'use strict';

var _xl = require('util/xl.js');
var _payment = {
    //获取支付信息
    getPaymentInfo : function(orderNumber,resolve,reject){
        _xl.request({
            method : 'post',
            url:_xl.getServerUrl('/order/pay'),
            data : {
                orderNo : orderNumber
            },
            success : resolve,
            error : reject
        })
    },
    //获取订单状态
    getPaymentStatus : function(orderNumber,resolve,reject){
        _xl.request({
            method : 'post',
            url:_xl.getServerUrl('/order/query_order_pay_status'),
            data : {
                orderNo : orderNumber
            },
            success : resolve,
            error : reject
        })
    },
}

module.exports = _payment;