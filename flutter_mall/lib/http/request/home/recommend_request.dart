import 'package:flutter_mall/http/request/base_request.dart';

///banner请求类
class RecommendRequest extends BaseRequest {

  @override
  bool needLogin() {
    return false;
  }

  @override
  String path() {
    return "/promotion/home/recommend/list";
  }

  @override
  RequestMethod requestMethod() {
    return RequestMethod.POST;
  }
}