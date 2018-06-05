<?php

namespace App\Admin\Controllers;

class HomeController extends Controller{

    //home admin后台主页
    public function index(){
        
        return view('admin.home.index');
    }
}