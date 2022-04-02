import 'package:dio/dio.dart';


class HttpError {
  ///HTTP 状态码
  static const int unauthorized = 401;
  static const int forbidden = 403;
  static const int notFound = 404;
  static const int requestTimeout = 408;
  static const int internalServerError = 500;
  static const int badGateway = 502;
  static const int serviceUnavailable = 503;
  static const int gatewayTimeout = 504;

  ///未知错误
  static const String unknown = "UNKNOWN";

  ///解析错误
  static const String parseError = "PARSE_ERROR";

  ///网络错误
  static const String networkError = "NETWORK_ERROR";

  ///协议错误
  static const String httpError = "HTTP_ERROR";

  ///证书错误
  static const String sslError = "SSL_ERROR";

  ///连接超时
  static const String connectTimeout = "CONNECT_TIMEOUT";

  ///响应超时
  static const String receiveTimeout = "RECEIVE_TIMEOUT";

  ///发送超时
  static const String sendTimeout = "SEND_TIMEOUT";

  ///网络请求取消
  static const String cancel = "CANCEL";

  String code;

  String message;

  HttpError(this.code, this.message);

  HttpError.dioError(DioError error) {
    message = error.message;
    switch (error.type) {
      case DioErrorType.CONNECT_TIMEOUT:
        code = connectTimeout;
        message = "网络连接超时，请检查网络设置";
        break;
      case DioErrorType.RECEIVE_TIMEOUT:
        code = receiveTimeout;
        message = "服务器异常，请稍后重试！";
        break;
      case DioErrorType.SEND_TIMEOUT:
        code = sendTimeout;
        message = "网络连接超时，请检查网络设置";
        break;
      case DioErrorType.RESPONSE:
        code = httpError;
        message = "服务器异常，请稍后重试！";
        break;
      case DioErrorType.CANCEL:
        code = cancel;
        message = "请求已被取消，请重新请求";
        break;
      case DioErrorType.DEFAULT:
        code = unknown;
        message = "网络异常，请稍后重试！";
        break;
    }
  }

  @override
  String toString() {
    return 'HttpError{code: $code, message: $message}';
  }
}
