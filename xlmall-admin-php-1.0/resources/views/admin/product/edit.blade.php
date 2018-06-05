@extends("admin.layout.main") @section("content")
<link rel="stylesheet" type="text/css" href="/css/wangEditor.min.css">
<!-- Main content -->
<section class="content">
    <!-- Small boxes (Stat box) -->
    <div class="row">
        <div class="col-lg-10 col-xs-6">
            <!-- /.box-header -->
            <div class="box-body">
                <div class="box box-warning">
                    <div class="box-header with-border">
                        <h3 class="box-title">修改商品</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <form action="/admin/product/{{$product->id}}" method="POST">
                            {{csrf_field()}}
                            <!-- text input -->
                            <div class="form-group">
                                <label>商品名称</label>
                                <input type="text" name="name" class="form-control" placeholder="Enter ..." value="{{$product->name}}">
                            </div>
                            <div class="form-group">
                                <label>商品描述</label>
                                <input type="text" name="subtitle" class="form-control" placeholder="Enter ..." value="{{$product->subtitle}}">
                            </div>
                            

                            <!-- select -->
                            <div class="form-group">
                                <label>所属分类</label>
                                <select name="category_id" class="form-control">
                                    @foreach($categorys as $category)
                                        <option value="{{$category->id}}">{{$category->name}}</option>
                                    @endforeach
                                </select>
                            </div>

                            <div class="form-group">
                                <label>商品价格</label>
                                <input type="text" name="price" class="form-control" placeholder="Enter ..." value="{{$product->price}}">
                            </div>

                            <div class="form-group">
                                <label>商品库存</label>
                                <input type="text" name="stock" class="form-control" placeholder="Enter ..." value="{{$product->stock}}">
                            </div>
                            
                            <div class="form-group">
                                <textarea id="content" name="detail" class="form-control" style="height:400px;max-height:500px;" placeholder="这里是内容">
                                    {{$product->detail}}
                                </textarea>
                            </div>
                            
                            @include("layout.error")

                            <button type="submit" class="btn btn-default">提交</button>

                        </form>
                    </div>
                    <!-- /.box-body -->
                </div>

            </div>
        </div>
    </div>
</section>	
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/wangEditor.min.js"></script>
<script src="/js/ylaravel.js"></script>
@endsection