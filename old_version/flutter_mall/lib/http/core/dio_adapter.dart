import 'package:dio/dio.dart';
import 'package:flutter_mall/http/core/zero_error.dart';
import 'package:flutter_mall/http/core/zero_net_adapter.dart';
import 'package:flutter_mall/http/request/base_request.dart';

///Dio请求适配器
class DioAdapter extends ZeroNetAdapter {

  @override
  Future<ZeroNetResponse<T>> send<T>(BaseRequest request) async {
    //设置配置
    var response, options = Options(headers: request.headers);
    var error;
    try {

      //对不同method做不同适配
      switch (request.requestMethod()) {
        case RequestMethod.GET:
          response = await Dio().get(request.url(), options: options);
          break;
        case RequestMethod.POST:
          response = await Dio().post(request.url(), data: request.params, options: options);
          break;
        case RequestMethod.DELETE:
          response = await Dio().delete(request.url(), data: request.params, options: options);
          break;
        case RequestMethod.PUT:
          response = await Dio().put(request.url(), data: request.params, options: options);
          break;
        default:
          response = await Dio().post(request.url(), data: request.params, options: options);
      }
    } on DioError catch (e) {
      print(e);
      error = e;
      response = e.response;
    }
    if (error != null) {
      ///抛出ZeroNetError
      throw ZeroNetError(response?.statusCode ?? -1, error.toString(), data: buildResponse(response, request));
    }
    return buildResponse(response, request);
  }

  ///构建ZeroNetResponse
  ZeroNetResponse buildResponse(Response response, BaseRequest request) {
    return ZeroNetResponse(
        data: response.data,
        request: request,
        statusCode: response.statusCode,
        statusMessage: response.statusMessage,
        extra: response);
  }
}