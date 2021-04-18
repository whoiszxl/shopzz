import 'package:flutter_mall/config/common_config.dart';

///http请求方法枚举
enum RequestMethod {
  GET,
  HEAD,
  POST,
  PUT,
  PATCH,
  DELETE,
  OPTIONS,
  TRACE
}

///基础请求抽象类，让其他请求类来实现此类来扩展。
abstract class BaseRequest {

  ///路径参数
  var pathParams;

  ///是否开启https，默认关闭
  var useHttps = false;

  ///请求参数
  Map<String, String> params = Map();
  Map<String, dynamic> headers = Map();


  String domainName() {
    return CommonConfig.apiUrl;
  }

  ///重写配置请求方式
  RequestMethod requestMethod();

  ///重写配置请求路径
  String path();

  ///重写 是否需要登录状态
  bool needLogin();


  ///url拼接
  String url() {
    Uri uri;
    var pathStr = path();
    //拼接path参数
    if (pathParams != null) {
      if (path().endsWith("/")) {
        pathStr = "${path()}$pathParams";
      } else {
        pathStr = "${path()}/$pathParams";
      }
    }

    //http和https切换
    if (useHttps) {
      uri = Uri.https(domainName(), pathStr, params);
    } else {
      uri = Uri.http(domainName(), pathStr, params);
    }

    return uri.toString();
  }

  ///添加参数
  BaseRequest addParam(String k, Object v) {
    params[k] = v.toString();
    return this;
  }

  ///添加header
  BaseRequest addHeader(String k, Object v) {
    headers[k] = v.toString();
    return this;
  }

  ///添加token
  BaseRequest addHeaderToken(String token) {
    headers['Authorization'] = token;
    return this;
  }

}