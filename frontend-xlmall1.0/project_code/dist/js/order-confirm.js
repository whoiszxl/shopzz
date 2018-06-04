webpackJsonp([6],{

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(136);


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

/***/ 136:
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	__webpack_require__(137);
	__webpack_require__(2);
	__webpack_require__(12);
	var _xl = __webpack_require__(8);
	var _order = __webpack_require__(139);
	var _address = __webpack_require__(140);
	var templateAddress = __webpack_require__(141);
	var templateProduct = __webpack_require__(142);
	var addressModal = __webpack_require__(143);
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
	            console.log(res);
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


/***/ }),

/***/ 137:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 139:
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

/***/ 140:
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	var _xl = __webpack_require__(8);
	var _address = {
	    //获取地址列表
	    getAddressList : function(resolve,reject){
	        _xl.request({
	            method : 'post',
	            url:_xl.getServerUrl('/shipping/list'),
	            data : {
	                pageSize : 50
	            },
	            success : resolve,
	            error : reject
	        })
	    },
	    //新建收件人
	    save : function(addressInfo,resolve,reject){
	        _xl.request({
	            method : 'post',
	            url:_xl.getServerUrl('/shipping/add'),
	            data : addressInfo,
	            success : resolve,
	            error : reject
	        })
	    },
	    //更新收件人
	    update : function(addressInfo,resolve,reject){
	        _xl.request({
	            method : 'post',
	            url:_xl.getServerUrl('/shipping/update'),
	            data : addressInfo,
	            success : resolve,
	            error : reject
	        })
	    },
	    //删除收件人
	    deleteAddress : function(shippingId,resolve,reject){
	        _xl.request({
	            method : 'post',
	            url:_xl.getServerUrl('/shipping/delete'),
	            data : {
	                shippingId : shippingId
	            },
	            success : resolve,
	            error : reject
	        })
	    },
	    //获取单条地址信息
	    getAddress : function(shippingId,resolve,reject){
	        _xl.request({
	            method : 'get',
	            url:_xl.getServerUrl('/shipping/selects'),
	            data : {
	                shippingId : shippingId
	            },
	            success : resolve,
	            error : reject
	        })
	    },
	}

	module.exports = _address;

/***/ }),

/***/ 141:
/***/ (function(module, exports) {

	module.exports = "{{#list}} {{#isActive}} <div class=\"address-item active\" data-id={{id}}> {{/isActive}} {{^isActive}} <div class=address-item data-id={{id}}> {{/isActive}} <div class=address-title> {{receiverCity}} {{receiverProvince}} ({{receiverName}} 收) </div> <div class=address-detail> {{receiverAddress}} {{receiverPhone}} </div> <div class=address-opera> <span class=\"link address-update\">编辑</span> <span class=\"link address-delete\">删除</span> </div> </div> {{/list}} <div class=address-add> <div class=address-new> <i class=\"fa fa-plus\"></i> <div class=text>使用新地址</div> </div> </div></div>";

/***/ }),

/***/ 142:
/***/ (function(module, exports) {

	module.exports = "<table class=product-table> <tr> <th class=cell-img>&nbsp;</th> <th class=cell-info>商品描述</th> <th class=cell-price>价格</th> <th class=cell-count>数量</th> <th class=cell-total>小计</th> </tr> {{#orderItemVoList}} <tr> <td class=cell-img> <a href=\"./detail.html?productId={{productId}}\" target=_blank> <img src={{imageHost}}{{productImage}} alt={{productName}} class=p-img> </a> </td> <td class=cell-info> <a href=\"./detail.html?productId=\" class=link target=_blank>{{productName}}</a> </td> <td class=cell-price>￥{{currentUnitPrice}}</td> <td class=cell-count>{{quantity}}</td> <td class=cell-total>{{totalPrice}}</td> </tr> {{/orderItemVoList}} </table> <div class=submit-con> <span>订单总价:</span> <span class=submit-total>￥{{productTotalPrice}}</span> <span class=\"btn order-submit\">提交订单</span> </div>";

/***/ }),

