<?php

namespace App\Admin\Controllers;

use \App\Article;

class ArticleController extends Controller{

    //文章审核后台
    public function index(){

        $articles = Article::withoutGlobalScope('avaiable')->where('status', 0)->orderBy('created_at', 'desc')->paginate(10);
        return view('admin.article.index', compact('articles'));
    }

    public function status(Article $article){

        $this->validate(request(), [
            'status' => 'required|in:-1,1',
        ]);

        $article->status = request('status');
        $article->save();

        return [
            'error' => 0,
            'msg' => ''
        ];
    }

}