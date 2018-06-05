@if($target_user->id != \Auth::id())
    <div>
        @if($target_user->hasFan(\Auth::id()))
            <button class="btn btn-default like-button" like-value="1" like-user="{{$target_user->id}}" _token="{{csrf_token()}}" type="button">取消关注</button>
        @else
            <button class="btn btn-default like-button" like-value="0" like-user="{{$target_user->id}}" _token="{{csrf_token()}}" type="button">关注</button>
        @endif
    </div>
@endif

