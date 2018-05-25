/*
 * @Author: whoiszxl 
 * @Date: 2018-05-13 22:06:33 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-05-25 23:20:36
 */

'use strict';

var _xl = require('util/xl.js');

var _cart = {
    // 获取购物车数量
    getCartCount : function(resolve, reject){
        _xl.request({
            method  : 'post',
            url     : _xl.getServerUrl('/cart/get_cart_product_count'),
            success : resolve,
            error   : reject
        });
    },
    // 添加到购物车
    addToCart : function(productInfo, resolve, reject){
        _xl.request({
            method  : 'post',
            url     : _xl.getServerUrl('/cart/add'),
            data    : productInfo,
            success : resolve,
            error   : reject
        });
    },
    // 获取购物车列表
    getCartList : function(resolve, reject){
        _xl.request({
            url     : _xl.getServerUrl('/cart/list'),
            success : resolve,
            error   : reject
        });
    },
    // 选择购物车商品
    selectProduct : function(productId, resolve, reject){
        _xl.request({
            method  : 'post',
            url     : _xl.getServerUrl('/cart/select'),
            data    : {
                productId : productId
            },
            success : resolve,
            error   : reject
        });
    },
    // 取消选择购物车商品
    unselectProduct : function(productId, resolve, reject){
        _xl.request({
            method  : 'post',
            url     : _xl.getServerUrl('/cart/un_select'),
            data    : {
                productId : productId
            },
            success : resolve,
            error   : reject
        });
    },
    // 选中全部商品
    selectAllProduct : function(resolve, reject){
        _xl.request({
            method  : 'post',
            url     : _xl.getServerUrl('/cart/select_all'),
            success : resolve,
            error   : reject
        });
    },
    // 取消选中全部商品
    unselectAllProduct : function(resolve, reject){
        _xl.request({
            method  : 'post',
            url     : _xl.getServerUrl('/cart/un_select_all'),
            success : resolve,
            error   : reject
        });
    },
    // 更新购物车商品数量
    updateProduct : function(productInfo, resolve, reject){
        _xl.request({
            method  : 'post',
            url     : _xl.getServerUrl('/cart/update'),
            data    : productInfo,
            success : resolve,
            error   : reject
        });
    },
    // 删除指定商品
    deleteProduct : function(productIds, resolve, reject){
        _xl.request({
            method  : 'post',
            url     : _xl.getServerUrl('/cart/delete_product'),
            data    : {
                productIds : productIds
            },
            success : resolve,
            error   : reject
        });
    },
}
module.exports = _cart;