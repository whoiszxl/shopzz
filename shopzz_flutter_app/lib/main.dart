import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:shopzz_flutter_app/controller/all_controller_binding.dart';
import 'package:shopzz_flutter_app/i18n/messages.dart';
import 'package:shopzz_flutter_app/router/router_manager.dart';
import 'package:shopzz_flutter_app/service/address_api_service.dart';
import 'package:shopzz_flutter_app/service/category_api_service.dart';
import 'package:shopzz_flutter_app/service/coupon_api_service.dart';
import 'package:shopzz_flutter_app/service/home_api_service.dart';
import 'package:shopzz_flutter_app/service/member_api_service.dart';
import 'package:shopzz_flutter_app/service/product_api_service.dart';
import 'package:shopzz_flutter_app/service/seckill_api_service.dart';

///主程序运行入口
void main() async {

  await initServices();

  runApp(OKToast(
      child: GetMaterialApp(
        initialBinding: AllControllerBinding(), //懒加载所有的控制器
        debugShowCheckedModeBanner: false, //关闭右上角的debug banner
        getPages: RouterManager.routes, //所有的路由
        initialRoute: Routers.main, //初始化的路由
        translations: Messages(), //国际化语言包
        locale: const Locale('zh', 'CN'), //设置默认语言为中文
        fallbackLocale: const Locale('zh', 'CN'), //配置错误下使用中文
      )
  ));
}

///Service手动注入，通过 Get.find<HomeApiService>() 的方式获取注入的对象
///和Java中的服务注入差不多
Future<void> initServices() async {
  await Get.putAsync(() async => HomeApiService());
  await Get.putAsync(() async => CategoryApiService());
  await Get.putAsync(() async => ProductApiService());
  await Get.putAsync(() async => MemberApiService());
  await Get.putAsync(() async => CouponApiService());
  await Get.putAsync(() async => AddressApiService());
  await Get.putAsync(() async => SeckillApiService());
}