/***/ 143:
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	var _xl = __webpack_require__(8);
	var _cities = __webpack_require__(144);
	var _address = __webpack_require__(140);
	var templateAddressModal = __webpack_require__(145);
	var addressModal = {
	    show : function (option) {
	        //option绑定
	        this.option = option;
	        this.option.data = option.data || {}
	        this.$modalWrap = $('.modal-wrap');
	        //渲染页面
	        this.loadModal();
	        //绑定事件
	        this.bindEvent()
	    },
	    bindEvent : function () {
	        var _this = this;
	        //省份和城市的二级联动
	        this.$modalWrap.find('#receiver-province').change(function () {
	            var selectedProvince = $(this).val();
	            _this.loadCities(selectedProvince);
	        })
	        //提交收货地址
	        this.$modalWrap.find('.address-btn').click(function () {
	            var receiverInfo = _this.getReceiverInfo(),
	                isUpdate = _this.option.isUpdate;
	            //使用新地址,且验证通过
	            if(!isUpdate && receiverInfo.status){
	                _address.save(receiverInfo.data,function (res) {
	                    _xl.successTips('地址添加成功');
	                    _this.hide();
	                    typeof _this.option.onSuccess() === 'function' && _this.option.onSuccess(res);
	                },function (errMsg) {
	                    _xl.errorTips(errMsg);
	                })
	            }
	            //更新收件人,并且验证通过
	            else if(isUpdate && receiverInfo.status){
	                _address.update(receiverInfo.data,function (res) {
	                    _xl.successTips('地址修改成功');
	                    _this.hide();
	                    typeof _this.option.onSuccess() === 'function' && _this.option.onSuccess(res);
	                },function (errMsg) {
	                    _xl.errorTips(errMsg);
	                })
	            }
	            //验证不通过
	            else{
	                _xl.errorTips(receiverInfo.errMsg || '好像哪里不对了');
	            }
	        })
	        //保证点击modal内容区的时候,不关闭弹窗
	        this.$modalWrap.find('.modal-container').click(function (e) {
	            e.stopPropagation();
	        })
	        //点击x或者蒙版关闭弹窗
	        this.$modalWrap.find('.close').click(function () {
	            _this.hide();
	        })
	    },
	    loadModal : function () {
	        var addressModalHtml = _xl.renderHtml(templateAddressModal,{
	            isUpdate : this.option.isUpdate,
	            data : this.option.data
	        });
	        this.$modalWrap.html(addressModalHtml);
	        //加载省份
	        this.loadProvince();
	    },
	    //加载省份信息
	    loadProvince : function () {
	        var provinces = _cities.getProvinces() || [],
	            $provincesSelect = this.$modalWrap.find('#receiver-province');
	        $provincesSelect.html(this.getSelectOption(provinces));
	        //如果是更新地址,并且有省份信息,做省份的回填
	        if(this.option.isUpdate && this.option.data.receiverProvince){
	            $provincesSelect.val(this.option.data.receiverProvince);
	            this.loadCities(this.option.data.receiverProvince)
	            console.log('省份')
	        }
	    },
	    //获取select框的选项,输入:array,输出:html
	    getSelectOption : function (optionArray) {
	        var html = '<option value="">请选择</option>';
	        for(var i = 0,length = optionArray.length;i < length; i++){
	            html += '<option value="' + optionArray[i] + '">' + optionArray[i] + '</option>'
	        }
	        return html;
	    },
	    //加载城市信息
	    loadCities : function (provinceName) {
	        var cities = _cities.getCities(provinceName) || [],
	            $citySelect = this.$modalWrap.find('#receiver-city');
	        $citySelect.html(this.getSelectOption(cities));
	        //如果是更新地址,并且有城市信息,做城市的回填
	        if(this.option.isUpdate && this.option.data.receiverCity){
	            $citySelect.val(this.option.data.receiverCity);
	            console.log($citySelect.val());
	        }
	    },
	    //获取表单收件人信息,并做表单的验证
	    getReceiverInfo : function () {
	        var receiverInfo = {},
	            result = {
	                status : false
	            };
	        receiverInfo.receiverName       = $.trim(this.$modalWrap.find('#receiver-name').val());
	        receiverInfo.receiverProvince   = $.trim(this.$modalWrap.find('#receiver-province').val());
	        receiverInfo.receiverCity       = $.trim(this.$modalWrap.find('#receiver-city').val());
	        receiverInfo.receiverAddress    = $.trim(this.$modalWrap.find('#receiver-address').val());
	        receiverInfo.receiverPhone      = $.trim(this.$modalWrap.find('#receiver-phone').val());
	        receiverInfo.receiverZip        = $.trim(this.$modalWrap.find('#receiver-zip').val());

	        if(this.option.isUpdate){
	            receiverInfo.id = this.$modalWrap.find('#receiver-id').val();
	        }
	        //表单验证
	        if(!receiverInfo.receiverName){
	            result.errMsg = '请输入收件人姓名';
	        }
	        else if(!receiverInfo.receiverProvince){
	            result.errMsg = '请选择收件人所在省份';
	        }
	        else if(!receiverInfo.receiverCity){
	            result.errMsg = '请选择收件人所在城市';
	        }
	        else if(!receiverInfo.receiverAddress){
	            result.errMsg = '请选择收件人详细地址';
	        }
	        else if(!receiverInfo.receiverPhone){
	            result.errMsg = '请选择收件人手机号';
	        }
	        //所有验证都通过
	        else{
	            result.status = true;
	            result.data = receiverInfo
	        }
	        return result;
	    },
	    //关闭弹窗
	    hide : function () {
	        this.$modalWrap.empty();
	    }
	};

	module.exports = addressModal;


