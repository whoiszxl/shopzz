'use strict';
require('./index.css');
require('page/common/header/index.js');
require('page/common/nav/index.js');
var _xl = require('util/xl.js');
var _order = require('service/order-service.js');
var _address = require('service/address-service.js');
var templateAddress = require('./address-list.string');
var templateProduct = require('./product-list.string');
var addressModal = require('./address-modal.js');
var page = {
    data : {
        selectedAddressId : null
    },
    init : function () {
        this.onLoad();
        this.bindEvent();
    },
    onLoad : function () {
        this.loadAddressList();
        this.loadProductList();
    },
    bindEvent : function () {
        var _this = this;
        //地址的选择
        $(document).on('click','.address-item',function () {
            $(this).addClass('active').siblings('.address-item').removeClass('active');
            _this.data.selectedAddressId = $(this).data('id');
        });
        //订单的提交
        $(document).on('click','.order-submit',function () {
            var shippingId = _this.data.selectedAddressId;
            if(shippingId){
                _order.createOrder({
                    shippingId : shippingId
                },function (res) {
                    window.location.href = './payment.html?orderNumber=' + res.orderNo;
                },function (errMsg) {
                    _xl.errorTips(errMsg);
                })
            }else{
                _xl.errorTips('请选择地址后再提交');
            }
        });
        //地址的添加
        $(document).on('click','.address-add',function () {
            addressModal.show({
                isUpdate : false,
                onSuccess : function () {
                    _this.loadAddressList();
                }
            })
        });
        //地址的编辑
        $(document).on('click','.address-update',function (e) {
            e.stopPropagation();
            var shippingId = $(this).parents('.address-item').data('id');
            _address.getAddress(shippingId,function (res) {
                addressModal.show({
                    isUpdate : true,
                    data : res,
                    onSuccess : function () {
                        _this.loadAddressList();
                    }
                })
            },function (errMsg) {
                _xl.errorTips(errMsg);
            })

        });
        //地址的删除
        $(document).on('click','.address-delete',function (e) {
            e.stopPropagation();
            var id = $(this).parents('.address-item').data('id');
            if(window.confirm('确认要删除改地址吗?')){
                _address.deleteAddress(id,function (res) {
                    _this.loadAddressList();
                },function (errMsg) {
                    _xl.errorTips(errMsg);
                })
            }
        });
    },
    //加载地址列表
    loadAddressList : function () {
        var _this = this;
        // 获取地址列表
        $('.address-con').html('<div class="loading"></div>')

        _address.getAddressList(function (res) {
            _this.addressFilter(res)
            var addressListHtml = _xl.renderHtml(templateAddress,res);
            $('.address-con').html(addressListHtml);
        },function (errMsg) {
            $('.address-con').html('<p class="err-tip">地址加载失败,请刷新后重试</p>')
        })
    },
    //处理地址列表中选中状态
    addressFilter : function (data) {
        console.log(this.data.selectedAddressId)
        if(this.data.selectedAddressId){
            var selectedAddressIdFlag = false;
            for(var i = 0,length = data.list.length; i < length; i++){
                if (data.list[i].id === this.data.selectedAddressId){
                    data.list[i].isActive = true;
                    selectedAddressIdFlag = true;
                }
            }
            //如果以前选中的地址不在列表里,将其删除
            if(!selectedAddressIdFlag){
                this.data.selectedAddressId = null;
            }
        }
    },
    //加载商品清单
    loadProductList : function () {
        var _this = this;
        $('.product-con').html('<div class="loading"></div>')
        // 获取地址列表
        _order.getProductList(function (res) {
            var productListHtml = _xl.renderHtml(templateProduct,res);
            $('.product-con').html(productListHtml);
        },function (errMsg) {
            $('.product-con').html('<p class="err-tip">商品信息加载失败,请刷新后重试</p>')
        })
    },
    //删除指定商品,支持批量,productId用逗号分隔
    deleteCartProduct : function () {
        var _this = this;
        _cart.deleteProduct(productIds,function (res) {
            _this.renderCart(res);
        },function (errMsg) {
            _this.showCartError()
        })
    },

    //数据匹配
    filter : function (data) {
        data.notEmpty = !!data.cartProductVoList.length;
    },
    //显示错误信息
    showCartError : function () {
        $('.page-wrap').html('<p class="err-tip">哪里不对了,刷新下试试吧</p>')
    }
};
$(function () {
    page.init();
})
