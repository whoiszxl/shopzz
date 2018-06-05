<?php

namespace App\Admin\Controllers;

use \App\Product;
use \App\Category;

class ProductController extends Controller{

    //文章审核后台
    public function index(){

        $products = Product::withoutGlobalScope('avaiable')->where('status', 1)->orderBy('create_time', 'desc')->paginate(10);
        $img_prefix_url = config('myconf.QINIU_URL');
        return view('admin.product.index', compact('products','img_prefix_url'));
    }


    //详情页面
    public function show(Product $product){
        $product->load('comments');
        return view("article/show", compact('article'));
    }

    //创建页面
    public function create(){

        return view("article/create");
    }

    //创建逻辑
    public function store(){
        // $product = new Product();
        // $product->title = request('title');
        // $product->content = request('content');
        // $product->save();

        //验证数据
        $this->validate(request(), [
            'title' => 'required|string|max:100|min:5',
            'content' => 'required|string|min:10',
        ]);

        //添加逻辑
        $user_id = \Auth::id();
        $params = array_merge(request(['title','content']), compact('user_id'));
        $result = Product::create($params);
        
        //视图渲染
        return redirect('/article');
    }

    //编辑页面
    public function edit(Product $product){
        $categorys = Category::where('parent_id','!=',0)->get();
        
        return view("admin.product.edit", compact('product','categorys'));
    }

    //编辑逻辑
    public function update(Product $product){
        //验证数据
        $this->validate(request(), [
            'name' => 'required|string|max:200|min:5'
        ]);
        //权限验证
        $this->authorize('update', $product);
        //逻辑
        $product->name = request('name');
        $product->subtitle = request('subtitle');
        $product->main_image = request('main_image');
        $product->sub_images = request('sub_images');
        $product->detail = request('detail');
        $product->price = request('price');
        $product->stock = request('stock');
        $product->save();
        //渲染
        return redirect("/product/{$product->id}");
    }

    //删除逻辑
    public function delete(Product $product){
        $this->authorize('delete', $product);
        $product->delete();
        return redirect("/article");
    }

    //图片上传
    public function imageUpload(Request $request){
        $path = $request->file('wangEditorH5File')->storePublicly(md5(time()));
        return asset('storage/'.$path);
    }


    public function status(Product $product){

        $this->validate(request(), [
            'status' => 'required|in:-1,1',
        ]);

        $product->status = request('status');
        $product->save();

        return [
            'error' => 0,
            'msg' => ''
        ];
    }

}