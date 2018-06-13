/*
 * @Author: whoiszxl 
 * @Date: 2018-05-12 15:32:04 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-13 14:46:27
 */

'use strict';
var _xl = require('util/xl.js');

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