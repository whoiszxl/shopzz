import 'package:flutter_mall/http/core/dio_adapter.dart';
import 'package:flutter_mall/http/core/zero_error.dart';
import 'package:flutter_mall/http/core/zero_interceptor.dart';
import 'package:flutter_mall/http/core/zero_net_adapter.dart';
import 'package:flutter_mall/http/request/base_request.dart';

class ZeroNet {

  ZeroNet ._();

  static ZeroNet _instance;

  ZeroErrorInterceptor _exErrorInterceptor;

  ///单例获取实例
  static ZeroNet getInstance() {
    if (_instance == null) {
      _instance = ZeroNet._();
    }
    return _instance;
  }


  Future request(BaseRequest request) async {
    ZeroNetResponse response;
    var error;
    try {
      response = await send(request);
    } on ZeroNetError catch (e) {
      error = e;
      response = e.data;
      printLog(e.message);
    } catch (e) {
      //其它异常
      error = e;
      printLog(e);
    }
    if (response == null) {
      printLog(error);
    }
    var result = response.data;
    var status = response.statusCode;
    var exError;

    switch (status) {
      case 200:
        return result;
        break;
      case 401:
        exError = NeedLogin();
        break;
      case 403:
        exError = NeedAuth(result.toString(), data: result);
        break;
      default:
        exError = ZeroNetError(status, result.toString(), data: result);
        break;
    }

    if(_exErrorInterceptor != null) {
      _exErrorInterceptor(exError);
    }

    throw exError;
  }

  void setErrorInterceptor(ZeroErrorInterceptor interceptor) {
    _exErrorInterceptor = interceptor;
  }

  Future<ZeroNetResponse<T>> send<T>(BaseRequest request) async {
    ///使用Dio发送请求
    ZeroNetAdapter adapter = DioAdapter();
    return adapter.send(request);
  }


  void printLog(log) {
    print('ex_net:' + log.toString());
  }
}