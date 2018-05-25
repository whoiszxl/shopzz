_xl = require("../util/xl.js");

var pay_service = {
    // 获取购物车数量
    pay: function(orderNo, resolve, reject) {
        _xl.request({
            url: _xl.getServerUrl('/order/pay'),
            data: {
                orderNo: orderNo
            },
            method: 'POST',
            success: resolve,
            error: reject
        });
    },
    // 获取订单状态
    getOrderStatus: function(orderNo, resolve, reject) {
        _xl.request({
            url: _xl.getServerUrl('/order/query_order_pay_status'),
            data: {
                orderNo: orderNo
            },
            method: 'POST',
            success: resolve,
            error: reject
        });
    },

}
module.exports = pay_service;