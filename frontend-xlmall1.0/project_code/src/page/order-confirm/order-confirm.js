require("./order-confirm.css");
var _xl = require("util/xl.js");
var orderAddressService = require("service/order-address-service.js");
var orderProductService = require("service/order-product-service.js");
var addressModal = require("./address-modal.js");
var templateAddress = require("./address.string");
var templateProductList = require("./product-list.string");

var page = {
    data: {
        selectAddressId: null
    },
    init: function() {
        this.onLoad();
        this.bindEvent();
    },
    onLoad: function() {
        this.loadProductList();
        this.loadAddressList();
    },
    bindEvent: function() {
        var _this = this;
        // 添加地址选中事件
        $(document).on("click", ".address-item", function() {
            $(this).addClass("active").siblings(".address-item").removeClass("active");
            // 把id传出去
            _this.data.selectAddressId = $(this).data("id");
        });
        // 创建订单
        $(document).on("click", ".submit", function() {
            var shippingId = _this.data.selectAddressId;
            if (shippingId) {
                // 地址选择好了进行请求
                orderProductService.createOrder({
                    shippingId: shippingId
                }, function(res) {
                    // 调转到支付页面
                    window.location.href = "./pay.html?orderNo=" + res.data.orderNo;
                }, function(errMsg) {
                    _xl.errorHint("errMsg")
                })
            } else {
                _xl.errorHint("请选择一个地址")
            }
        });
        // 添加新地址
        $(document).on("click", ".address-add", function() {
            addressModal.show({
                isUpdate: false,
                onSuccess: function() {
                    _this.onLoad();
                }
            })
        });
        // 更新地址
        $(document).on("click", ".ad-update", function(e) {
            // 先进行请求获得id
            var shippingId = $(this).parents(".address-item").data("id");
            orderAddressService.getAddress({
                    shippingId: shippingId
                }, function(res) {
                    addressModal.show({
                        isUpdate: true,
                        data: res.data,
                        onSuccess: function() {
                            _this.onLoad();
                        }
                    });
                }, function(errMsg) {
                    _xl.errorHint(errMsg);

                })
                // 阻止事件冒泡
            e.stopPropagation();
        });
        // 删除地址
        $(document).on("click", ".ad-delete", function(e) {
            // 获取选择框id，进行删除操作
            var shippingId = $(this).parents(".address-item").data("id");
            orderAddressService.deleteAddress({
                shippingId: shippingId
            }, function(res) {
                _this.loadAddressList();
            }, function(errMsg) {
                _xl.errorHint(errMsg);
            });
            // 阻止事件冒泡
            e.stopPropagation();
        });
    },
    // 加载地址列表
    loadAddressList: function() {
        var _this = this;
        orderAddressService.getAddressList(function(res) {
            var addressHtml = _xl.renderHtml(templateAddress, res.data);
            $(".address").html(addressHtml);

            if (_this.data.selectAddressId) {
                $(".address-item").each(function() {
                    var $this = $(this);
                    if ($this.data("id") === _this.data.selectAddressId) {
                        $this.addClass("active");
                    }
                })
            };
        }, function(errMsg) {
            $(".address").html("<p>出错了，请刷新</p>");
        })
    },
    // 加载订单商品列表
    loadProductList: function() {
        orderProductService.getInventory(function(res) {
            var ProductListHtml = _xl.renderHtml(templateProductList, res.data);
            $(".inventory-con").html(ProductListHtml);

        }, function(errMsg) {
            $(".address").html("<p>出错了，请刷新</p>");
        })
    },

};

$(function() {
    page.init();
});