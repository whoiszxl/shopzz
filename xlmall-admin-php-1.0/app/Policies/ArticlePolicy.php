<?php

namespace App\Policies;

use App\User;
use App\Article;
use Illuminate\Auth\Access\HandlesAuthorization;

class ArticlePolicy
{
    use HandlesAuthorization;

    /**
     * Create a new policy instance.
     *
     * @return void
     */
    public function __construct()
    {
        //
    }

    //修改权限
    public function update (User $user,Article $article){

        return $user->id == $article->user_id;
    }

    //删除权限
    public function delete (User $user,Article $article){
        
        return $user->id == $article->user_id;
    }
}
