
'use strict';

var _xl = require('util/xl.js');

var _article = {
    // 获取商品列表
    getBannerList : function(resolve, reject){
        _xl.request({
            url     : _xl.getServerUrl('/article/banners'),
            success : resolve,
            error   : reject
        });
    },

    //获取关键词列表
    getKeywordsList : function(resolve, reject) {
        _xl.request({
            url     : _xl.getServerUrl('/article/keywords'),
            success : resolve,
            error   : reject
        });
    }
}
module.exports = _article;