import 'dart:collection';

import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/dto/error_dto.dart';
import 'package:shopzz_flutter_app/entity/response/member_info_response.dart';
import 'package:shopzz_flutter_app/http/api_urls.dart';
import 'package:shopzz_flutter_app/http/http_manager.dart';
import 'package:shopzz_flutter_app/router/sp_keys.dart';
import 'package:shopzz_flutter_app/utils/sp_util.dart';

///会员接口服务
class MemberApiService extends GetxService {

  ///会员登录，返回token
  Future<String> login(String username, String password) async {
    Map<String, Object> params = HashMap();
    params["username"] = username;
    params["password"] = password;
    var result = await HttpManager.getInstance().post(url: ApiUrls.memberLogin, data: params);
    if(result is ErrorDTO) {
      return null;
    }
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
      return false;
    }
    return true;
  }

  Future<MemberInfoResponse> memberInfo() async {
    String token = 'Bearer ';
    await SPUtil.getString(SPKeys.token).then((text){
      token += text;
    });

    debugPrint("当前的token: " + token);

    if(token.isBlank) {
      return null;
    }

    var result = await HttpManager.getInstance().get(url: ApiUrls.memberInfo);
    debugPrint("result:" + result.toString());
    return MemberInfoResponse.fromJson(result);
  }

}