/*
 * @Author: Administrator
 * @Date:   2018-03-24 15:27:37
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-07 15:34:09
 */

const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require("extract-text-webpack-plugin");
const webpack = require('webpack');

module.exports = {
    entry: './src/app.jsx',
    output: {
        path: path.resolve(__dirname, 'dist'),
        //resolve用来解析一个路径，也就是通过webpack打包的文件最后要放的位置。
        //__dirname指的是项目根目录，也就是webpack.config.js所在目录
        //第一个参数是致当前目录，第二个'dist'相当于当前目录下的dist文件夹
        publicPath: '/dist/', //引用资源的路径，比如js的引用目录变成/dist/js/xxx.js
        filename: 'js/app.js'
    },
    resolve: {
        alias: {
            page: path.resolve(__dirname, 'src/page'),
            component: path.resolve(__dirname, 'src/component'),
            util: path.resolve(__dirname, 'src/util'),
            service: path.resolve(__dirname, 'src/service')
        }
    },
    module: {
        rules: [{
            test: /\.jsx$/,
            exclude: /(node_modules)/,
            use: {
                loader: 'babel-loader',
                options: {
                    presets: ['env', 'react'] //表示自动根据环境来打包，如浏览器环境或node环境
                }
            }
        },
            //css文件处理
            {
                test: /\.css$/,
                use: ExtractTextPlugin.extract({
                    fallback: "style-loader",
                    use: "css-loader"
                })
            },
            //sass文件处理
            {
                test: /\.scss$/,
                use: ExtractTextPlugin.extract({
                    fallback: 'style-loader',
                    //如果需要，可以在 sass-loader 之前将 resolve-url-loader 链接进来
                    use: ['css-loader', 'sass-loader']
                })
            },
            //图片的处理
            {
                test: /\.(png|jpg|gif)$/,
                use: [{
                    loader: 'url-loader',
                    options: {
                        limit: 8192,
                        name: 'resource/[name].[ext]' //name字段用于指定路径
                    }
                }]
            },
            //加载字体图标
            {
                test: /\.(woff|woff2|eot|ttf|otf|svg)$/,
                use: [{
                    loader: 'url-loader',
                    options: {
                        limit: 8192,
                        name: 'resource/[name].[ext]'
                    }
                }]
            }
        ]
    },
    plugins: [
        //处理html文件
        new HtmlWebpackPlugin({
            template: './src/index.html',
            favicon: './favicon.ico'
        }),
        //独立css文件
        new ExtractTextPlugin("css/[name].css"),
        //提取公共模块，webpack自带的api
        new webpack.optimize.CommonsChunkPlugin({
            name: 'common',
            filename: 'js/base.js'
        })
    ],
    devServer: {
        // contentBase: './dist',
        port: 8085,
        historyApiFallback: {
            index: '/dist/index.html'
        },
        proxy: {
            '/manage': {
                target: 'http://localhost:8888',
                changeOrigin: true //加了这个字段，请求就是从上面这个域名发出的，而不是从localhost发出
            },
            '/user/logout.do': {
                target: 'http://localhost:8888',
                changeOrigin: true
            }
        }
    }
};