/***/ }),

/***/ 144:
/***/ (function(module, exports) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-26 00:45:02 
	 * @Last Modified by:   whoiszxl 
	 * @Last Modified time: 2018-05-26 00:45:02 
	 */

	'use strict';

	var _cities = {
	    cityInfo : {
	        '北京':['北京'],
	        '上海':['上海'],
	        '天津':['天津'],
	        '重庆':['重庆'],
	        '河北省':['石家庄','张家口','承德','秦皇岛','唐山','廊坊','保定','沧州','衡水','邢台','邯郸'],
	        '山西省':['太原','大同','朔州','阳泉','长治','晋城','忻州','吕梁','晋中','临汾','运城'],
	        '辽宁省':['沈阳','朝阳','阜新','铁岭','抚顺','本溪','辽阳','鞍山','丹东','大连','营口','盘锦','锦州','葫芦岛'],
	        '吉林省':['长春','白城','松原','吉林','四平','辽源','通化','白山','延边'],
	        '黑龙江省':['哈尔滨','齐齐哈尔','黑河','大庆','伊春','鹤岗','佳木斯','双鸭山','七台河','鸡西','牡丹江','绥化','大兴安'],
	        '江苏省':['南京','徐州','连云港','宿迁','淮阴','盐城','扬州','泰州','南通','镇江','常州','无锡','苏州'],
	        '浙江省':['杭州','湖州','嘉兴','舟山','宁波','绍兴','金华','台州','温州','丽水'],
	        '安徽省':['合肥','宿州','淮北','阜阳','蚌埠','淮南','滁州','马鞍山','芜湖','铜陵','安庆','黄山','六安','巢湖','池州','宣城'],
	        '福建省':['福州','南平','三明','莆田','泉州','厦门','漳州','龙岩','宁德'],
	        '江西省':['南昌','九江','景德镇','鹰潭','新余','萍乡','赣州','上饶','抚州','宜春','吉安'],
	        '山东省':['济南','聊城','德州','东营','淄博','潍坊','烟台','威海','青岛','日照','临沂','枣庄','济宁','泰安','莱芜','滨州','菏泽'],
	        '河南省':['郑州','三门峡','洛阳','焦作','新乡','鹤壁','安阳','濮阳','开封','商丘','许昌','漯河','平顶山','南阳','信阳','周口','驻马店'],
	        '湖北省':['武汉','十堰','襄攀','荆门','孝感','黄冈','鄂州','黄石','咸宁','荆州','宜昌','恩施','襄樊'],
	        '湖南省':['长沙','张家界','常德','益阳','岳阳','株洲','湘潭','衡阳','郴州','永州','邵阳','怀化','娄底','湘西'],
	        '广东省':['广州','清远','韶关','河源','梅州','潮州','汕头','揭阳','汕尾','惠州','东莞','深圳','珠海','江门','佛山','肇庆','云浮','阳江','茂名','湛江'],
	        '海南省':['海口','三亚'],
	        '四川省':['成都','广元','绵阳','德阳','南充','广安','遂宁','内江','乐山','自贡','泸州','宜宾','攀枝花','巴中','达川','资阳','眉山','雅安','阿坝','甘孜','凉山'],
	        '贵州省':['贵阳','六盘水','遵义','毕节','铜仁','安顺','黔东南','黔南','黔西南'],
	        '云南省':['昆明','曲靖','玉溪','丽江','昭通','思茅','临沧','保山','德宏','怒江','迪庆','大理','楚雄','红河','文山','西双版纳'],
	        '陕西省':['西安','延安','铜川','渭南','咸阳','宝鸡','汉中','榆林','商洛','安康'],
	        '甘肃省':['兰州','嘉峪关','金昌','白银','天水','酒泉','张掖','武威','庆阳','平凉','定西','陇南','临夏','甘南'],
	        '青海省':['西宁','海东','西宁','海北','海南','黄南','果洛','玉树','海西'],
	        '内蒙古':['呼和浩特','包头','乌海','赤峰','呼伦贝尔盟','兴安盟','哲里木盟','锡林郭勒盟','乌兰察布盟','鄂尔多斯','巴彦淖尔盟','阿拉善盟'],
	        '广西':['南宁','桂林','柳州','梧州','贵港','玉林','钦州','北海','防城港','南宁','百色','河池','柳州','贺州'],
	        '西藏':['拉萨','那曲','昌都','林芝','山南','日喀则','阿里'],
	        '宁夏':['银川','石嘴山','吴忠','固原'],
	        '新疆':['乌鲁木齐','克拉玛依','喀什','阿克苏','和田','吐鲁番','哈密','博尔塔拉','昌吉','巴音郭楞','伊犁','塔城','阿勒泰'],
	        '香港':['香港'],
	        '澳门':['澳门'],
	        '台湾':['台北','台南','其他']
	    },
	    // 获取所有的省份
	    getProvinces : function(){
	        var provinces = [];
	        for(var item in this.cityInfo){
	            provinces.push(item);
	        }
	        return provinces;
	    },
	    // 获取某省份的所有城市
	    getCities : function(provinceName){
	        return this.cityInfo[provinceName] || [];
	    }
	}

	module.exports = _cities

/***/ }),

