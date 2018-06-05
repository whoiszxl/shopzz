<?php

namespace App;

use App\Model;
use Laravel\Scout\Searchable;
use Illuminate\Database\Eloquent\Builder;

class Article extends Model
{

    use Searchable;

    //定义索引里的type
    public function searchableAs(){
        return "article";
    }

    //定义有哪些字段需要搜索
    public function toSearchableArray(){
        return [
            'title' => $this->title,
            'content' => $this->content,
        ];
    }

    //关联用户
    public function user(){
        
        return $this->belongsTo('App\User', 'user_id', 'id');
    }

    //关联评论
    public function comments(){
        
        return $this->hasMany('\App\Comment', 'article_id', 'id')->orderBy('created_at', 'desc');
    }

    //關聯讚
    public function zan($user_id){
        
        return $this->hasOne(\App\Zan::class)->where('user_id', $user_id);
    }

    //文章的所有讚
    public function zans(){
        return $this->hasMany(\App\Zan::class);
    }

    //属于某个作者的文章
    public function scopeAuthorBy(Builder $query, $user_id){
        return $query->where('user_id', $user_id);
    }

    public function articleTopics(){
        return $this->hasMany(\App\ArticleTopic::class, 'article_id', 'id');
    }

    //不属于某个专题的文章
    public function scopeTopicNotBy(Builder $query, $topic_id){
        return $query->doesntHave('articleTopics', 'and', function($q) use ($topic_id){
            $q->where('topic_id', $topic_id);
        });
    }

    //全局scope
    protected static function boot(){

        parent::boot();

        static::addGlobalScope("avaiable", function(Builder $builder){
            $builder->whereIn('status', [0,1]);
        });
    }
}
