import 'package:flutter/material.dart';
import 'package:flutter_mall/navigator/zero_navigator.dart';
import 'package:flutter_mall/page/cart_page.dart';
import 'package:flutter_mall/page/category_page.dart';
import 'package:flutter_mall/page/discovery_page.dart';
import 'package:flutter_mall/page/home_page.dart';
import 'package:flutter_mall/page/member_page.dart';
import 'package:flutter_mall/util/color.dart';

///底部导航按钮
class BottomNavigator extends StatefulWidget {

  @override
  _BottomNavigatorState createState() => _BottomNavigatorState();

}


class _BottomNavigatorState extends State<BottomNavigator> {
  final _defaultColor = Colors.grey;
  final _activeColor = primary;

  int _currentIndex = 0;
  static int initialPage = 0;

  final PageController _controller = PageController(initialPage: initialPage);

  List<Widget> _pages;
  bool _hasBuild = false;

  @override
  Widget build(BuildContext context) {
    _pages = [HomePage(), CategoryPage(), DiscoveryPage(), CartPage(), MemberPage()];
    if(!_hasBuild) {
      //页面第一次打开时通知打开的是那个tab
      ZeroNavigator.getInstance().onBottomTabChange(initialPage, _pages[initialPage]);
      _hasBuild = true;
    }

    return Scaffold(
      body: PageView(
        controller: _controller,
        children: _pages,
        onPageChanged: (index) => _onJumpTo(index, pageChange: true),
        physics: NeverScrollableScrollPhysics(),
      ),

      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        onTap: (index) => _onJumpTo(index),
        type: BottomNavigationBarType.fixed,
        selectedItemColor: _activeColor,
        
        items: [
          _bottomItem('首页', Icons.home_outlined, 0),
          _bottomItem('分类', Icons.category_outlined, 1),
          _bottomItem('发现', Icons.search_outlined, 2),
          _bottomItem('购物车', Icons.shopping_cart_outlined, 3),
          _bottomItem('我的', Icons.supervisor_account_outlined, 4),
        ],
      ),
    );
    
  }

  _bottomItem(String title, IconData icon, int index) {
    return BottomNavigationBarItem(
      label: title,
      icon: Icon(icon, color: _defaultColor),
      activeIcon: Icon(icon, color: _activeColor)
    );
  }

  void _onJumpTo(int index, {pageChange = false}) {
    if (!pageChange) {
      //让PageView展示对应tab
      _controller.jumpToPage(index);
    } else {
      ZeroNavigator.getInstance().onBottomTabChange(index, _pages[index]);
    }
    setState(() {
      //控制选中第一个tab
      _currentIndex = index;
    });
  }
}
