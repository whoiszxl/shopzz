import 'package:flutter_mall/http/request/base_request.dart';

///分类树列表请求类
class CategoryTreeRequest extends BaseRequest {

  @override
  bool needLogin() {
    return false;
  }

  @override
  String path() {
    return "/product/category/list/tree";
  }

  @override
  RequestMethod requestMethod() {
    return RequestMethod.GET;
  }
}