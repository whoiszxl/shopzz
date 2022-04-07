import 'package:get/get.dart';
import 'package:shopzz_flutter_app/page/cart/cart_page.dart';
import 'package:shopzz_flutter_app/page/category/category_page.dart';
import 'package:shopzz_flutter_app/page/detail/detail_page.dart';
import 'package:shopzz_flutter_app/page/discovery/discovery_page.dart';
import 'package:shopzz_flutter_app/page/home/home_page.dart';
import 'package:shopzz_flutter_app/page/main_page.dart';
import 'package:shopzz_flutter_app/page/member/member_page.dart';

///路由名称，所有路由在此处统一管理
///跳转时直接使用如下代码进行处理：Get.toNamed(Routers.pageName, parameters: map);
class Routers{

  static const String main = '/main';
  static const String home = '/home';
  static const String category = '/category';
  static const String discovery = '/discovery';
  static const String cart = '/cart';
  static const String member = '/member';

  static const String detail = '/detail';

}

///路由管理者
class RouterManager{
  static final routes = [
    GetPage(name: Routers.main, page: () => const MainPage()),
    GetPage(name: Routers.home, page: () => const HomePage()),
    GetPage(name: Routers.category, page: () => const CategoryPage()),
    GetPage(name: Routers.discovery, page: () => const DiscoveryPage()),
    GetPage(name: Routers.cart, page: () => const CartPage()),
    GetPage(name: Routers.member, page: () => const MemberPage()),

    GetPage(name: Routers.detail, page: () => const DetailPage()),
  ];

}
