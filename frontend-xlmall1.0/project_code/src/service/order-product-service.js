_xl = require("../util/xl.js");

var page = {
    // 得到当前产品清单
    getInventory: function(resolve, reject) {
        _xl.request({
            url: _xl.getServerUrl('/order/get_order_cart_product'),
            method: 'POST',
            success: resolve,
            error: reject
        });
    },
    // 创建一个订单
    createOrder: function(data, resolve, reject) {
        _xl.request({
            url: _xl.getServerUrl('/order/create'),
            data: data,
            method: 'POST',
            success: resolve,
            error: reject
        });
    },
    // 获取订单列表
    getProductList: function(data, resolve, reject) {
        _xl.request({
            url: _xl.getServerUrl('/order/list'),
            data: data,
            success: resolve,
            error: reject
        });
    },
    // 获取订单详情
    getOrderDetail: function(orderNo, resolve, reject) {
        _xl.request({
            url: _xl.getServerUrl('/order/detail'),
            data: {
                orderNo: orderNo
            },
            success: resolve,
            error: reject
        });
    },
    // 取消订单
    orderCancal: function(orderNo, resolve, reject) {
        _xl.request({
            url: _xl.getServerUrl('/order/cancel'),
            data: {
                orderNo: orderNo
            },
            method: 'POST',
            success: resolve,
            error: reject
        });
    },

}

module.exports = page;