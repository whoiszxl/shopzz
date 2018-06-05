<?php

namespace App;

use App\Model;
use Laravel\Scout\Searchable;
use Illuminate\Database\Eloquent\Builder;

class Category extends Model
{

    use Searchable;

    protected $table = 'xl_category';

    const CREATED_AT = 'create_time';
    const UPDATED_AT = 'update_time';

    //定义索引里的type
    public function searchableAs(){
        return "category";
    }

}
