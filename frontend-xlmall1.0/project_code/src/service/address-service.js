'use strict';

var _xl = require('util/xl.js');
var _address = {
    //获取地址列表
    getAddressList : function(resolve,reject){
        _xl.request({
            method : 'post',
            url:_xl.getServerUrl('/shipping/list'),
            data : {
                pageSize : 50
            },
            success : resolve,
            error : reject
        })
    },
    //新建收件人
    save : function(addressInfo,resolve,reject){
        _xl.request({
            method : 'post',
            url:_xl.getServerUrl('/shipping/add'),
            data : addressInfo,
            success : resolve,
            error : reject
        })
    },
    //更新收件人
    update : function(addressInfo,resolve,reject){
        _xl.request({
            method : 'post',
            url:_xl.getServerUrl('/shipping/update'),
            data : addressInfo,
            success : resolve,
            error : reject
        })
    },
    //删除收件人
    deleteAddress : function(shippingId,resolve,reject){
        _xl.request({
            method : 'post',
            url:_xl.getServerUrl('/shipping/delete'),
            data : {
                shippingId : shippingId
            },
            success : resolve,
            error : reject
        })
    },
    //获取单条地址信息
    getAddress : function(shippingId,resolve,reject){
        _xl.request({
            method : 'get',
            url:_xl.getServerUrl('/shipping/selects'),
            data : {
                shippingId : shippingId
            },
            success : resolve,
            error : reject
        })
    },
}

module.exports = _address;