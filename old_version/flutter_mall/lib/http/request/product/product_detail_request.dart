
import 'package:flutter_mall/http/request/base_request.dart';

///商详请求类
class ProductDetailRequest extends BaseRequest {

  @override
  bool needLogin() {
    return false;
  }

  @override
  String path() {
    return "/product/product/detail/";
  }

  @override
  RequestMethod requestMethod() {
    return RequestMethod.POST;
  }
}