/***/ 145:
/***/ (function(module, exports) {

	module.exports = "<div class=\"modal close\"> <div class=modal-container> <div class=modal-header> {{#isUpdate}} <h1 class=modal-title>更新地址</h1> {{/isUpdate}} {{^isUpdate}} <h1 class=modal-title>使用新地址</h1> {{/isUpdate}} <i class=\"fa fa-close close\"></i> </div> <div class=modal-body> <div class=form> <div class=form-line> <label for=receiver-name class=label> <span class=require>*</span>收件人姓名:</label> <input type=text class=form-item id=receiver-name placeholder=请输入收件人姓名 value={{data.receiverName}}> </div> <div class=form-line> <label for=receiver-province class=label> <span class=require>*</span>所在城市:</label> <select name=\"\" id=receiver-province class=form-item> <option value=\"\"> 请选择 </option> </select> <select name=\"\" id=receiver-city class=form-item> <option value=\"\"> 请选择 </option> </select> </div> <div class=form-line> <label for=receiver-address class=label> <span class=require>*</span>详细地址:</label> <input type=text class=form-item id=receiver-address placeholder=请精确到门牌号 value={{data.receiverAddress}}> </div> <div class=form-line> <label for=receiver-phone class=label> <span class=require>*</span>收件人手机:</label> <input type=text class=form-item id=receiver-phone placeholder=请输入11位手机号 value={{data.receiverName}}> </div> <div class=form-line> <label for=receiver-zip class=label>邮政编码:</label> <input type=text class=form-item id=receiver-zip placeholder=请输入邮政编码 value={{data.receiverZip}}> </div> <div class=form-line> <input type=hidden value={{data.id}} id=receiver-id /> <a class=\"btn address-btn\">保存收货地址</a> </div> </div> </div> </div> </div>";

/***/ })

});