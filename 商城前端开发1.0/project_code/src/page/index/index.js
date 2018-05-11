/*
 * @Author: whoiszxl 
 * @Date: 2018-05-11 10:32:21 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-05-11 22:30:12
 */

'use strict';
var _xl = require('util/xl.js');
_xl.request({
  url: 'http://xlmall.whoiszxl.com/product/detail?productId=26',
  success: function(res) {
    console.log(res);
  },
  error: function(res) {
    console.log(res);
  }
});
