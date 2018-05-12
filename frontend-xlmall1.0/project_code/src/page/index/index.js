/*
 * @Author: whoiszxl 
 * @Date: 2018-05-11 10:32:21 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-05-12 12:25:58
 */

'use strict';
require('./index.css');
require('page/common/nav/index.js');
require('page/common/header/index.js');

var navSide = require('page/common/nav-side/index.js');
var _xl = require('util/xl.js');

navSide.init({
    name : 'order-list'
});
