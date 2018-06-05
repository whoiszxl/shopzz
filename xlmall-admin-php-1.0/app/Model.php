<?php

namespace App;

use Illuminate\Database\Eloquent\Model as BaseModel;

class Model extends BaseModel
{
    protected $guarded = [];//不可注入字段
    //protected $fillable= ['title','content'];//可以注入字段
}
