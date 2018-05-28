webpackJsonp([7],{

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(150);


/***/ }),

/***/ 2:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-12 10:31:15 
	 * @Last Modified by:   whoiszxl 
	 * @Last Modified time: 2018-05-12 10:31:15 
	 */

	'use strict';

	__webpack_require__(3);
	var _xl     = __webpack_require__(8);
	// 通用页面头部
	var header = {
	    init : function(){
	        this.onLoad();
	        this.bindEvent();
	    },
	    onLoad : function(){
	        var keyword = _xl.getUrlParam('keyword');
	        // keyword存在，则回填输入框
	        if(keyword){
	            $('#search-input').val(keyword);
	        };
	    },
	    bindEvent : function(){
	        var _this = this;
	        // 点击搜索按钮以后，做搜索提交
	        $('#search-btn').click(function(){
	            _this.searchSubmit();
	        });
	        // 输入会车后，做搜索提交
	        $('#search-input').keyup(function(e){
	            // 13是回车键的keyCode
	            if(e.keyCode === 13){
	                _this.searchSubmit();
	            }
	        });
	    },
	    // 搜索的提交
	    searchSubmit : function(){
	        var keyword = $.trim($('#search-input').val());
	        // 如果提交的时候有keyword,正常跳转到list页
	        if(keyword){
	            window.location.href = './list.html?keyword=' + keyword;
	        }
	        // 如果keyword为空，直接返回首页
	        else{
	            _xl.goHome();
	        }
	    }
	};

	header.init();

/***/ }),

/***/ 3:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 12:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-11 23:15:43 
	 * @Last Modified by: whoiszxl
	 * @Last Modified time: 2018-05-26 01:03:24
	 */

	'use strict';
	__webpack_require__(13);
	var _xl     = __webpack_require__(8);
	// 导航
	var user_service = __webpack_require__(15);
	var cart_service = __webpack_require__(16);
	var nav = {
	    //初始化
	    init: function() {
	        this.bind();
	        this.loadUserInfo();
	        this.loadCartInfo();
	        return this;
	    },

	    // 绑定事件
	    bind: function() {
	        // 处理登录
	        $(".js-login").click(function() {
	            _xl.doLogin();
	        });
	        //处理注册
	        $(".js-register").click(function() {
	            window.location.href = './user-register.html';
	        });
	        // 处理退出
	        $(".js-loginOut").click(function() {
	            user_service.loginOut(function() {
	                window.location.reload();
	            }, function() {
	                _xl.errorHint();
	            });
	        })
	    },

	    //加载用户信息
	    loadUserInfo: function() {
	        user_service.checkLogin(function(res) {
	            console.log("用户名："+res.username);
	            $(".not-login").hide();
	            $('.login').show().find('.username').text(res.username);
	        }, function(errMsg) {
	            // do nothing
	        })
	    },

	    //加载购物车信息
	    loadCartInfo: function() {
	        cart_service.getCartCount(function(res) {
	            $(".cart-count").text("(" + res + ")" || "(0)");
	        }, function(errMsg) {
	            $(".cart-count").text("(0)");
	        })
	    }

	}

	module.exports = nav.init();

/***/ }),

