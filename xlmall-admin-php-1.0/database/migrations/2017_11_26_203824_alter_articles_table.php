<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AlterArticlesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table("articles",function(Blueprint $table){
            $table->tinyInteger('status')->default(0);// 0待审核 1通过  2不通过
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table("articles",function(Blueprint $table){
            $table->dropColumn('status');// 0待审核 1通过  2不通过
        });
    }
}
