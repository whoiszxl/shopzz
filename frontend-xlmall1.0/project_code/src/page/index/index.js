/*
 * @Author: whoiszxl 
 * @Date: 2018-05-11 10:32:21 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-07 11:54:49
 */

'use strict';
require('./index.css');
require('page/common/nav/index.js');
require('page/common/header/index.js');
require('util/slider/index.js');

var templateBanner  = require('./banner.string');
var navSide = require('page/common/nav-side/index.js');
var _xl = require('util/xl.js');
var _article = require('service/article-service.js');

$(function() {
    
    // 前一张和后一张操作的事件绑定
    $('.banner-con .banner-arrow').click(function(){
        var forward = $(this).hasClass('prev') ? 'prev' : 'next';
        $slider.data('unslider')[forward]();
    });

    
    _article.getBannerList(function(res){

        // 渲染banner的html
        console.log(res);
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

    
    
    
});
