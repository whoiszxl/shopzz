
import 'package:flutter_mall/http/request/base_request.dart';

///商品搜索请求类
class ProductSearchResultRequest extends BaseRequest {

  @override
  bool needLogin() {
    return false;
  }

  @override
  String path() {
    return "/product/product/search";
  }

  @override
  RequestMethod requestMethod() {
    return RequestMethod.POST;
  }
}