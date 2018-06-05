<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\User;

class RegisterController extends Controller
{
    
    //用户注册页面
    public function index(){
        return view('register.index');
    }

    //用户注册逻辑
    public function register(){

        //验证
        $this->validate(request(), [
            'name' => 'required|min:3|max:30|unique:users,name',
            'email' => 'required|unique:users,email|email',
            'password' => 'required|min:5|max:16|confirmed'
        ]);

        //逻辑
        $name = request('name');
        $email = request('email');
        $password = bcrypt(request('password'));

        $user = User::create(compact('name', 'email', 'password'));
        
        //渲染
        return redirect('/login');
    }

}
