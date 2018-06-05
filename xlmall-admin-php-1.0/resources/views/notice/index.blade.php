@extends("layout.main")

@section("content")

    <div class="col-sm-8 blog-main">
        @foreach($notices as $notice)
            <div class="blog-post">
                <p class="blog-post-meta">{{$notice->title}}</p>

                <p>{{$notice->content}}</p>
            </div>
        @endforeach
    </div><!-- /.blog-main -->


@endsection