/***/ 13:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 15:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-12 15:32:04 
	 * @Last Modified by: whoiszxl
	 * @Last Modified time: 2018-05-25 17:22:37
	 */

	'use strict';
	var _xl = __webpack_require__(8);

	var _user = {
	    // 用户登录
	    login : function(userInfo, resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/user/jwt_login'),
	            data    : userInfo,
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 检查用户名
	    checkUsername : function(username, resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/user/check_valid'),
	            data    : {
	                type    : 'username',
	                str     : username
	            },
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 用户注册
	    register : function(userInfo, resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/user/register'),
	            data    : userInfo,
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 检查登录状态
	    checkLogin : function(resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/user/get_user_info'),
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 获取用户密码提示问题
	    getQuestion : function(username, resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/user/forget_get_question'),
	            data    : {
	                username : username
	            },
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 检查密码提示问题答案
	    checkAnswer : function(userInfo, resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/user/forget_check_answer'),
	            data    : userInfo,
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 重置密码
	    resetPassword : function(userInfo, resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/user/forget_reset_password'),
	            data    : userInfo,
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 获取用户信息
	    getUserInfo : function(resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/user/get_information'),
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 更新个人信息
	    updateUserInfo : function(userInfo, resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/user/update_information'),
	            data    : userInfo,
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 登录状态下更新密码
	    updatePassword : function(userInfo, resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/user/reset_password'),
	            data    : userInfo,
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 登出
	    logout : function(resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/user/logout'),
	            method  : 'POST',
	            success : resolve,
	            error   : reject
	        });
	    }
	}
	module.exports = _user;

/***/ }),

/***/ 16:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-13 22:06:33 
	 * @Last Modified by: whoiszxl
	 * @Last Modified time: 2018-05-25 23:20:36
	 */

	'use strict';

	var _xl = __webpack_require__(8);

	var _cart = {
	    // 获取购物车数量
	    getCartCount : function(resolve, reject){
	        _xl.request({
	            method  : 'post',
	            url     : _xl.getServerUrl('/cart/get_cart_product_count'),
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 添加到购物车
	    addToCart : function(productInfo, resolve, reject){
	        _xl.request({
	            method  : 'post',
	            url     : _xl.getServerUrl('/cart/add'),
	            data    : productInfo,
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 获取购物车列表
	    getCartList : function(resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/cart/list'),
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 选择购物车商品
	    selectProduct : function(productId, resolve, reject){
	        _xl.request({
	            method  : 'post',
	            url     : _xl.getServerUrl('/cart/select'),
	            data    : {
	                productId : productId
	            },
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 取消选择购物车商品
	    unselectProduct : function(productId, resolve, reject){
	        _xl.request({
	            method  : 'post',
	            url     : _xl.getServerUrl('/cart/un_select'),
	            data    : {
	                productId : productId
	            },
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 选中全部商品
	    selectAllProduct : function(resolve, reject){
	        _xl.request({
	            method  : 'post',
	            url     : _xl.getServerUrl('/cart/select_all'),
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 取消选中全部商品
	    unselectAllProduct : function(resolve, reject){
	        _xl.request({
	            method  : 'post',
	            url     : _xl.getServerUrl('/cart/un_select_all'),
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 更新购物车商品数量
	    updateProduct : function(productInfo, resolve, reject){
	        _xl.request({
	            method  : 'post',
	            url     : _xl.getServerUrl('/cart/update'),
	            data    : productInfo,
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 删除指定商品
	    deleteProduct : function(productIds, resolve, reject){
	        _xl.request({
	            method  : 'post',
	            url     : _xl.getServerUrl('/cart/delete_product'),
	            data    : {
	                productIds : productIds
	            },
	            success : resolve,
	            error   : reject
	        });
	    },
	}
	module.exports = _cart;

/***/ }),

/***/ 128:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-12 10:39:56 
	 * @Last Modified by: whoiszxl
	 * @Last Modified time: 2018-05-12 10:48:51
	 */

	'use strict';

	__webpack_require__(129);
	var _xl             = __webpack_require__(8);
	var templateIndex   = __webpack_require__(131);
	// 侧边导航
	var navSide = {
	    option : {
	        name : '',
	        navList : [
	            {name : 'user-center', desc : '个人中心', href: './user-center.html'},
	            {name : 'order-list', desc : '我的订单', href: './order-list.html'},
	            {name : 'user-pass-update', desc : '修改密码', href: './user-pass-update.html'},
	            {name : 'about', desc : '关于xlall', href: './about.html'}
	        ]
	    },
	    init : function(option){
	        // 合并选项
	        $.extend(this.option, option);
	        this.renderNav();
	    },
	    // 渲染导航菜单
	    renderNav : function(){
	        // 计算active数据
	        for(var i = 0, iLength = this.option.navList.length; i < iLength; i++){
	            if(this.option.navList[i].name === this.option.name){
	                this.option.navList[i].isActive = true;
	            }
	        };
	        // 渲染list数据
	        var navHtml = _xl.renderHtml(templateIndex, {
	            navList : this.option.navList
	        });
	        // 把html放入容器
	        $('.nav-side').html(navHtml);
	    }
	};

	module.exports = navSide;

/***/ }),

/***/ 129:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 131:
/***/ (function(module, exports) {

	module.exports = "{{#navList}} {{#isActive}} <li class=\"nav-item active\"> {{/isActive}} {{^isActive}} </li><li class=nav-item> {{/isActive}} <a class=link href={{href}}>{{desc}}</a> </li> {{/navList}} ";

/***/ }),

/***/ 143:
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	var _xl = __webpack_require__(8);
	var _order = {
	    //获取商品列表
	    getProductList : function(resolve,reject){
	        _xl.request({
	            method : 'post',
	            url:_xl.getServerUrl('/order/get_order_cart_product'),
	            success : resolve,
	            error : reject
	        })
	    },
	    //提交订单
	    createOrder : function (orderInfo, resolve, reject) {
	        _xl.request({
	            method : 'post',
	            url:_xl.getServerUrl('/order/create'),
	            data : orderInfo,
	            success : resolve,
	            error : reject
	        })
	    },
	    //获取订单列表
	    getOrderList : function (listParam, resolve, reject) {
	        _xl.request({
	            method : 'get',
	            url:_xl.getServerUrl('/order/list'),
	            data : listParam,
	            success : resolve,
	            error : reject
	        })
	    },
	    //获取订单详情
	    getOrderDetail : function (orderNumber, resolve, reject) {
	        _xl.request({
	            method : 'get',
	            url:_xl.getServerUrl('/order/detail'),
	            data : {
	                orderNo : orderNumber
	            },
	            success : resolve,
	            error : reject
	        })
	    },
	    //取消订单
	    cancelOrder : function (orderNumber, resolve, reject) {
	        _xl.request({
	            method : 'post',
	            url:_xl.getServerUrl('/order/cancel'),
	            data : {
	                orderNo : orderNumber
	            },
	            success : resolve,
	            error : reject
	        })
	    },

	}

	module.exports = _order;

/***/ }),

/***/ 150:
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	__webpack_require__(151)
	__webpack_require__(12);
	__webpack_require__(2);
	var navSide = __webpack_require__(128);
	var _xl = __webpack_require__(8);
	var _order = __webpack_require__(143);
	var templateIndex = __webpack_require__(153);


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


/***/ }),

/***/ 151:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 153:
/***/ (function(module, exports) {

	module.exports = "<div class=panel> <div class=panel-title>订单信息</div> <div class=panel-body> <div class=order-info> <div class=text-line> <span class=text>订单号:{{orderNo}}</span> <span class=text>创建时间:{{createTime}}</span> </div> <div class=text-line> <span class=text>收件人: {{receiverName}} {{shippingVo.receiverProvince}} {{shippingVo.receiverCity}} {{shippingVo.receiverAddress}} {{shippingVo.receiverMobile}} </span> </div> <div class=text-line> <span class=text>订单状态:{{statusDesc}}</span> </div> <div class=text-line> <span class=text>支付方式:{{paymentTypeDesc}}</span> </div> <div class=text-line> {{#needPay}} <a href=\"./payment.html?orderNumber={{orderNo}}\" class=btn>去支付</a> {{/needPay}} {{#isCancelable}} <a class=\"btn order-cancel\">取消订单</a> {{/isCancelable}} </div> </div> </div> </div> <div class=panel> <div class=panel-title>商品清单</div> <div class=panel-body> <table class=product-table> <tr> <th class=\"cell-th cell-img\">&nbsp;</th> <th class=\"cell-th cell-info\">商品信息</th> <th class=\"cell-th cell-price\">单价</th> <th class=\"cell-th cell-count\">数量</th> <th class=\"cell-th cell-total\">小计</th> </tr> {{#orderItemVoList}} <tr> <td class=\"cell cell-img\"> <a href=\"./detail.html?productId={{productId}}\" target=_blank> <img src={{imageHost}}{{productImage}} alt={{productName}} class=p-img> </a> </td> <td class=\"cell cell-info\"> <a href=\"./detail.html?productId={{productId}}\" class=link target=_blank> {{productName}} </a> </td> <td class=\"cell cell-price\">${{currentUnitPrice}}</td> <td class=\"cell cell-count\">{{quantity}}</td> <td class=\"cell cell-total\">${{totalPrice}}</td> </tr> {{/orderItemVoList}} </table> <p class=total> <span>订单总价:</span> <span class=total-price>${{payment}}</span> </p> </div> </div> ";

/***/ })

});