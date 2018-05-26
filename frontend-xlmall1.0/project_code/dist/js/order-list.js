webpackJsonp([8],{

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(154);


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

/***/ 135:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-13 22:04:55 
	 * @Last Modified by:   whoiszxl 
	 * @Last Modified time: 2018-05-13 22:04:55 
	 */

	'use strict';

	'use strict';
	__webpack_require__(136);
	var _xl                 = __webpack_require__(8);
	var templatePagination  = __webpack_require__(138);

	var Pagination = function(){
	    var _this = this;
	    this.defaultOption = {
	        container       : null,
	        pageNum         : 1,
	        pageRange       : 3,
	        onSelectPage    : null
	    };
	    // 事件的处理
	    $(document).on('click', '.pg-item', function(){
	        var $this = $(this);
	        // 对于active和disabled按钮点击，不做处理
	        if($this.hasClass('active') || $this.hasClass('disabled')){
	            return;
	        }
	        typeof _this.option.onSelectPage === 'function' 
	            ? _this.option.onSelectPage($this.data('value')) : null;
	    });
	};
	// 渲染分页组件
	Pagination.prototype.render = function(userOption){
	    // 合并选项
	    this.option = $.extend({}, this.defaultOption, userOption);
	    // 判断容器是否为合法的jquery对象
	    if(!(this.option.container instanceof jQuery)){
	        return;
	    }
	    // 判断是否只有1页
	    if(this.option.pages <= 1){
	        return;
	    }
	    // 渲染分页内容
	    this.option.container.html(this.getPaginationHtml());
	};
	// 获取分页的html, |上一页| 2 3 4 =5= 6 7 8|下一页|  5/9
	Pagination.prototype.getPaginationHtml = function(){
	    var html        = '',
	        option      = this.option,
	        pageArray   = [],
	        start       = option.pageNum - option.pageRange > 0 
	            ? option.pageNum - option.pageRange : 1,
	        end         = option.pageNum + option.pageRange < option.pages
	            ? option.pageNum + option.pageRange : option.pages;
	    // 上一页按钮的数据
	    pageArray.push({
	        name : '上一页',
	        value : this.option.prePage,
	        disabled : !this.option.hasPreviousPage
	    });
	    // 数字按钮的处理
	    for(var i = start; i <= end; i++){
	        pageArray.push({
	            name : i,
	            value : i,
	            active : (i === option.pageNum)
	        });
	    };
	    // 下一页按钮的数据
	    pageArray.push({
	        name : '下一页',
	        value : this.option.nextPage,
	        disabled : !this.option.hasNextPage
	    });
	    html = _xl.renderHtml(templatePagination, {
	        pageArray   : pageArray,
	        pageNum     : option.pageNum,
	        pages       : option.pages
	    });
	    return html;
	};

	module.exports = Pagination;

/***/ }),

/***/ 136:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 138:
/***/ (function(module, exports) {

	module.exports = "<div class=pg-content> {{#pageArray}} {{#disabled}} <span class=\"pg-item disabled\" data-value={{value}}>{{name}}</span> {{/disabled}} {{^disabled}} {{#active}} <span class=\"pg-item active\" data-value={{value}}>{{name}}</span> {{/active}} {{^active}} <span class=pg-item data-value={{value}}>{{name}}</span> {{/active}} {{/disabled}} {{/pageArray}} <span class=pg-total>{{pageNum}} / {{pages}}</span> </div>";

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

/***/ 154:
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	__webpack_require__(155)
	__webpack_require__(12);
	__webpack_require__(2);
	var navSide = __webpack_require__(128);
	var _xl = __webpack_require__(8);
	var _order = __webpack_require__(143);
	var Pagination = __webpack_require__(135);
	var templateIndex = __webpack_require__(157);


	// page逻辑部分
	var page = {
	    data : {
	        listParam : {
	            pageNum : 1,
	            pageSize : 10
	        }
	    },
	    init : function () {
	        this.onLoad();
	    },
	    onLoad : function () {
	        this.loadOrderList();
	        // 初始化左侧菜单
	        navSide.init({
	            name : 'order-list'
	        })
	    },
	    //加载订单列表
	    loadOrderList : function () {
	        var orderListHtml = '',
	            _this = this,
	            $listCon = $('.order-list-con');
	        $listCon.html('<div class="loading"></div>')
	        _order.getOrderList(this.data.listParam,function (res) {
	            //渲染html
	            orderListHtml = _xl.renderHtml(templateIndex,res);
	            $listCon.html(orderListHtml);
	            _this.loadPagination({
	                hasPreviousPage : res.hasPreviousPage,
	                prePage         : res.prePage,
	                hasNextPage     : res.hasNextPage,
	                nextPage        : res.nextPage,
	                pageNum         : res.pageNum,
	                pages           : res.pages
	            })
	        },function (errMsg) {
	            $listCon.html("<p class='err-tip'>加载订单失败,请刷新后重试</p>")
	        })
	    },
	    //加载分页信息
	    loadPagination : function (pageInfo) {
	        var _this = this;
	        this.pagination ? '' : (this.pagination = new Pagination());
	        this.pagination.render($.extend({},pageInfo,{
	            container : $('.pagination'),
	            onSelectPage : function (pageNum) {
	                _this.data.listParam.pageNum = pageNum;
	                _this.loadOrderList();
	            }
	        }));
	    }
	}
	$(function () {
	    page.init()
	})


/***/ }),

/***/ 155:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 157:
/***/ (function(module, exports) {

	module.exports = "<table class=\"order-list-table header\"> <tr> <th class=\"cell cell-img\">&nbsp;</th> <th class=\"cell cell-info\">商品信息</th> <th class=\"cell cell-price\">单价</th> <th class=\"cell cell-count\">数量</th> <th class=\"cell cell-total\">小计</th> </tr> </table> {{#list}} <table class=\"order-list-table order-item\"> <tr> <td colspan=5 class=order-info> <span class=order-text> <span>订单号:</span> <a href=\"./order-detail.html?orderNumber={{orderNo}}\" class=\"link order-num\" target=_blank>{{orderNo}}</a> </span> <span class=order-text>{{createTime}}</span> <span class=order-text>收件人:{{receiverName}}</span> <span class=order-text>订单状态:{{statusDesc}}</span> <span class=order-text> <span>订单总价:</span> <span class=order-total>${{payment}}</span> </span> <a href=\"./order-detail.html?orderNumber={{orderNo}}\" class=\"link order-detail\" target=_blank>查看详情</a> </td> </tr> {{#orderItemVoList}} <tr> <td class=\"cell cell-img\"> <a href=\"./detail.html?productId={{productId}}\" target=_blank> <img src={{imageHost}}{{productImage}} alt={{productName}} class=p-img> </a> </td> <td class=\"cell cell-info\"> <a href=\"./detail.html?productId={{productId}}\" class=link target=_blank> {{productName}} </a> </td> <td class=\"cell cell-price\">${{currentUnitPrice}}</td> <td class=\"cell cell-count\">{{quantity}}</td> <td class=\"cell cell-total\">${{totalPrice}}</td> </tr> {{/orderItemVoList}} </table> {{/list}} {{^list}} <p class=err-tip>您暂时还没有订单</p> {{/list}}";

/***/ })

});