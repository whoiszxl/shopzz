
import 'dart:async';

import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/controller/main_page_controller.dart';
import 'package:zz_flutter_shop/entity/dto/category_tree_model.dart';
import 'package:zz_flutter_shop/entity/response/home_app_index_response.dart';
import 'package:zz_flutter_shop/entity/response/member_info_response.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';
import 'package:zz_flutter_shop/router/sp_keys.dart';
import 'package:zz_flutter_shop/service/category_api_service.dart';
import 'package:zz_flutter_shop/service/home_api_service.dart';
import 'package:zz_flutter_shop/service/member_api_service.dart';
import 'package:zz_flutter_shop/utils/sp_util.dart';

class MemberPageController extends GetxController {

  MainPageController mainPageController = Get.find();

  List<NavigationEntity> navigationList = <NavigationEntity>[].obs;

  List<BannerEntity> smallBanners = <BannerEntity>[].obs;

  final memberInfoResponse = MemberInfoResponse().obs;

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


  Future<bool> memberInfo() async {
    memberInfoResponse.value = await Get.find<MemberApiService>().memberInfo();
    return true;
  }


  Future<bool> getNav() async {
    var result = await Get.find<HomeApiService>().homeRecommendAppIndex();
    navigationList.clear();
    navigationList.addAll(result.navigations);
    return true;
  }

  Future<bool> getSmallBanners() async {
    var result = await Get.find<HomeApiService>().getBannersByType(3);
    smallBanners.clear();
    smallBanners.addAll(result.banners);
    return true;
  }
}