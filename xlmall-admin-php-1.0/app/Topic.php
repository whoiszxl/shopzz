<?php

namespace App;

class Topic extends Model
{
    protected $table = "topics";

    /*
     * 属于这个主题的所有文章
     */
    public function article()
    {
        return $this->belongsToMany(\App\Article::class, 'article_topics', 'topic_id', 'article_id');
    }

    //属于这个专题的文章数
    public function articleTopics(){
        return $this->hasMany(\App\ArticleTopic::class, 'topic_id', 'id');
    }
}
