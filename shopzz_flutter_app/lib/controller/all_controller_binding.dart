import 'package:get/get.dart';
import 'package:shopzz_flutter_app/controller/cart_page_controller.dart';
import 'package:shopzz_flutter_app/controller/category_page_controller.dart';
import 'package:shopzz_flutter_app/controller/coupon_page_controller.dart';
import 'package:shopzz_flutter_app/controller/language_controller.dart';
import 'package:shopzz_flutter_app/controller/main_page_controller.dart';
import 'package:shopzz_flutter_app/controller/member_address_controller.dart';
import 'package:shopzz_flutter_app/controller/member_page_controller.dart';
import 'package:shopzz_flutter_app/controller/product_page_controller.dart';
import 'package:shopzz_flutter_app/controller/recommend_page_controller.dart';
import 'package:shopzz_flutter_app/controller/seckill_page_controller.dart';

///所有控制器的加载器，通过Get.lazyPut进行懒注入
class AllControllerBinding implements Bindings {

  @override
  void dependencies() {
    Get.lazyPut<MainPageController>(() => MainPageController());
    Get.lazyPut<LanguageController>(() => LanguageController());
    Get.lazyPut<RecommendPageController>(() => RecommendPageController());
    Get.lazyPut<CategoryPageController>(() => CategoryPageController());
    Get.lazyPut<ProductPageController>(() => ProductPageController());
    Get.lazyPut<MemberPageController>(() => MemberPageController());
    Get.lazyPut<CouponPageController>(() => CouponPageController());
    Get.lazyPut<CartPageController>(() => CartPageController());
    Get.lazyPut<MemberAddressController>(() => MemberAddressController());
    Get.lazyPut<SeckillPageController>(() => SeckillPageController());
  }
}