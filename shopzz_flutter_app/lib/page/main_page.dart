
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:shopzz_flutter_app/controller/main_page_controller.dart';
import 'package:shopzz_flutter_app/page/cart/cart_page.dart';
import 'package:shopzz_flutter_app/page/category/category_page.dart';
import 'package:shopzz_flutter_app/page/discovery/discovery_page.dart';
import 'package:shopzz_flutter_app/page/home/home_page.dart';
import 'package:shopzz_flutter_app/page/member/member_page.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';

///主页面
class MainPage extends StatefulWidget {

  const MainPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _MainPageState();
  }
}

class _MainPageState extends State<MainPage> {

  //主页页面控制器
  final MainPageController mainPageController = Get.put(MainPageController());

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    WidgetsBinding.instance?.addPostFrameCallback((_bottomBarLayout) {
      //设置状态栏的颜色和图标模式
      SystemChrome.setSystemUIOverlayStyle(const SystemUiOverlayStyle(
        statusBarColor: Colors.transparent,
        statusBarIconBrightness: Brightness.dark,
      ));
    });

    const pages = <Widget> [
      HomePage(),CategoryPage(),DiscoveryPage(),CartPage(),MemberPage()
    ];

    return Scaffold(
        body: Obx(() {
          return IndexedStack(
            index: mainPageController.bottomPageIndex.value,
            children: pages,
          );
        }),
        bottomNavigationBar: BottomNavigationBar(
          backgroundColor: ColorManager.white,
          selectedItemColor: ColorManager.main,
          unselectedItemColor: ColorManager.grey,
          showUnselectedLabels: true,

          onTap: (index) {
            setState(() {
              mainPageController.bottomPageIndex.value = index;
            });
          },

          currentIndex: mainPageController.bottomPageIndex.value,
          type: BottomNavigationBarType.fixed,

          items: <BottomNavigationBarItem>[
            BottomNavigationBarItem(
              icon: const Icon(Icons.home_outlined),
              label: 'home'.tr,
              activeIcon: const Icon(Icons.home),
            ),
            BottomNavigationBarItem(
              icon: const Icon(Icons.category_outlined),
              label: 'category'.tr,
              activeIcon: const Icon(Icons.category),
            ),
            BottomNavigationBarItem(
              icon: const Icon(Icons.person_search_outlined),
              label: 'discovery'.tr,
              activeIcon: const Icon(Icons.person_search),
            ),
            BottomNavigationBarItem(
              icon: const Icon(Icons.shopping_cart_outlined),
              label: 'cart'.tr,
              activeIcon: const Icon(Icons.shopping_cart),
            ),
            BottomNavigationBarItem(
              icon: const Icon(Icons.person_outlined),
              label: 'me'.tr,
              activeIcon: const Icon(Icons.person),
            ),
          ],

        )
    );
  }

  ///根据全局的底部bar index获取对应的页面容器
  _getPageContainer() {
    switch (mainPageController.bottomPageIndex.value) {
      case 0:
        return const HomePage();
      case 1:
        return const CategoryPage();
      case 2:
        return const DiscoveryPage();
      case 3:
        return const CartPage();
      case 4:
        return const MemberPage();
    }
  }

}

