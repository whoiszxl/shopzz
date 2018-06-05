<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class LoginController extends Controller
{

    //用户登录页面
    public function index(){
        return view("login.index");
    }

    //用户登录逻辑
    public function login(){
        //验证
        $this->validate(request(), [
            'email' => 'required|email',
            'password' => 'required|min:5|max:16',
            'is_remember' => 'integer'
        ]);

        //逻辑
        $user = request(['email','password']);
        $is_remember = boolval(request('is_remember'));
        if(\Auth::attempt($user,$is_remember)){
            return redirect('/article');
        }

        //渲染
        return \Redirect::back()->withErrors("邮箱密码不匹配");
    }

    //用户登出逻辑
    public function logout(){
        \Auth::logout();
        return redirect('/login');
    }
}
