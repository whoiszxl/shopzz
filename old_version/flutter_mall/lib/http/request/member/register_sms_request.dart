import 'package:flutter_mall/http/request/base_request.dart';

class RegisterSmsRequest extends BaseRequest {
    @override
  RequestMethod requestMethod() {
    return RequestMethod.POST;
  }

  @override
  bool needLogin() {
    return false;
  }

  @override
  String path() {
    return '/member/sendVerifySms';
  }
}