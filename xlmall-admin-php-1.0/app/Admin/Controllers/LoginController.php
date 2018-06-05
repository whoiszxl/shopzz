<?php

namespace App\Admin\Controllers;

class LoginController extends Controller{

    //admin登录页
    public function index(){
        
        return view('admin.login.index');
    }

    //admin登录逻辑
    public function login(){
        //验证
        $this->validate(request(), [
            'name' => 'required|min:3|max:16',
            'password' => 'required|min:5|max:16',
        ]);

        //逻辑
        $user = request(['name','password']);
        if(\Auth::guard("admin")->attempt($user)){
            return redirect('/admin/home');
        }

        //渲染
        return \Redirect::back()->withErrors("  ");
    }

    //admin登出逻辑
    public function logout(){
        \Auth::guard("admin")->logout();
        return redirect("/admin/login");
    }

}