'use strict';
require('./index.css')
require('page/common/nav/index.js');
require('page/common/header/index.js');
var navSide = require('page/common/nav-side/index.js');
var _xl = require('util/xl.js');
var _payment = require('service/payment-service.js');
var templateIndex = require('./index.string');


// page逻辑部分
var page = {
    data : {
        orderNumber : _xl.getUrlParam('orderNumber')
    },
    init : function () {
        this.onLoad();
    },
    onLoad : function () {
        //加载detail数据
        this.loadPaymentInfo();
    },

    loadPaymentInfo : function () {
        var paymentHtml = '',
            _this = this,
            $pageWrap = $('.page-wrap');
        $pageWrap.html('<div class="loading"></div>')
        _payment.getPaymentInfo(this.data.orderNumber,function (res) {
            //渲染html
            paymentHtml = _xl.renderHtml(templateIndex,res);
            $pageWrap.html(paymentHtml);
            _this.listenOrderStatus();
        },function (errMsg) {
            $content.html("<p class='err-tip'>" + errMsg + "</p>");
        })
    },
    //监听订单状态
    listenOrderStatus : function () {
        var _this = this;
        this.paymentTimer = window.setInterval(function () {
            _payment.getPaymentStatus(_this.data.orderNumber,function (res) {
                if (res == true){
                    window.location.href = './result.html?type=payment&orderNumber='+_this.data.orderNumber;
                }
            })
        },5e3)
    }

}
$(function () {
    page.init()
})
