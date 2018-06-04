webpackJsonp([14],{

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(176);


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

/***/ 176:
/***/ (function(module, exports, __webpack_require__) {

	/*
	 * @Author: whoiszxl 
	 * @Date: 2018-05-12 16:34:53 
	 * @Last Modified by:   whoiszxl 
	 * @Last Modified time: 2018-05-12 16:34:53 
	 */

	'use strict';
	__webpack_require__(177);
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
	    data : {
	        username    : '',
	        question    : '',
	        answer      : '',
	        token       : ''
	    },
	    init: function(){
	        this.onLoad();
	        this.bindEvent();
	    },
	    onLoad : function(){
	        this.loadStepUsername();
	    },
	    bindEvent : function(){
	        var _this = this;
	        // 输入用户名后下一步按钮的点击
	        $('#submit-username').click(function(){
	            var username = $.trim($('#username').val());
	            // 用户名存在
	            if(username){
	                _user.getQuestion(username, function(res){
	                    _this.data.username = username;
	                    _this.data.question = res;
	                    _this.loadStepQuestion();
	                }, function(errMsg){
	                    formError.show(errMsg);
	                });
	            }
	            // 用户名不存在
	            else{
	                formError.show('请输入用户名');
	            }
	        });
	        // 输入密码提示问题答案中的按钮点击
	        $('#submit-question').click(function(){
	            var answer = $.trim($('#answer').val());
	            // 密码提示问题答案存在
	            if(answer){
	                // 检查密码提示问题答案
	                _user.checkAnswer({
	                    username : _this.data.username,
	                    question : _this.data.question,
	                    answer   : answer
	                }, function(res){
	                    _this.data.answer   = answer;
	                    _this.data.token    = res;
	                    _this.loadStepPassword();
	                }, function(errMsg){
	                    formError.show(errMsg);
	                });
	            }
	            // 用户名不存在
	            else{
	                formError.show('请输入密码提示问题答案');
	            }
	        });
	        // 输入新密码后的按钮点击
	        $('#submit-password').click(function(){
	            var password = $.trim($('#password').val());
	            // 密码不为空
	            if(password && password.length >= 6){
	                // 检查密码提示问题答案
	                _user.resetPassword({
	                    username        : _this.data.username,
	                    passwordNew     : password,
	                    forgetToken     : _this.data.token
	                }, function(res){
	                    window.location.href = './result.html?type=pass-reset';
	                }, function(errMsg){
	                    formError.show(errMsg);
	                });
	            }
	            // 密码为空
	            else{
	                formError.show('请输入不少于6位的新密码');
	            }
	        });
	        
	    },
	    // 加载输入用户名的一步
	    loadStepUsername : function(){
	        $('.step-username').show();
	    },
	    // 加载输入密码提示问题答案的一步
	    loadStepQuestion : function(){
	        // 清除错误提示
	        formError.hide();
	        // 做容器的切换
	        $('.step-username').hide()
	            .siblings('.step-question').show()
	            .find('.question').text(this.data.question);
	    },
	    // 加载输入password的一步
	    loadStepPassword : function(){
	        // 清除错误提示
	        formError.hide();
	        // 做容器的切换
	        $('.step-question').hide()
	            .siblings('.step-password').show();
	    }
	    
	};
	$(function(){
	    page.init();
	});

/***/ }),

/***/ 177:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ })

});