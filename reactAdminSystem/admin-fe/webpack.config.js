/*
 * @Author: whoiszxl 
 * @Date: 2018-06-08 12:26:50 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-09 14:59:38
 */

const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

let WEBPACK_ENV = process.env.WEBPACK_ENV || 'dev';
console.log(WEBPACK_ENV);
module.exports = {
    entry: './src/app.jsx', //配置入口
    output: {
        path: path.resolve(__dirname, 'dist'), //编译输出的路径
        publicPath: WEBPACK_ENV === 'dev' 
        ? '/dist/' : '//s.jianliwu.com/admin-v2-fe/dist/', //配置资源文件访问的公共路径
        filename: 'js/app.js' //输出的入口js
    },
    resolve: {
        alias: {
            page        : path.resolve(__dirname, 'src/page'),
            component   : path.resolve(__dirname, 'src/component'),
            util        : path.resolve(__dirname, 'src/util'),
            service     : path.resolve(__dirname, 'src/service')
        }
    },
    module: {
        rules: [
            //react语法处理
            {
                test: /\.jsx$/,
                exclude: /(node_modules)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['env', 'react']
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
                    use: ['css-loader', 'sass-loader']
                })
            },
            //图片处理
            {
                test: /\.(png|jpg|gif)$/,
                use: [
                  {
                    loader: 'url-loader',
                    options: {
                      limit: 8192,
                      name: 'resource/[name].[ext]' //配置图片打包后的路径和文件名
                    }
                  }
                ]
            },
            //字体图标处理
            {
                test: /\.(eot|svg|ttf|woff|woff2|otf)$/,
                use: [
                    {
                        loader: 'url-loader',
                        options: {
                            limit: 8192,
                            name: 'resource/[name].[ext]'
                        }
                    }
                ]
            }
        ]
    },

    plugins: [
        //处理html
        new HtmlWebpackPlugin({
            template: './src/index.html'
        }),
        //将css独立出来
        new ExtractTextPlugin("css/[name].css"),
        //提出公共模块
        new webpack.optimize.CommonsChunkPlugin({
            name : 'common',
            filename : 'js/base.js'
        })
    ],
    
    devServer: {
        port: 8086, //配置端口
        historyApiFallback: {
            index: '/dist/index.html'
        },
        proxy : {
            '/manage' : {
                target: 'http://118.126.92.128:10101',
                changeOrigin : true
            },
            '/user/logout.do' : {
                target: 'http://118.126.92.128:10101',
                changeOrigin : true
            }
        }
    }
};