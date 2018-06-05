<?php

namespace App;

use App\Model;

class Comment extends Model
{
    //评论所属文章
    public function article(){
        
        return $this->belongsTo('App\Article', 'article_id', 'id');
    }

    //评论所属用户
    public function user(){
        
        return $this->belongsTo('App\User', 'user_id', 'id');
    }
}
