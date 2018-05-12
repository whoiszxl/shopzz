/*
 * @Author: whoiszxl 
 * @Date: 2018-05-12 13:54:10 
 * @Last Modified by:   whoiszxl 
 * @Last Modified time: 2018-05-12 13:54:10 
 */

'use strict';
require('./index.css');
require('page/common/nav-simple/index.js');
var _xl = require('util/xl.js');

$(function(){
    var type        = _xl.getUrlParam('type') || 'default',
        $element    = $('.' + type + '-success');
    // 显示对应的提示元素
    $element.show();
})