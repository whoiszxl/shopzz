import 'package:get/get.dart';
import 'package:shopzz_flutter_app/page/cart/cart_page.dart';
import 'package:shopzz_flutter_app/page/cart/order_confirm_page.dart';
import 'package:shopzz_flutter_app/page/category/category_page.dart';
import 'package:shopzz_flutter_app/page/coupon/coupon_page.dart';
import 'package:shopzz_flutter_app/page/detail/detail_page.dart';
import 'package:shopzz_flutter_app/page/discovery/discovery_page.dart';
import 'package:shopzz_flutter_app/page/home/home_page.dart';
import 'package:shopzz_flutter_app/page/home/seckill/seckill_result_page.dart';
import 'package:shopzz_flutter_app/page/login/password_login_page.dart';
import 'package:shopzz_flutter_app/page/login/register_page.dart';
import 'package:shopzz_flutter_app/page/login/sms_login_page.dart';
import 'package:shopzz_flutter_app/page/main_page.dart';
import 'package:shopzz_flutter_app/page/member/add_address_page.dart';
import 'package:shopzz_flutter_app/page/member/address_page.dart';
import 'package:shopzz_flutter_app/page/member/member_page.dart';
import 'package:shopzz_flutter_app/page/member/qrcode_page.dart';
import 'package:shopzz_flutter_app/page/member/settings_page.dart';

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


  static const String passwordLogin = '/passwordLogin';
  static const String smsLogin = '/smsLogin';
  static const String register = '/register';

  static const String qrcode = '/qrcode';
  static const String settings = '/settings';
  static const String address = '/address';
  static const String addAddress = '/addAddress';

  static const String coupon = '/coupon';

  static const String seckillResult = '/seckillResult';

  static const String orderConfirm = '/orderConfirm';

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

    GetPage(name: Routers.passwordLogin, page: () => const PasswordLoginPage()),
    GetPage(name: Routers.smsLogin, page: () => const SmsLoginPage()),
    GetPage(name: Routers.register, page: () => const RegisterPage()),

    GetPage(name: Routers.qrcode, page: () => const QrcodePage()),

    GetPage(name: Routers.coupon, page: () => const CouponPage()),

    GetPage(name: Routers.settings, page: () => const SettingsPage()),
    GetPage(name: Routers.address, page: () => const AddressPage()),
    GetPage(name: Routers.addAddress, page: () => const AddAddressPage()),

    GetPage(name: Routers.seckillResult, page: () => const SeckillResultPage()),


    GetPage(name: Routers.orderConfirm, page: () => const OrderConfirmPage()),

  ];

}
