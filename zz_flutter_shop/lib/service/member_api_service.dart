
import 'dart:collection';

import 'package:get/get.dart';
import 'package:zz_flutter_shop/http/http_manager.dart';
import 'package:zz_flutter_shop/service/api_urls.dart';

///会员接口服务
class MemberApiService extends GetxService {

  ///会员登录，返回token
  Future<String> login(String username, String password) async {
    Map<String, Object> params = HashMap();
    params["username"] = username;
    params["password"] = password;
    var result = await HttpManager.getInstance().post(url: ApiUrls.memberLogin, data: params);
    return result;
  }

}