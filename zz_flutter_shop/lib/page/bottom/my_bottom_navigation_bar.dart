import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/controller/language_controller.dart';
import 'package:zz_flutter_shop/controller/main_page_controller.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';

///首页底部导航
// ignore: must_be_immutable
class MyBottomNavigationBar extends StatefulWidget {

  LanguageController messagesController = Get.put(LanguageController());

  MyBottomNavigationBar({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _MyBottomNavigationBarState();
  }
}

class _MyBottomNavigationBarState extends State<MyBottomNavigationBar>{

  final MainPageController mainPageScrollController = Get.find<MainPageController>();

  //用来获取BottomBar的高度
  final GlobalKey bottomBarKey = GlobalKey();
  Widget _bottomBarLayout;

  @override
  void initState() {
    super.initState();
    _bottomBarLayout = _getLayoutBottomBar();
    //BottomBar绘制完成时候的监听
    WidgetsBinding.instance?.addPostFrameCallback((_bottomBarLayout) {
      double videoViewHeight = MediaQuery.of(context).size.height - bottomBarKey.currentContext.size.height - MediaQueryData.fromWindow(window).padding.top;
      mainPageScrollController.setVideoViewHeight(videoViewHeight);
    });
  }

  @override
  Widget build(BuildContext context) {
    return _bottomBarLayout;
  }

  _getLayoutBottomBar(){
    return Container(
      key: bottomBarKey,
      color:Colors.white,
      height: 44,
      child: Row(
        children: [
          Expanded(
              flex: 1, child: _getBottomTextWidget('home'.tr,0)
          ),
          Expanded(
              flex: 1, child: _getBottomTextWidget('category'.tr,1)
          ),
          Expanded(
              flex: 1,
              child: _getBottomTextWidget('discovery'.tr,2)
          ),
          Expanded(
              flex: 1, child:_getBottomTextWidget('cart'.tr,3)
          ),
          Expanded(
              flex: 1, child:_getBottomTextWidget('me'.tr,4)
          ),
        ],
      ),
    );
  }

  ///获取底部的按钮控件
  _getBottomTextWidget(String barName,int index){
    return TextButton(
        onPressed: (){
          mainPageScrollController.selectIndexBottomBarMainPage(index);
        },
        style: ButtonStyle(
          overlayColor: MaterialStateProperty.all(Colors.transparent),
        ),

        //配置如果选中了就显示白色，不然灰色，选中字体大1号。
        child:Obx(()=>  Text(barName,style: TextStyle(
            color: mainPageScrollController.bottomPageIndex.value == index ? ColorManager.red:ColorManager.yellow,
            fontSize: mainPageScrollController.bottomPageIndex.value == index ? 13:12,
            fontWeight: FontWeight.bold //字体加粗
        ),),
        )
    );
  }

}