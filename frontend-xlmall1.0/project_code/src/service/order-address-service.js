/*
 * @Author: whoiszxl 
 * @Date: 2018-05-25 23:23:53 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-05-25 23:25:24
 */

'use strict';
_xl = require("../util/xl.js");

var page = {
    //得到地址列表
    getAddressList: function(resolve, reject) {
        _xl.request({
            url: _xl.getServerUrl('/shipping/list'),
            data: {
                pageSize: 50
            },
            method: 'POST',
            success: resolve,
            error: reject
        });
    },
    // 获得选中地址
    getAddress: function(addressInfo, resolve, reject) {
        _xl.request({
            url: _xl.getServerUrl('/shipping/selects'),
            data: addressInfo,
            method: 'GET',
            success: resolve,
            error: reject
        });
    },
    // 添加新地址
    addAddress: function(addressInfo, resolve, reject) {
        _xl.request({
            url: _xl.getServerUrl('/shipping/add'),
            data: addressInfo,
            method: 'POST',
            success: resolve,
            error: reject
        });
    },
    // 更新收货地址
    updateAddress: function(addressInfo, resolve, reject) {
        _xl.request({
            url: _xl.getServerUrl('/shipping/update'),
            data: addressInfo,
            method: 'POST',
            success: resolve,
            error: reject
        });
    },
    // 删除地址
    deleteAddress: function(addressInfo, resolve, reject) {
        _xl.request({
            url: _xl.getServerUrl('/shipping/delete'),
            data: addressInfo,
            method: 'POST',
            success: resolve,
            error: reject
        });
    },
}

module.exports = page;