<?php

namespace App;

use App\Model;
use Laravel\Scout\Searchable;
use Illuminate\Database\Eloquent\Builder;

class Product extends Model
{

    use Searchable;

    protected $table = 'xl_product';

    //定义索引里的type
    public function searchableAs(){
        return "product";
    }

    //定义有哪些字段需要搜索
    public function toSearchableArray(){
        return [
            'name' => $this->name,
            'subtitle' => $this->subtitle,
        ];
    }

    //全局scope
    protected static function boot(){

        parent::boot();

        static::addGlobalScope("avaiable", function(Builder $builder){
            $builder->whereIn('status', [0,1]);
        });
    }
}
