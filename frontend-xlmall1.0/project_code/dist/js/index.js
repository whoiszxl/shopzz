webpackJsonp([4],{

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(115);


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

/***/ 115:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-11 10:32:21 
	 * @Last Modified by: whoiszxl
	 * @Last Modified time: 2018-06-04 21:06:41
	 */

	'use strict';
	__webpack_require__(116);
	__webpack_require__(12);
	__webpack_require__(2);
	__webpack_require__(118);

	var templateBanner  = __webpack_require__(122);
	var navSide = __webpack_require__(123);
	var _xl = __webpack_require__(8);
	var _article = __webpack_require__(127);

	$(function() {
	    
	    // 前一张和后一张操作的事件绑定
	    $('.banner-con .banner-arrow').click(function(){
	        var forward = $(this).hasClass('prev') ? 'prev' : 'next';
	        $slider.data('unslider')[forward]();
	    });

	    
	    _article.getBannerList(function(res){

	        // 渲染banner的html
	        console.log(res);
	        var bannerHtml  = _xl.renderHtml(templateBanner, {
	            list :  res
	        });
	        $('.banner-con').html(bannerHtml);

	        // 初始化banner
	        var $slider     = $('.banner').unslider({
	            dots: true
	        });
	    }, function(errMsg){
	        _xl.errorTips(errMsg);
	        _xl.doLogin();
	    });

	    
	    
	    
	});


/***/ }),

/***/ 116:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 118:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-12 18:33:08 
	 * @Last Modified by: whoiszxl
	 * @Last Modified time: 2018-05-12 18:36:57
	 */

	'use strict';

	__webpack_require__(119);
	__webpack_require__(121);

/***/ }),

/***/ 119:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 121:
/***/ (function(module, exports) {

	window.console&&console.warn("This version of Unslider is due to be deprecated by December 1. Please visit unslider.com for details on how to upgrade."),function(t,s){if(!t)return s;var i=function(){this.el=s,this.items=s,this.sizes=[],this.max=[0,0],this.current=0,this.interval=s,this.opts={speed:500,delay:3e3,complete:s,keys:!s,dots:s,fluid:s};var i=this;this.init=function(s,i){return this.el=s,this.ul=s.children("ul"),this.max=[s.outerWidth(),s.outerHeight()],this.items=this.ul.children("li").each(this.calculate),this.opts=t.extend(this.opts,i),this.setup(),this},this.calculate=function(s){var e=t(this),n=e.outerWidth(),h=e.outerHeight();i.sizes[s]=[n,h],n>i.max[0]&&(i.max[0]=n),h>i.max[1]&&(i.max[1]=h)},this.setup=function(){if(this.el.css({overflow:"hidden",width:i.max[0],height:this.items.first().outerHeight()}),this.ul.css({width:100*this.items.length+"%",position:"relative"}),this.items.css("width",100/this.items.length+"%"),this.opts.delay!==s&&(this.start(),this.el.hover(this.stop,this.start)),this.opts.keys&&t(document).keydown(this.keys),this.opts.dots&&this.dots(),this.opts.fluid){var e=function(){i.el.css("width",Math.min(Math.round(i.el.outerWidth()/i.el.parent().outerWidth()*100),100)+"%")};e(),t(window).resize(e)}this.opts.arrows&&this.el.parent().append('<p class="arrows"><span class="prev">芒鈥犅�</span><span class="next">芒鈥犫€�</span></p>').find(".arrows span").click(function(){t.isFunction(i[this.className])&&i[this.className]()}),t.event.swipe&&this.el.on("swipeleft",i.prev).on("swiperight",i.next)},this.move=function(s,e){this.items.eq(s).length||(s=0),0>s&&(s=this.items.length-1);var n=this.items.eq(s),h={height:n.outerHeight()},o=e?5:this.opts.speed;this.ul.is(":animated")||(i.el.find(".dot:eq("+s+")").addClass("active").siblings().removeClass("active"),this.el.animate(h,o)&&this.ul.animate(t.extend({left:"-"+s+"00%"},h),o,function(){i.current=s,t.isFunction(i.opts.complete)&&!e&&i.opts.complete(i.el)}))},this.start=function(){i.interval=setInterval(function(){i.move(i.current+1)},i.opts.delay)},this.stop=function(){return i.interval=clearInterval(i.interval),i},this.keys=function(s){var e=s.which,n={37:i.prev,39:i.next,27:i.stop};t.isFunction(n[e])&&n[e]()},this.next=function(){return i.stop().move(i.current+1)},this.prev=function(){return i.stop().move(i.current-1)},this.dots=function(){var s='<ol class="dots">';t.each(this.items,function(t){s+='<li class="dot'+(1>t?" active":"")+'">'+(t+1)+"</li>"}),s+="</ol>",this.el.addClass("has-dots").append(s).find(".dot").click(function(){i.move(t(this).index())})}};t.fn.unslider=function(s){var e=this.length;return this.each(function(n){var h=t(this),o=(new i).init(h,s);h.data("unslider"+(e>1?"-"+(n+1):""),o)})}}(window.jQuery,!1);

/***/ }),

/***/ 122:
/***/ (function(module, exports) {

	module.exports = "<div class=banner> <ul> {{#list}} <li> <a href={{jumpurl}} target=_blank> <img class=banner-img src={{imgurl}} alt={{title}} /> </a> </li> {{/list}} </ul> <div class=\"banner-arrow prev\"> <i class=\"fa fa-angle-left\"></i> </div> <div class=\"banner-arrow next\"> <i class=\"fa fa-angle-right\"></i> </div> </div>";

/***/ }),

/***/ 123:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-12 10:39:56 
	 * @Last Modified by: whoiszxl
	 * @Last Modified time: 2018-05-12 10:48:51
	 */

	'use strict';

	__webpack_require__(124);
	var _xl             = __webpack_require__(8);
	var templateIndex   = __webpack_require__(126);
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

/***/ 124:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 126:
/***/ (function(module, exports) {

	module.exports = "{{#navList}} {{#isActive}} <li class=\"nav-item active\"> {{/isActive}} {{^isActive}} </li><li class=nav-item> {{/isActive}} <a class=link href={{href}}>{{desc}}</a> </li> {{/navList}} ";

/***/ }),

/***/ 127:
/***/ (function(module, exports, __webpack_require__) {

	
	'use strict';

	var _xl = __webpack_require__(8);

	var _article = {
	    // 获取商品列表
	    getBannerList : function(resolve, reject){
	        _xl.request({
	            url     : _xl.getServerUrl('/article/banners'),
	            success : resolve,
	            error   : reject
	        });
	    }
	}
	module.exports = _article;

/***/ })

});