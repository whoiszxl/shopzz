<?php

namespace App;

use App\Model;
use Laravel\Scout\Searchable;
use Illuminate\Database\Eloquent\Builder;

class Category extends Model
{

    use Searchable;

    protected $table = 'xl_category';

    //定义索引里的type
    public function searchableAs(){
        return "category";
    }

}
