@extends("layout.main")

@section("content")

    <div class="alert alert-success" role="alert">
        下面是搜索"{{$keyword}}"出现的文章，共{{$article->total()}}条
    </div>

    <div class="col-sm-8 blog-main">
        @foreach($article as $art)
            <div class="blog-post">
                <h2 class="blog-post-title"><a href="/article/{{$art->id}}" >{{$art->title}}</a></h2>
                <p class="blog-post-meta">{{$art->created_at->toFormattedDateString()}} by <a href="#">Mark</a></p>

                <p>{!! str_limit($art->content, 200, '...') !!}</p>
            </div>
        @endforeach

        {{$article->links()}}
    </div><!-- /.blog-main -->


@endsection