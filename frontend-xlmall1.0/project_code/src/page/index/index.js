/*
 * @Author: whoiszxl 
 * @Date: 2018-05-11 10:32:21 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-14 14:35:48
 */

'use strict';
require('./index.css');
require('page/common/nav/index.js');
require('page/common/header/index.js');
require('util/slider/index.js');

var templateBanner  = require('./banner.string');
var templateKeywords = require('./keywords.string');
var templateCategory = require('./category.string');
var navSide = require('page/common/nav-side/index.js');
var _xl = require('util/xl.js');
var _article = require('service/article-service.js');
var _product = require('service/product-service.js');

$(function() {
    
    // 前一张和后一张操作的事件绑定
    $('.banner-con .banner-arrow').click(function(){
        var forward = $(this).hasClass('prev') ? 'prev' : 'next';
        $slider.data('unslider')[forward]();
    });

    
    _article.getBannerList(function(res){

        // 渲染banner的html
        var bannerHtml  = _xl.renderHtml(templateBanner, {
            list :  res
        });
        $('.banner-con').html(bannerHtml);

        // 初始化banner
        var $slider     = $('.banner').unslider({
            dots: true
        });
    }, function(errMsg){
    });


    _article.getKeywordsList(function(res){

        console.log(res);
        
        var keywordsHtml = _xl.renderHtml(templateKeywords, {
            keywordsList : res
        });

        $('.keywords-list').html(keywordsHtml);

    });

    _product.getIndexCategoryList(function(res){

        console.log(res);
        var categoryHtml = _xl.renderHtml(templateCategory, {
            categoryMap : res
        });

        $('.index-category').html(categoryHtml);

    });
    
    
    
});
