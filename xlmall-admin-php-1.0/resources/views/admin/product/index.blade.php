@extends("admin.layout.main")

@section("content")
    <!-- Main content -->
    <section class="content">
        <!-- Small boxes (Stat box) -->
        <div class="row">
            <div class="col-lg-10 col-xs-6">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">商品列表</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <table class="table table-bordered">
                            <tbody><tr>
                                <th>商品ID</th>
                                <th style="width: 300px">商品名称</th>
                                <th>商品图片</th>
                                <th>价格</th>
                                <th>销售状态</th>
                                <th>操作</th>
                            </tr>
                            @foreach($products as $product)
                            <tr>
                                <td>{{$product->id}}</td>
                                <td>{{$product->name}}</td>
                                <td><img style="width: 50px;height: 50px;" src="{{$img_prefix_url.$product->main_image}}" alt="" srcset=""></td>
                                <td>{{$product->price}}</td>
                                <td>{{$product->status == 1 ? "在售":"已下架"}}</td>
                                <td>
                                    <a href="/admin/product/edit/{{$product->id}}"><button type="button" class="btn bg-navy btn-flat margin" article-id="{{$product->id}}" article-action-status="1" >查看详情</button></a>
                                    <button type="button" class="btn bg-navy btn-flat margin" article-id="{{$product->id}}" article-action-status="-1" >编辑</button>
                                </td>
                            </tr>
                            @endforeach
                            </tbody></table>
                    </div>
                    {{$products->links()}}
                </div>
            </div>
        </div>
    </section>
@endsection
