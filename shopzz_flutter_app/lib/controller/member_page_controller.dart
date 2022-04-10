import 'package:get/get.dart';
import 'package:shopzz_flutter_app/controller/main_page_controller.dart';
import 'package:shopzz_flutter_app/entity/response/banner_response.dart';
import 'package:shopzz_flutter_app/entity/response/member_info_response.dart';
import 'package:shopzz_flutter_app/router/router_manager.dart';
import 'package:shopzz_flutter_app/router/sp_keys.dart';
import 'package:shopzz_flutter_app/service/home_api_service.dart';
import 'package:shopzz_flutter_app/service/member_api_service.dart';
import 'package:shopzz_flutter_app/utils/sp_util.dart';

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

  ///登录
  Future<bool> paswordRegister(String username, String password, String rePassword) async {
    var registerFlag = await Get.find<MemberApiService>().passwordRegister(username, password, rePassword);
    if(registerFlag) {
      Get.offNamed(Routers.passwordLogin);
      return true;
    }
    return false;
  }


  Future<bool> memberInfo() async {
    var result = await Get.find<MemberApiService>().memberInfo();
    if(result == null) {
      return false;
    }
    memberInfoResponse.value = result;
    return true;
  }


  Future<bool> getNav() async {
    var result = await Get.find<HomeApiService>().getBannerResponse();
    navigationList.clear();
    navigationList.addAll(result.navigations);
    return true;
  }

}