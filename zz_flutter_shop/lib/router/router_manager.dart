import 'package:get/get.dart';
import 'package:zz_flutter_shop/page/address_page.dart';
import 'package:zz_flutter_shop/page/cart_page.dart';
import 'package:zz_flutter_shop/page/category_page.dart';
import 'package:zz_flutter_shop/page/discovery_page.dart';
import 'package:zz_flutter_shop/page/home_page.dart';
import 'package:zz_flutter_shop/page/login_page.dart';
import 'package:zz_flutter_shop/page/main_page.dart';
import 'package:zz_flutter_shop/page/member_page.dart';
import 'package:zz_flutter_shop/page/order_confirm_page.dart';
import 'package:zz_flutter_shop/page/order_list_page.dart';
import 'package:zz_flutter_shop/page/pay_counter_page.dart';
import 'package:zz_flutter_shop/page/pay_dc_page.dart';
import 'package:zz_flutter_shop/page/product_detail_page.dart';
import 'package:zz_flutter_shop/page/product_list_page.dart';
import 'package:zz_flutter_shop/page/qrcode_page.dart';
import 'package:zz_flutter_shop/page/register_page.dart';
import 'package:zz_flutter_shop/page/settings_page.dart';

///路由名称
class Routers{

  static const String main = '/main';
  static const String home = '/home';
  static const String category = '/category';
  static const String discovery = '/discovery';
  static const String cart = '/cart';
  static const String member = '/member';

  static const String productList = '/productList';
  static const String productDetail = '/productDetail';


  static const String login = '/login';
  static const String register = '/register';

  static const String setting = '/setting';

  static const String qrcode = '/qrcode';

  static const String orderConfirm = "/orderConfirm";
  static const String orderList = "/orderList";
  static const String address = "/address";
  static const String payCounter = "/payCounter";

  static const String payDc = "/payDc";

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

    GetPage(name: Routers.productList, page: () => const ProductListPage()),
    GetPage(name: Routers.productDetail, page: () => const ProductDetailPage()),


    GetPage(name: Routers.login, page: () => const LoginPage()),
    GetPage(name: Routers.register, page: () => const RegisterPage()),

    GetPage(name: Routers.setting, page: () => const SettingsPage()),
    GetPage(name: Routers.qrcode, page: () => const QrcodePage()),


    GetPage(name: Routers.orderConfirm, page: () => const OrderConfirmPage()),
    GetPage(name: Routers.orderList, page: () => const OrderListPage()),
    GetPage(name: Routers.address, page: () => const AddressPage()),
    GetPage(name: Routers.payCounter, page: () => const PayCounterPage()),

    GetPage(name: Routers.payDc, page: () => const PayDcPage()),

  ];


}
