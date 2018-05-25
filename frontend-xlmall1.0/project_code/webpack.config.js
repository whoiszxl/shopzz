/*
 * @Author: whoiszxl 
 * @Date: 2018-05-11 10:32:51 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-05-25 23:45:17
 */
var webpack = require('webpack');
var Ex = require('extract-text-webpack-plugin');
var HtmlWebpackPlugin = require('html-webpack-plugin');

// 环境变量配置，dev / online
var WEBPACK_ENV         = process.env.WEBPACK_ENV || 'dev';

// 获取html-webpack-plugin参数的方法 
var getHtmlConfig = function(name, title){
    return {
        template    : './src/view/' + name + '.html',
        filename    : 'view/' + name + '.html',
        title       : title,
        favicon: "./favicon.jpg",
        inject      : true,
        hash        : true,
        chunks      : ['common', name]
    };
};


// webpack config
var config = {

    devServer: {
        host: '0.0.0.0',
        disableHostCheck: true
    },
    entry: {
        'common': ['./src/page/common/index.js'], 
        'index' : ['./src/page/index/index.js'],
        'list'  : ['./src/page/list/index.js'],
        'detail': ['./src/page/detail/index.js'],
        'cart'  : ['./src/page/cart/index.js'],
        "order-confirm": ["./src/./page/order-confirm/order-confirm.js"],
        "order-list": ["./src/./page/order-list/order-list.js"],
        "order-detail": ["./src/./page/order-detail/order-detail.js"],
        "pay": ["./src/./page/pay/pay.js"],
        'user-login' : ['./src/page/user-login/index.js'],
        'user-register' : ['./src/page/user-register/index.js'],
        'user-pass-reset'   : ['./src/page/user-pass-reset/index.js'],
        'user-pass-update'  : ['./src/page/user-pass-update/index.js'],
        'user-center'       : ['./src/page/user-center/index.js'],
        'user-center-update': ['./src/page/user-center-update/index.js'],
        'result': ['./src/page/result/index.js'],
        "about": ["./src/page/about/index.js"],
    },
    output: {
        path: './dist',
        publicPath : '/dist',
        filename: 'js/[name].js'
    },
    externals:{
        '$'         :'window.jQuery',
        'jquery'    :'window.jQuery'
    },
    module: {
        loaders: [
            { test: /\.css$/, loader: Ex.extract("style-loader","css-loader") },
            { test: /\.(gif|png|jpg|woff|svg|eot|ttf)\??.*$/, loader: 'url-loader?limit=100&name=resource/[name].[ext]' },
            { 
                test: /\.string$/, 
                loader: 'html-loader',
                query: {
                minimize: true,
                removerAttributeQuotes: false
                }
            }
        ]
    },
    resolve : {
        alias : {
            node_modules    : __dirname + '/node_modules',
            util            : __dirname + '/src/util',
            page            : __dirname + '/src/page',
            service         : __dirname + '/src/service',
            image           : __dirname + '/src/image'
        }
    },
    plugins: [
        // 独立通用模块到js/base.js
        new webpack.optimize.CommonsChunkPlugin({
            name : 'common',
            filename : 'js/base.js'
        }),
        // 把css单独打包到文件里
        new Ex("css/[name].css"),
        // html模板的处理
        new HtmlWebpackPlugin(getHtmlConfig('index', '首页')),
        new HtmlWebpackPlugin(getHtmlConfig('list', '商品列表页')),
        new HtmlWebpackPlugin(getHtmlConfig('detail', '商品详情页')),
        new HtmlWebpackPlugin(getHtmlConfig('cart', '购物车')),
        new htmlWebpackPlugin(getHtmlConfig("order-confirm", "订单确认")),
        new htmlWebpackPlugin(getHtmlConfig("order-list", "订单列表")),
        new htmlWebpackPlugin(getHtmlConfig("order-detail", "订单详情")),
        new htmlWebpackPlugin(getHtmlConfig("pay", "支付")),
        new HtmlWebpackPlugin(getHtmlConfig('user-login', '用户登录')),
        new HtmlWebpackPlugin(getHtmlConfig('user-register', '用户注册')),
        new HtmlWebpackPlugin(getHtmlConfig('user-pass-reset', '找回密码')),
        new HtmlWebpackPlugin(getHtmlConfig('user-center', '个人中心')),
        new HtmlWebpackPlugin(getHtmlConfig('user-center-update', '修改个人信息')),
        new HtmlWebpackPlugin(getHtmlConfig('user-pass-update', '修改密码')),
        new HtmlWebpackPlugin(getHtmlConfig('result', '操作结果')),
        new HtmlWebpackPlugin(getHtmlConfig('about', '关于XMMALL')),

    ]
};

if('dev' === WEBPACK_ENV){
    config.entry.common.push('webpack-dev-server/client?http://localhost:8088/');
}
module.exports = config;