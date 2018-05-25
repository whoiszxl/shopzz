/*
 * @Author: whoiszxl 
 * @Date: 2018-05-25 18:23:02 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-05-25 18:24:01
 */

'use strict';

require("./index.css");
var navSide = require("../common/nav-side/index.js")
var page = {
    init: function() {
        this.onload();
    },
    onload: function() {
        navSide.init({
            name: "about"
        });
        this.loadOrderList();
    }
}
$(function() {
    page.init();
})