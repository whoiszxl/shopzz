
import 'package:get/get.dart';
import 'package:zz_flutter_shop/controller/cart_page_controller.dart';
import 'package:zz_flutter_shop/controller/category_page_controller.dart';
import 'package:zz_flutter_shop/controller/home_page_controller.dart';
import 'package:zz_flutter_shop/controller/language_controller.dart';
import 'package:zz_flutter_shop/controller/main_page_controller.dart';
import 'package:zz_flutter_shop/controller/member_page_controller.dart';
import 'package:zz_flutter_shop/controller/product_page_controller.dart';

///所有控制器的加载器，通过Get.lazyPut进行懒注入
class AllControllerBinding implements Bindings {

  @override
  void dependencies() {
    Get.lazyPut<MainPageController>(() => MainPageController());
    Get.lazyPut<LanguageController>(() => LanguageController());
    Get.lazyPut<HomePageController>(() => HomePageController());
    Get.lazyPut<CategoryPageController>(() => CategoryPageController());
    Get.lazyPut<ProductPageController>(() => ProductPageController());
    Get.lazyPut<MemberPageController>(() => MemberPageController());
    Get.lazyPut<CartPageController>(() => CartPageController());
  }
}