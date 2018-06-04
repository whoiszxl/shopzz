webpackJsonp([1],[
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(17);


/***/ }),
/* 1 */,
/* 2 */
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
/* 3 */
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),
/* 4 */,
/* 5 */,
/* 6 */,
/* 7 */,
/* 8 */,
/* 9 */,
/* 10 */,
/* 11 */,
/* 12 */
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
/* 13 */
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),
/* 14 */,
/* 15 */
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
/* 16 */
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
/* 17 */
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-14 10:30:56 
	 * @Last Modified by: whoiszxl
	 * @Last Modified time: 2018-05-26 10:07:25
	 */

	'use strict';

	__webpack_require__(18);
	__webpack_require__(2);
	var nav             = __webpack_require__(12);
	var _xl             = __webpack_require__(8);
	var _cart           = __webpack_require__(16);
	var templateIndex   = __webpack_require__(20);

	var page = {
	    data : {
	        
	    },
	    init : function(){
	        this.onLoad();
	        this.bindEvent();
	    },
	    onLoad : function(){
	        this.loadCart();
	    },
	    bindEvent : function(){
	        var _this = this;
	        // 商品的选择 / 取消选择
	        $(document).on('click', '.cart-select', function(){
	            var $this = $(this),
	                productId = $this.parents('.cart-table').data('product-id');
	            // 选中
	            if($this.is(':checked')){
	                _cart.selectProduct(productId, function(res){
	                    _this.renderCart(res);
	                }, function(errMsg){
	                    _this.showCartError();
	                });
	            }
	            // 取消选中
	            else{
	                _cart.unselectProduct(productId, function(res){
	                    _this.renderCart(res);
	                }, function(errMsg){
	                    _this.showCartError();
	                });
	            }
	        });
	        // 商品的全选 / 取消全选
	        $(document).on('click', '.cart-select-all', function(){
	            var $this = $(this);
	            // 全选
	            if($this.is(':checked')){
	                _cart.selectAllProduct(function(res){
	                    _this.renderCart(res);
	                }, function(errMsg){
	                    _this.showCartError();
	                });
	            }
	            // 取消全选
	            else{
	                _cart.unselectAllProduct(function(res){
	                    _this.renderCart(res);
	                }, function(errMsg){
	                    _this.showCartError();
	                });
	            }
	        });
	        // 商品数量的变化
	        $(document).on('click', '.count-btn', function(){
	            var $this       = $(this),
	                $pCount     = $this.siblings('.count-input'),
	                currCount   = parseInt($pCount.val()),
	                type        = $this.hasClass('plus') ? 'plus' : 'minus',
	                productId   = $this.parents('.cart-table').data('product-id'),
	                minCount    = 1,
	                maxCount    = parseInt($pCount.data('max')),
	                newCount    = 0;
	            if(type === 'plus'){
	                if(currCount >= maxCount){
	                    _xl.errorTips('该商品数量已达到上限');
	                    return;
	                }
	                newCount = currCount + 1;
	            }else if(type === 'minus'){
	                if(currCount <= minCount){
	                    return;
	                }
	                newCount = currCount - 1;
	            }
	            // 更新购物车商品数量
	            _cart.updateProduct({
	                productId : productId,
	                count : newCount
	            }, function(res){
	                _this.renderCart(res);
	            }, function(errMsg){
	                _this.showCartError();
	            });
	        });
	        // 删除单个商品
	        $(document).on('click', '.cart-delete', function(){
	            if(window.confirm('确认要删除该商品？')){
	                var productId = $(this).parents('.cart-table')
	                    .data('product-id');
	                _this.deleteCartProduct(productId);
	            }
	        });
	        // 删除选中商品
	        $(document).on('click', '.delete-selected', function(){
	            if(window.confirm('确认要删除选中的商品？')){
	                var arrProductIds = [],
	                    $selectedItem = $('.cart-select:checked');
	                // 循环查找选中的productIds
	                for(var i = 0, iLength = $selectedItem.length; i < iLength; i ++){
	                    arrProductIds
	                        .push($($selectedItem[i]).parents('.cart-table').data('product-id'));
	                }
	                if(arrProductIds.length){
	                    _this.deleteCartProduct(arrProductIds.join(','));
	                }
	                else{
	                    _xl.errorTips('您还没有选中要删除的商品');
	                }  
	            }
	        });
	        // 提交购物车
	        $(document).on('click', '.btn-submit', function(){
	            // 总价大于0，进行提交
	            if(_this.data.cartInfo && _this.data.cartInfo.cartTotalPrice > 0){
	                window.location.href = './order-confirm.html';
	            }else{
	                _xl.errorTips('请选择商品后再提交');
	            }
	        });
	    },
	    // 加载购物车信息
	    loadCart : function(){
	        var _this       = this;
	        // 获取购物车列表
	        _cart.getCartList(function(res){
	            _this.renderCart(res);
	        }, function(errMsg){
	            _this.showCartError();
	        })
	    },
	    // 渲染购物车
	    renderCart : function(data){
	        this.filter(data);
	        // 缓存购物车信息
	        this.data.cartInfo = data;
	        // 生成HTML
	        var cartHtml = _xl.renderHtml(templateIndex, data);
	        $('.page-wrap').html(cartHtml);
	        // 通知导航的购物车更新数量
	        nav.loadCartCount();
	    },
	    // 删除指定商品，支持批量，productId用逗号分割
	    deleteCartProduct : function(productIds){
	        var _this = this;
	        _cart.deleteProduct(productIds, function(res){
	            _this.renderCart(res);
	        }, function(errMsg){
	            _this.showCartError();
	        });
	    },
	    // 数据匹配
	    filter : function(data){
	        data.notEmpty = !!data.cartProductVoList.length;
	    },
	    // 显示错误信息
	    showCartError: function(){
	        $('.page-wrap').html('<p class="err-tip">哪里不对了，刷新下试试吧。</p>');
	    }
	};
	$(function(){
	    page.init();
	})

