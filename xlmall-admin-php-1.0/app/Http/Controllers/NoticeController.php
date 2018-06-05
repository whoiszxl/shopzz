<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class NoticeController extends Controller
{
    /*
     * 消息页面
     */
    public function index()
    {
        // 获取我收到的消息
        $user = \Auth::user();
        $notices = $user->notices;
        return view("notice/index", compact('notices'));
    }
}
