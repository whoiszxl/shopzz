import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:zz_flutter_shop/controller/all_controller_binding.dart';
import 'package:zz_flutter_shop/i18n/messages.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';
import 'package:zz_flutter_shop/service/category_api_service.dart';
import 'package:zz_flutter_shop/service/home_api_service.dart';
import 'package:zz_flutter_shop/service/member_api_service.dart';
import 'package:zz_flutter_shop/service/product_api_service.dart';

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
Future<void> initServices() async {
  print("init services inject start...");
  await Get.putAsync(() async => HomeApiService());
  await Get.putAsync(() async => CategoryApiService());
  await Get.putAsync(() async => ProductApiService());
  await Get.putAsync(() async => MemberApiService());
  print("init services inject end...");
}
