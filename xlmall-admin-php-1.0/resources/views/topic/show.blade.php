@extends('layout.main') @section("content")
<div class="col-sm-8">
    <blockquote>
        <p>{{$topic->name}}</p>
        <footer>文章：{{$topic->article_topics_count}}</footer>
        <button class="btn btn-default topic-submit" data-toggle="modal" 
        data-target="#topic_submit_modal" topic-id="{{$topic->id}}" type="button">投稿</button>
    </blockquote>
</div>
<div class="modal fade" id="topic_submit_modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">我的文章</h4>
            </div>
            <div class="modal-body">
                <form action="/topic/submit/{{$topic->id}}">
                    {{csrf_field()}}
                    @foreach($myarticles as $myarticle)
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="article_ids[]" value="{{$myarticle->id}}">
                            {{$myarticle->title}}
                        </label>
                    </div>
                    @endforeach

                    <button type="submit" class="btn btn-default">投稿</button>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="col-sm-8 blog-main">
    <div class="nav-tabs-custom">
        <ul class="nav nav-tabs">
            <li class="active">
                <a href="#tab_1" data-toggle="tab" aria-expanded="true">文章</a>
            </li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="tab_1">

                @foreach($articles as $article)
                <div class="blog-post" style="margin-top: 30px">
                    <p class="">
                        <a href="/user/{{$article->user->id}}">{{$article->user->name}}</a> {{$article->created_at->diffForHumans()}}</p>
                    <p class="">
                        <a href="/article/{{$article->id}}">
                            {{$article->title}}
                        </a>
                    </p>

                    <p>
                        {!! str_limit($article->content,100,'.....') !!}
                    </p>
                </div>
                @endforeach
                
            </div>

        </div>
        <!-- /.tab-content -->
    </div>


</div>
<!-- /.blog-main -->
@endsection