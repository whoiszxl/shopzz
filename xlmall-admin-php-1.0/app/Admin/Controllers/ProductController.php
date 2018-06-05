<?php

namespace App\Admin\Controllers;

use \App\Product;

class ProductController extends Controller{

    //文章审核后台
    public function index(){

        $products = Product::withoutGlobalScope('avaiable')->where('status', 1)->orderBy('create_time', 'desc')->paginate(10);
        $img_prefix_url = config('myconf.QINIU_URL');
        return view('admin.product.index', compact('products','img_prefix_url'));
    }


    //详情页面
    public function show(Article $article){
        $article->load('comments');
        return view("article/show", compact('article'));
    }

    //创建页面
    public function create(){

        return view("article/create");
    }

    //创建逻辑
    public function store(){
        // $article = new Article();
        // $article->title = request('title');
        // $article->content = request('content');
        // $article->save();

        //验证数据
        $this->validate(request(), [
            'title' => 'required|string|max:100|min:5',
            'content' => 'required|string|min:10',
        ]);

        //添加逻辑
        $user_id = \Auth::id();
        $params = array_merge(request(['title','content']), compact('user_id'));
        $result = Article::create($params);
        
        //视图渲染
        return redirect('/article');
    }

    //编辑页面
    public function edit(Product $product){
        return view("admin.product.edit", compact('product'));
    }

    //编辑逻辑
    public function update(Article $article){
        //验证数据
        $this->validate(request(), [
            'title' => 'required|string|max:100|min:5',
            'content' => 'required|string|min:10',
        ]);
        //权限验证
        $this->authorize('update', $article);
        //逻辑
        $article->title = request('title');
        $article->content = request('content');
        $article->save();
        //渲染
        return redirect("/article/{$article->id}");
    }

    //删除逻辑
    public function delete(Article $article){
        $this->authorize('delete', $article);
        $article->delete();
        return redirect("/article");
    }

    //图片上传
    public function imageUpload(Request $request){
        $path = $request->file('wangEditorH5File')->storePublicly(md5(time()));
        return asset('storage/'.$path);
    }


    public function status(Article $article){

        $this->validate(request(), [
            'status' => 'required|in:-1,1',
        ]);

        $article->status = request('status');
        $article->save();

        return [
            'error' => 0,
            'msg' => ''
        ];
    }

}