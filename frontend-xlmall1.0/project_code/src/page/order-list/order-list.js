require("./order-list.css");
var navSide = require("../common/nav-side/nav-side.js")
var _xl = require("util/xl.js");
var orderProductService = require("service/order-product-service.js");
var templateList = require("./order-list.string");
var paging = require("util/paging/paging.js");
var page = {
    data: {
        listParam: {
            pageSize: 5,
            pageNum: 1
        }

    },
    init: function() {
        this.onload();
        this.bindEvent();
    },
    onload: function() {
        navSide.init({
            name: "order-list"
        });
        this.loadOrderList();
    },
    bindEvent: function() {
        var _this = this;
    },
    loadOrderList: function() {
        var html = "";
        var _this = this;
        orderProductService.getProductList(_this.data.listParam, function(res) {
            html = _xl.renderHtml(templateList, res.data);
            $(".order-list-con").html(html);
            // 加载分页器
            var pagingData = {
                pageNum: res.data.pageNum, //当前页数
                pages: res.data.pages, //总共多少页
                firstPage: res.data.firstPage,
                prePage: res.data.prePage,
                nextPage: res.data.nextPage,
                lastPage: res.data.lastPage,
                navigatepageNums: res.data.navigatepageNums,
                onselect: function(value) {
                    _this.data.listParam.pageNum = value;
                    _this.loadOrderList();
                }
            }
            _this.loadPaging(pagingData);

        }, function(errMsg) {
            _xl.errorHint(errMsg);
        });
    },
    // 加载分页器
    loadPaging: function(data) {
        // console.log(data.nextPage);
        this.paging = new paging(data);
    }
}

$(function() {
    page.init();
})