require("./pay.css");
var _xl = require("util/xl.js");
var payService = require("service/pay-service.js");
var templatePayment = require("./pay.string");
var page = {
    data: {
        orderNo: _xl.getUrlParam("orderNo"),
    },
    init: function() {
        this.onload();
    },
    onload: function() {
        this.loadPayment();
    },
    // 加载支付页面
    loadPayment: function() {
        var html = "";
        var _this = this;
        payService.pay(_this.data.orderNo, function(res) {
            var html = '';
            html = _xl.renderHtml(templatePayment, res.data);
            $(".page-wrap").html(html);
            _this.listernOrderStatus();
        }, function(errMsg) {
            _xl.errorHint(errMsg);
        });
    },
    // 监听订单状态
    listernOrderStatus: function() {
        var _this = this;
        // 定期检查
        window.setInterval(function() {
            payService.getOrderStatus(_this.data.orderNo, function(res) {
                if (res.data === true) {
                    window.location.href = './result.html?type=payment&orderNo=' + _this.data.orderNo;
                }
            }, function(errMsg) {
                _xl.errorHint(errMsg);
            })
        }, 5000);
    }
}

$(function() {
    page.init();
})