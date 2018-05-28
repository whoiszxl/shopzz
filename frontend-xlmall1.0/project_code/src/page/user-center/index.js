/*
 * @Author: whoiszxl 
 * @Date: 2018-05-12 17:07:37 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-05-26 18:02:08
 */

'use strict';
require('./index.css');
require('page/common/nav/index.js');
require('page/common/header/index.js');
var navSide         = require('page/common/nav-side/index.js');
var _xl             = require('util/xl.js');
var _user           = require('service/user-service.js');
var templateIndex   = require('./index.string');

// page 逻辑部分
var page = {
    init: function(){
        this.onLoad();
    },
    onLoad : function(){
        // 初始化左侧菜单
        navSide.init({
            name: 'user-center'
        });
        // 加载用户信息
        this.loadUserInfo();
    },
    // 加载用户信息
    loadUserInfo : function(){
        var userHtml = '';
        _user.getUserInfo(function(res){
            userHtml = _xl.renderHtml(templateIndex, res);
            $('.panel-body').html(userHtml);
        }, function(errMsg){
            _xl.errorTips(errMsg);
            _xl.doLogin();
        });
    }
};
$(function(){
    page.init();
});