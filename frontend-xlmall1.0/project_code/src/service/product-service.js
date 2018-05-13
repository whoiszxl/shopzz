/*
 * @Author: whoiszxl 
 * @Date: 2018-05-13 22:06:02 
 * @Last Modified by:   whoiszxl 
 * @Last Modified time: 2018-05-13 22:06:02 
 */

'use strict';

var _xl = require('util/xl.js');

var _product = {
    // 获取商品列表
    getProductList : function(listParam, resolve, reject){
        _xl.request({
            url     : _xl.getServerUrl('/product/list.do'),
            data    : listParam,
            success : resolve,
            error   : reject
        });
    },
    // 获取商品详细信息
    getProductDetail : function(productId, resolve, reject){
        _xl.request({
            url     : _xl.getServerUrl('/product/detail.do'),
            data    : {
                productId : productId
            },
            success : resolve,
            error   : reject
        });
    }
}
module.exports = _product;