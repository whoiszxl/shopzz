webpackJsonp([16],{

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(182);


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

/***/ 162:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-11 23:10:37 
	 * @Last Modified by:   whoiszxl 
	 * @Last Modified time: 2018-05-11 23:10:37 
	 */

	'use strict';
	__webpack_require__(163);

/***/ }),

/***/ 163:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 182:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-12 15:50:16 
	 * @Last Modified by: whoiszxl
	 * @Last Modified time: 2018-05-12 15:52:07
	 */

	'use strict';
	__webpack_require__(183);
	__webpack_require__(162);
	var _user   = __webpack_require__(15);
	var _xl     = __webpack_require__(8);

	// 表单里的错误提示
	var formError = {
	    show : function(errMsg){
	        $('.error-item').show().find('.err-msg').text(errMsg);
	    },
	    hide : function(){
	        $('.error-item').hide().find('.err-msg').text('');
	    }
	};

	// page 逻辑部分
	var page = {
	    init: function(){
	        this.bindEvent();
	    },
	    bindEvent : function(){
	        var _this = this;
	        // 验证username
	        $('#username').blur(function(){
	            var username = $.trim($(this).val());
	            // 如果用户名为空，我们不做验证
	            if(!username){
	                return;
	            }
	            // 异步验证用户名是否存在
	            _user.checkUsername(username, function(res){
	                formError.hide();
	            }, function(errMsg){
	                formError.show(errMsg);
	            });
	        });
	        // 注册按钮的点击
	        $('#submit').click(function(){
	            _this.submit();
	        });
	        // 如果按下回车，也进行提交
	        $('.user-content').keyup(function(e){
	            // keyCode == 13 表示回车键
	            if(e.keyCode === 13){
	                _this.submit();
	            }
	        });
	    },
	    // 提交表单
	    submit : function(){
	        var formData = {
	                username        : $.trim($('#username').val()),
	                password        : $.trim($('#password').val()),
	                passwordConfirm : $.trim($('#password-confirm').val()),
	                phone           : $.trim($('#phone').val()),
	                email           : $.trim($('#email').val()),
	                question        : $.trim($('#question').val()),
	                answer          : $.trim($('#answer').val())
	            },
	            // 表单验证结果
	            validateResult = this.formValidate(formData);
	        // 验证成功
	        if(validateResult.status){
	            _user.register(formData, function(res){
	                window.location.href = './result.html?type=register';
	            }, function(errMsg){
	                formError.show(errMsg);
	            });
	        }
	        // 验证失败
	        else{
	            // 错误提示
	            formError.show(validateResult.msg);
	        }

	    },
	    // 表单字段的验证
	    formValidate : function(formData){
	        var result = {
	            status  : false,
	            msg     : ''
	        };
	        // 验证用户名是否为空
	        if(!_xl.validate(formData.username, 'require')){
	            result.msg = '用户名不能为空';
	            return result;
	        }
	        // 验证密码是否为空
	        if(!_xl.validate(formData.password, 'require')){
	            result.msg = '密码不能为空';
	            return result;
	        }
	        // 验证密码长度
	        if(formData.password.length < 6){
	            result.msg = '密码长度不能少于6位';
	            return result;
	        }
	        // 验证两次输入的密码是否一致
	        if(formData.password !== formData.passwordConfirm){
	            result.msg = '两次输入的密码不一致';
	            return result;
	        }
	        // 验证手机号
	        if(!_xl.validate(formData.phone, 'phone')){
	            result.msg = '手机号格式不正确';
	            return result;
	        }
	        // 验证邮箱格式
	        if(!_xl.validate(formData.email, 'email')){
	            result.msg = '邮箱格式不正确';
	            return result;
	        }
	        // 验证密码提示问题是否为空
	        if(!_xl.validate(formData.question, 'require')){
	            result.msg = '密码提示问题不能为空';
	            return result;
	        }
	        // 验证密码提示问题答案是否为空
	        if(!_xl.validate(formData.answer, 'require')){
	            result.msg = '密码提示问题答案不能为空';
	            return result;
	        }
	        // 通过验证，返回正确提示
	        result.status   = true;
	        result.msg      = '验证通过';
	        return result;
	    }
	};
	$(function(){
	    page.init();
	});

/***/ }),

/***/ 183:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ })

});