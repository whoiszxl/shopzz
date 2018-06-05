<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Topic;

class TopicController extends Controller
{
    //
    public function show(Topic $topic){
        //带文章数的专题
        $topic = Topic::withCount('articleTopics')->find($topic->id);
        //专题文章列表，倒叙，前10个
        $articles = $topic->article()->orderBy('created_at', 'desc')->take(10)->get();
        //属于我的文章但是未投稿
        $myarticles = \App\Article::authorBy(\Auth::id())->topicNotBy($topic->id)->get();

        return view('topic/show', compact('topic','articles','myarticles'));
    }


    //投稿
    public function submit(Topic $topic){
        $this->validate(request(),[
            'article_ids' => 'required|array'
        ]);

        $article_ids = request('article_ids');
        $topic_id = $topic->id;

        foreach($article_ids as $article_id){
            \App\ArticleTopic::firstOrCreate(compact('topic_id','article_id'));
        }

        return back();
    }
}
