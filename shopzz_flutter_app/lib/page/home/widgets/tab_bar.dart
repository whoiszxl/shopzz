import 'package:flutter/material.dart';
import 'package:oktoast/oktoast.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';

/// 首页上推荐、猜你喜欢...的tab bar组件
class HomeTabBar extends StatefulWidget {

  final TabController tabController;
  final List<String> tabs;
  final VoidCallback onTapCallback;

  HomeTabBar({
    this.tabController,
    this.tabs,
    this.onTapCallback,
  });

  @override
  _HomeTabBarState createState() => _HomeTabBarState();
}

class _HomeTabBarState extends State<HomeTabBar> {
  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: const BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topRight,
            colors: <Color>[
              ColorManager.main, Color.fromARGB(255,238,78,20)
            ],
          )
      ),
      child: Row(
        children: <Widget>[

          Expanded(
            child: TabBar(
              labelColor: ColorManager.white, //选中的标签颜色
              unselectedLabelColor: ColorManager.white, //未选中的标签颜色
              controller: widget.tabController,
              tabs: widget.tabs.map((text) =>
                  SizedBox(height: 34.0, child: Center(child: Text(text)))).toList(), //遍历传入的tab值
              isScrollable: true, //可滚动
              indicatorColor: ColorManager.white, //下标颜色
              indicatorSize: TabBarIndicatorSize.label, //下标size
              labelStyle: const TextStyle(fontSize: 14.0), //选中字体大小
              unselectedLabelStyle: const TextStyle(fontSize: 13.0), //未选中字体大小
              labelPadding: const EdgeInsets.only(left: 15.0, right: 8.0),
              indicatorPadding: const EdgeInsets.only(left: 3.0, right: -3.0),
            ),
          ),

          _icon(Icons.menu, () {
            showToast("点击了");
          }),
        ],
      ),
    );
  }

  _icon(icon, onTap) {
    return InkWell(
      onTap: onTap,
      child: Stack(
        children: <Widget>[
          Opacity(
            opacity: 0.0,
            child: Container(width: 38.0, height: 20.0, color: ColorManager.white),
          ),
          Container(
            width: 38.0,
            height: 20.0,
            alignment: Alignment.center,
            child: Icon(icon, size: 22, color: ColorManager.white),
          ),
        ],
      ),
    );
  }
}