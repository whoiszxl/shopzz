/*
 * @Author: whoiszxl 
 * @Date: 2018-05-11 23:15:43 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-05-25 18:08:06
 */

'use strict';
require('./index.css');
var _xl     = require('util/xl.js');
// 导航
var user_service = require("service/user-service.js");
var cart_service = require("service/cart-service.js");
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
            $(".notLogin").hide();
            $('.login').show().find('.username').text(res.username);
        }, function(errMsg) {
            // do nothing
        })
    },

    //加载购物车信息
    loadCartInfo: function() {
        cart_service.getCartCount(function(res) {
            $(".cart-count").text("(" + res.data + ")" || "(0)");
        }, function(errMsg) {
            $(".cart-count").text("(0)");
        })
    }

}

module.exports = nav.init();