webpackJsonp([3],{

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(110);


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

/***/ 110:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-13 23:29:54 
	 * @Last Modified by: whoiszxl
	 * @Last Modified time: 2018-05-14 00:16:39
	 */

	'use strict';

	__webpack_require__(111);
	__webpack_require__(12);
	__webpack_require__(2);
	var _xl             = __webpack_require__(8);
	var _product        = __webpack_require__(113);
	var _cart           = __webpack_require__(16);
	var templateIndex   = __webpack_require__(114);

	var page = {
	    data : {
	        productId : _xl.getUrlParam('productId') || '',
	    },
	    init : function(){
	        this.onLoad();
	        this.bindEvent();
	    },
	    onLoad : function(){
	        // 如果没有传productId, 自动跳回首页
	        if(!this.data.productId){
	            _xl.goHome();
	        }
	        this.loadDetail();
	    },
	    bindEvent : function(){
	        var _this = this;
	        // 图片预览
	        $(document).on('mouseenter', '.p-img-item', function(){
	            var imageUrl   = $(this).find('.p-img').attr('src');
	            $('.main-img').attr('src', imageUrl);
	        });
	        // count的操作
	        $(document).on('click', '.p-count-btn', function(){
	            var type        = $(this).hasClass('plus') ? 'plus' : 'minus',
	                $pCount     = $('.p-count'),
	                currCount   = parseInt($pCount.val()),
	                minCount    = 1,
	                maxCount    = _this.data.detailInfo.stock || 1;
	            if(type === 'plus'){
	                $pCount.val(currCount < maxCount ? currCount + 1 : maxCount);
	            }
	            else if(type === 'minus'){
	                $pCount.val(currCount > minCount ? currCount - 1 : minCount);
	            }
	        });
	        // 加入购物车
	        $(document).on('click', '.cart-add', function(){
	            _cart.addToCart({
	                productId   : _this.data.productId,
	                count       : $('.p-count').val()
	            }, function(res){
	                window.location.href = './result.html?type=cart-add';
	            }, function(errMsg){
	                _xl.errorTips(errMsg);
	            });
	        });
	    },
	    // 加载商品详情的数据
	    loadDetail : function(){
	        var _this       = this,
	            html        = '',
	            $pageWrap   = $('.page-wrap');
	        // loading
	        $pageWrap.html('<div class="loading"></div>');
	        // 请求detail信息
	        _product.getProductDetail(this.data.productId, function(res){
	            _this.filter(res);
	            // 缓存住detail的数据
	            _this.data.detailInfo = res;
	            // render
	            html = _xl.renderHtml(templateIndex, res);
	            $pageWrap.html(html);
	        }, function(errMsg){
	            $pageWrap.html('<p class="err-tip">此商品太淘气，找不到了</p>');
	        });
	    },
	    // 数据匹配
	    filter : function(data){
	        data.subImages = data.subImages.split(',');
	    }
	};
	$(function(){
	    page.init();
	})

/***/ }),

/***/ 111:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 113:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-13 22:06:02 
	 * @Last Modified by: whoiszxl
	 * @Last Modified time: 2018-05-25 17:22:48
	 */

	'use strict';

	var _xl = __webpack_require__(8);

	var _product = {
	    // 获取商品列表
	    getProductList : function(listParam, resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/product/list'),
	            data    : listParam,
	            success : resolve,
	            error   : reject
	        });
	    },
	    // 获取商品详细信息
	    getProductDetail : function(productId, resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/product/detail'),
	            data    : {
	                productId : productId
	            },
	            success : resolve,
	            error   : reject
	        });
	    }
	}
	module.exports = _product;

/***/ }),

/***/ 114:
/***/ (function(module, exports) {

	module.exports = "<div class=intro-wrap> <div class=p-img-con> <div class=main-img-con> <img class=main-img src={{imageHost}}{{mainImage}} alt={{name}} /> </div> <ul class=p-img-list> {{#subImages}} <li class=p-img-item> <img class=p-img src={{imageHost}}{{.}} alt={{name}} /> </li> {{/subImages}} </ul> </div> <div class=p-info-con> <h1 class=p-name>{{name}}</h1> <p class=p-subtitle>{{subtitle}}</p> <div class=\"p-info-item p-price-con\"> <span class=label>价格:</span> <span class=info>￥{{price}}</span> </div> <div class=p-info-item> <span class=label>库存:</span> <span class=info>{{stock}}</span> </div> <div class=\"p-info-item p-count-con\"> <span class=label>数量:</span> <input class=p-count value=1 readonly=\"\"/> <span class=\"p-count-btn plus\">+</span> <span class=\"p-count-btn minus\">-</span> </div> <div class=p-info-item> <a class=\"btn cart-add\">加入购物车</a> </div> </div> </div> <div class=detail-wrap> <div class=detail-tab-con> <ul class=tab-list> <li class=\"tab-item active\">详细描述</li> </ul> </div> <div class=detail-con> {{{detail}}} </div> </div>";

/***/ })

});