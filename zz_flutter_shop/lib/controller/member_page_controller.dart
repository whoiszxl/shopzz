
import 'dart:async';

import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/controller/main_page_controller.dart';
import 'package:zz_flutter_shop/entity/dto/category_tree_model.dart';
import 'package:zz_flutter_shop/entity/response/home_app_index_response.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';
import 'package:zz_flutter_shop/router/sp_keys.dart';
import 'package:zz_flutter_shop/service/category_api_service.dart';
import 'package:zz_flutter_shop/service/member_api_service.dart';
import 'package:zz_flutter_shop/utils/sp_util.dart';

class MemberPageController extends GetxController {

  MainPageController mainPageController = Get.find();

  ///登录
  Future<bool> login(String username, String password) async {
    var token = await Get.find<MemberApiService>().login(username, password);
    if(token != null && token.isNotEmpty) {
      SPUtil.set(SPKeys.token, token);
      Get.offNamed(Routers.main);
      mainPageController.selectIndexBottomBarMainPage(4);
      return true;
    }
    return false;
  }

}