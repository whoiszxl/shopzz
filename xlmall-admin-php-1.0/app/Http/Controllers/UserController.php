<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\User;

class UserController extends Controller
{
    


    public function setting()
    {
        $me = \Auth::user();
        return view('user/setting', compact('me'));
    }

    public function settingStore(Request $request, User $user)
    {
        $this->validate(request(),[
            'name' => 'min:3',
        ]);

        $name = request('name');
        if ($name != $user->name) {
            if(\App\User::where('name', $name)->count() > 0) {
                return back()->withErrors(array('message' => '用户名称已经被注册'));
            }
            $user->name = request('name');
        }
        if ($request->file('avatar')) {
            $path = $request->file('avatar')->storePublicly(md5(\Auth::id() . time()));
            \Log::info('上傳圖片成功:'.$path);
            $user->avatar = "/storage/". $path;
        }

        $user->save();
        return back();
    }

    //个人中心页面
    public function show(User $user){

        //當前用戶的信息， 關注、粉絲，文章數
        $user = User::withCount(['stars', 'fans', 'articles'])->find($user->id);

        //文章列表，前10條
        $article = $user->articles()->orderBy('created_at', 'desc')->take(10)->get();

        //這個人關注的用戶
        $stars = $user->stars;//通過對象關聯獲取到關注的人，通過fan_id=current_id獲取到關注的用戶
        //通過User查詢id在關注的用戶id之內的用戶，并計數
        $susers = User::whereIn('id', $stars->pluck('star_id'))->withCount(['stars', 'fans', 'articles'])->get();

        //關注這個人的用戶
        $fans = $user->fans;
        $fusers = User::whereIn('id', $fans->pluck('fan_id'))->withCount(['stars', 'fans', 'articles'])->get();

        return view('user/show', compact('user', 'article', 'susers', 'fusers'));
    }

    //关注用户
    public function fan(User $user){
        $current_user = \Auth::user();
        $current_user->doFan($user->id);

        return [
            'error' => 0,
            'msg' => ''
        ];
    }

    //取消用户
    public function unfan(User $user){
        $current_user = \Auth::user();
        $current_user->doUnFan($user->id);

        return [
            'error' => 0,
            'msg' => ''
        ];
    }

}
