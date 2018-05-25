'use strict';
require('./index.css')
require('page/common/nav/index.js');
require('page/common/header/index.js');
var navSide = require('page/common/nav-side/index.js');
var _xl = require('util/xl.js');
var _order = require('service/order-service.js');
var templateIndex = require('./index.string');


// page逻辑部分
var page = {
    data : {
        orderNumber : _xl.getUrlParam('orderNumber')
    },
    init : function () {
        this.onLoad();
        this.bindEvent();
    },
    onLoad : function () {
        // 初始化左侧菜单
        navSide.init({
            name : 'order-list'
        })
        //加载detail数据
        this.loadDetail();
    },
    bindEvent : function () {
        var _this = this;
        $(document).on('click','.order-cancel',function () {
            if(window.confirm('确实要取消该订单吗?')){
                _order.cancelOrder(_this.data.orderNumber,function (res) {
                    _xl.successTips('该订单取消成功');
                    _this.loadDetail();
                },function (errMsg) {
                    _xl.errorTips(errMsg);
                })
            }
        })
    },
    //加载订单列表
    loadDetail : function () {
        var orderDetailHtml = '',
            _this = this,
            $content = $('.content');
        $content.html('<div class="loading"></div>')
        _order.getOrderDetail(this.data.orderNumber,function (res) {
            _this.dataFilter(res);
            //渲染html
            orderDetailHtml = _xl.renderHtml(templateIndex,res);
            $content.html(orderDetailHtml);
        },function (errMsg) {
            $content.html("<p class='err-tip'>" + errMsg + "</p>");
        })
    },
    //数据的适配
    dataFilter : function (data) {
        data.needPay = data.status == 10 ;
        data.isCancelable = data.status == 10 ;
    }
}
$(function () {
    page.init()
})
