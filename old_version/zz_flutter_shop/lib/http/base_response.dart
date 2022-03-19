///基础返回实体
class BaseResponse {
  //返回的消息主体
  dynamic data;

  //状态码
  int code;

  //提示消息
  String message;

  //请求是否ok
  bool ok;

  BaseResponse(this.data, this.code, this.message, this.ok);

  BaseResponse.fromJson(Map<String,dynamic> json) {
    data = json['data'];
    code = json['code'];
    message = json['message'];
    ok = json['ok'];
  }
}