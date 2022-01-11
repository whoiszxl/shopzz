
import 'dart:collection';

import 'package:get/get.dart';
import 'package:zz_flutter_shop/entity/response/member_info_response.dart';
import 'package:zz_flutter_shop/http/http_manager.dart';
import 'package:zz_flutter_shop/router/sp_keys.dart';
import 'package:zz_flutter_shop/service/api_urls.dart';
import 'package:zz_flutter_shop/utils/sp_util.dart';

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

  ///会员账号密码注册
  Future<bool> passwordRegister(String username, String password, String rePassword) async {
    Map<String, Object> params = HashMap();
    params["username"] = username;
    params["password"] = password;
    params["rePassword"] = rePassword;
    var result = await HttpManager.getInstance().post(url: ApiUrls.memberPasswordRegister, data: params);
    if(result == null) {
      print("gfdsgfdgfds");
      return false;
    }
    print("654646");
    return true;
  }

  Future<MemberInfoResponse> memberInfo() async {
    String token = '';
    await SPUtil.getString(SPKeys.token).then((text){
      token = text;
    });

    if(token.isBlank) {
      return null;
    }

    var result = await HttpManager.getInstance().get(url: ApiUrls.memberInfo);
    return MemberInfoResponse.fromJson(result);
  }

}