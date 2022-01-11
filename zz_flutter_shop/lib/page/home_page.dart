import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/page/home_tab_core_page.dart';
import 'package:zz_flutter_shop/page/home_tab_sub_page.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';

///home主頁，下分两个tab
class HomePage extends StatefulWidget {

  const HomePage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _HomePageState();
  }
}

class _HomePageState extends State<HomePage> with AutomaticKeepAliveClientMixin<HomePage> {

  TabController _tabController;
  PageController _pageController;

  double tabHeight = 40;
  double _screenHeight;
  double _screenWidth;

  @override
  void initState() {
    super.initState();
    //初始化tabController，长度为2，【首页，订阅】，初始index为第一个
    //_tabController = TabController(length: 2, vsync: this);
    //初始化pageController，初始化2个页面，分别为首页，订阅页面，并保持页面，在切换后还能回到原处。
    _pageController = PageController(initialPage: 0,keepPage: true);

    //配置单次回调，在frame绘制完成后
    WidgetsBinding.instance?.addPostFrameCallback((_bottomBarLayout) {
      //配置系统的bar样式
      SystemChrome.setSystemUIOverlayStyle(const SystemUiOverlayStyle(
        statusBarColor: Colors.transparent,
        statusBarIconBrightness: Brightness.light,
      ));
    });
  }

  @override
  void dispose() {
    super.dispose();
    _tabController.dispose();
    _pageController.dispose();
  }
  @override
  Widget build(BuildContext context) {
    super.build(context);
    //获取屏幕的初始化高度和宽度
    _screenHeight = MediaQuery.of(context).size.height;
    _screenWidth = MediaQuery.of(context).size.width;
    return  Scaffold(
      backgroundColor: ColorManager.bg, //配置背景主色
      body: Container(
        color: ColorManager.bg, //配置背景主色
        width: _screenWidth, //配置容器的宽为屏幕的宽度
        height: _screenHeight, //配置容器的高为屏幕的高度
        child: Stack(
          children: [
            SizedBox( //如果存在状态栏，就设置一个box将状态栏撑开。
              width: MediaQuery.of(context).size.width,
              height: MediaQueryData.fromWindow(window).padding.top,
            ),
            Positioned( //配置
                top: MediaQueryData.fromWindow(window).padding.top,
                child: _getTabContent()),
            Positioned(
                top: MediaQueryData.fromWindow(window).padding.top,
                child: _getTopLayout()),
          ],
        ),
      )
    );
  }


  _getTopLayout() {
    return SizedBox(
      width: MediaQuery.of(context).size.width,
      height: tabHeight,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Container(),
          //_getHomeTabBar(),
          Container(),
        ],
      ),
    );
  }

  //头部：城市、关注、推荐
  _getHomeTabBar() {
    return Container(
      width: 100,
      height: tabHeight,
      alignment: Alignment.center,
      child: TabBar(
        //定义三个tab的文字内容
        tabs: [
          Text('home'.tr),
          Text('sub'.tr),
        ],
        indicatorColor: ColorManager.white, //定义下标颜色
        indicatorSize: TabBarIndicatorSize.label, //下标大小
        labelPadding: const EdgeInsets.only(bottom: 2, top: 8),//标签padding
        labelColor: ColorManager.white, //标签的选中颜色
        unselectedLabelColor: ColorManager.grey, //标签的未选中颜色
        labelStyle: const TextStyle(fontSize: 12,fontWeight: FontWeight.bold), //label样式，18大小，字体加粗
        controller: _tabController, //tab bar 的控制器
        onTap: (index){
          //点击事件，点击后跳转到对应tab
          _pageController.jumpToPage(index);
        },
      ),

    );
  }

  ///获取tab的内容页面
  _getTabContent() {
    //定义内容高度，减去tab高度和状态栏高度
    double contentHeight = MediaQuery.of(context).size.height - tabHeight - MediaQueryData.fromWindow(window).padding.top;

    //返回一个约束框，指定最大宽度为屏幕宽度，最大高度为内容高度，约束框里为PageView，返回城市，关注，推荐三个tab。
    return ConstrainedBox(
      constraints: BoxConstraints(
        maxWidth: MediaQuery.of(context).size.width,
        maxHeight: contentHeight,
      ),
      child: NotificationListener(
        child: PageView(
            physics: const NeverScrollableScrollPhysics(),
            controller: _pageController,
            children: [
              const HomeTabCorePage(),
              HomeTabSubPage(), //订阅tab
            ],

            onPageChanged: (index) {
              _tabController.animateTo(index);
            }),
      ),
    );
  }

  @override
  bool get wantKeepAlive => true;
}