/***/ }),
/* 18 */
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),
/* 19 */,
/* 20 */
/***/ (function(module, exports) {

	module.exports = "{{#notEmpty}} <div class=cart-header> <table class=cart-table> <tr> <th class=\"cart-cell cell-check\"> <label class=cart-label> {{#allChecked}} <input type=checkbox class=cart-select-all checked=checked /> {{/allChecked}} {{^allChecked}} <input type=checkbox class=cart-select-all /> {{/allChecked}} <span>全选</span> </label> </th> <th class=\"cart-cell cell-info\">商品信息</th> <th class=\"cart-cell cell-price\">单价</th> <th class=\"cart-cell cell-count\">数量</th> <th class=\"cart-cell cell-total\">合计</th> <th class=\"cart-cell cell-opera\">操作</th> </tr> </table> </div> <div class=cart-list> {{#cartProductVoList}} <table class=cart-table data-product-id={{productId}}> <tr> <td class=\"cart-cell cell-check\"> <label class=cart-label> {{#productChecked}} <input type=checkbox class=cart-select checked=checked /> {{/productChecked}} {{^productChecked}} <input type=checkbox class=cart-select /> {{/productChecked}} </label> </td> <td class=\"cart-cell cell-img\"> <a class=link href=\"./detail.html?productId={{productId}}\"> <img class=p-img src={{imageHost}}{{productMainImage}} alt={{productName}} /> </a> </td> <td class=\"cart-cell cell-info\"> <a class=link href=\"./detail.html?productId={{productId}}\">{{productName}}</a> </td> <td class=\"cart-cell cell-price\">￥{{productPrice}}</td> <td class=\"cart-cell cell-count\"> <span class=\"count-btn minus\">-</span> <input class=count-input value={{quantity}} data-max={{productStock}} /> <span class=\"count-btn plus\">+</span> </td> <td class=\"cart-cell cell-total\">￥{{productTotalPrice}}</td> <td class=\"cart-cell cell-opera\"> <span class=\"link cart-delete\">删除</span> </td> </tr> </table> {{/cartProductVoList}} </div> <div class=cart-footer> <div class=select-con> <label> {{#allChecked}} <input type=checkbox class=cart-select-all checked=checked /> {{/allChecked}} {{^allChecked}} <input type=checkbox class=cart-select-all /> {{/allChecked}} <span>全选</span> </label> </div> <div class=delete-con> <span class=\"link delete-selected\"> <i class=\"fa fa-trash-o\"></i> <span>删除选中</span> </span> </div> <div class=submit-con> <span>总价：</span> <span class=submit-total>￥{{cartTotalPrice}}</span> <span class=\"btn btn-submit\">去结算</span> </div> </div> {{/notEmpty}} {{^notEmpty}} <p class=err-tip> <span>您的购物车空空如也，</span> <a href=./index.html>立即去购物</a> </p> {{/notEmpty}}";

/***/ })
]);