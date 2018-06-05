<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use \App\Article;
use \App\Comment;
use \App\Zan;

class ArticleController extends Controller
{
    //列表
    public function index(){
        \Log::info("my_index", ['data'=>'xixi']);

        $article = Article::where('status','1')->orderBy('created_at', 'desc')->withCount(['comments','zans'])->paginate(10);

        return view("article/index", compact('article'));
    }

    //详情页面
    public function show(Article $article){
        $article->load('comments');
        return view("article/show", compact('article'));
    }

    //创建页面
    public function create(){

        return view("article/create");
    }

    //创建逻辑
    public function store(){
        // $article = new Article();
        // $article->title = request('title');
        // $article->content = request('content');
        // $article->save();

        //验证数据
        $this->validate(request(), [
            'title' => 'required|string|max:100|min:5',
            'content' => 'required|string|min:10',
        ]);

        //添加逻辑
        $user_id = \Auth::id();
        $params = array_merge(request(['title','content']), compact('user_id'));
        $result = Article::create($params);
        
        //视图渲染
        return redirect('/article');
    }

    //编辑页面
    public function edit(Article $article){

        return view("article/edit", compact('article'));
    }

    //编辑逻辑
    public function update(Article $article){
        //验证数据
        $this->validate(request(), [
            'title' => 'required|string|max:100|min:5',
            'content' => 'required|string|min:10',
        ]);
        //权限验证
        $this->authorize('update', $article);
        //逻辑
        $article->title = request('title');
        $article->content = request('content');
        $article->save();
        //渲染
        return redirect("/article/{$article->id}");
    }

    //删除逻辑
    public function delete(Article $article){
        $this->authorize('delete', $article);
        $article->delete();
        return redirect("/article");
    }

    //图片上传
    public function imageUpload(Request $request){
        $path = $request->file('wangEditorH5File')->storePublicly(md5(time()));
        return asset('storage/'.$path);
    }

    //提交评论
    public function comment(Article $article){
        $this->validate(request(), [
            'content' => 'required|min:5',
        ]);

        $comment = new Comment();
        $comment->user_id = \Auth::id();
        $comment->content = request('content');
        $article->comments()->save($comment);
        return back();
    }

    //點讚
    public function zan(Article $article){
        $param = [
            'user_id' => \Auth::id(),
            'article_id' => $article->id,
        ];
        //數據庫存在便查找，不存在則創建
        Zan::firstOrCreate($param);
        return back();
    }

    //取消讚
    public function unzan(Article $article){
        $article->zan(\Auth::id())->delete();
        return back();
    }

    //搜索结果页面
    public function search(){

        //验证
        $this->validate(request(), [
            'keyword' => 'required'
        ]);

        //逻辑
        $keyword = request('keyword');
        $article = Article::search($keyword)->paginate(6);

        return view('article/search', compact('article','keyword'));
    }
}
