<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/
use App\Http\Controllers;

Route::get('/', "LoginController@index");

Route::group(['middleware' => 'auth:web'], function(){

    /********************文章路由 **********************/
    //文章列表页
    Route::get('/article', 'ArticleController@index');

    //创建文章
    Route::get('/article/create', 'ArticleController@create');
    Route::post('/article', 'ArticleController@store');

    //搜索
    Route::get('/article/search', 'ArticleController@search');

    //文章详情页
    Route::get('/article/{article}', 'ArticleController@show');

    //编辑文章
    Route::get('/article/edit/{article}', 'ArticleController@edit');
    Route::put('/article/{article}', 'ArticleController@update');

    //删除文章
    Route::get('/article/delete/{article}', 'ArticleController@delete');
    
    //图片上传
    Route::post('/article/image/upload', 'ArticleController@imageUpload');

    //提交评论
    Route::post('/article/comment/{article}', 'ArticleController@comment');

    //点赞
    Route::get('/article/zan/{article}', 'ArticleController@zan');

    //取消赞
    Route::get('/article/unzan/{article}', 'ArticleController@unzan');

    //个人中心
    Route::get('/user/{user}', 'UserController@show');
    Route::post('/user/fan/{user}', 'UserController@fan');
    Route::post('/user/unfan/{user}', 'UserController@unfan');

    //专题详情页面
    Route::get('/topic/{topic}', 'TopicController@show');
    //投稿
    Route::get('/topic/submit/{topic}', 'TopicController@submit');

    //通知
    Route::get('/notices', 'NoticeController@index');


    //个人设置页面
    Route::get('/user/setting/{user}', 'UserController@setting');
    //个人设置逻辑
    Route::post('/user/setting/{user}', 'UserController@settingStore');

});







/*******************用户模块 **********************/
//用户注册页面
Route::get('/register', 'RegisterController@index');
//用户注册逻辑
Route::post('/register', 'RegisterController@register');

//用户登录页面
Route::get('/login', 'LoginController@index')->name('login');
//用户登录逻辑
Route::post('/login', 'LoginController@login');
//用户登出逻辑
Route::get('/logout', 'LoginController@logout');


include_once('admin.php');

