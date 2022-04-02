import 'package:dio/dio.dart';
import 'package:shopzz_flutter_app/router/sp_keys.dart';
import 'package:shopzz_flutter_app/utils/sp_util.dart';

///http header头拦截器，将token从sp里获取并放到Authorization中
class HeaderInterceptor implements Interceptor{
  @override
  Future onError(DioError err)async {
    return err;
  }

  @override
  Future onRequest(RequestOptions options) async{
    String token = '';
    await SPUtil.getString(SPKeys.token).then((text){
      token = text;
    });
    if(token != null && token.isNotEmpty) {
      options.headers['Authorization'] = 'Bearer ' + token??'';
    }
    return options;
  }

  @override
  Future onResponse(Response response) async{
    return response;
  }

}