
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/navigator/bottom_navigator.dart';
import 'package:flutter_mall/page/cart_page.dart';
import 'package:flutter_mall/page/category/category_page.dart';
import 'package:flutter_mall/page/detail_page.dart';
import 'package:flutter_mall/page/discovery_page.dart';
import 'package:flutter_mall/page/login_page.dart';
import 'package:flutter_mall/page/member_page.dart';
import 'package:flutter_mall/page/product_list_page.dart';
import 'package:flutter_mall/page/register_page.dart';

typedef RouteChangeListener(RouteStatusInfo current, RouteStatusInfo pre);


///创建页面
pageWrap(Widget child) {
  return MaterialPage(key: ValueKey(child.hashCode), child: child);
}

///获取routeStatus在页面栈中的位置，
int getPageIndex(List<MaterialPage> pages, RouteStatus routeStatus) {
  for (int i = 0; i < pages.length; i++) {
    MaterialPage page = pages[i];
    if (getStatus(page) == routeStatus) {
      return i;
    }
  }
  return -1;
}

///自定义路由封装，路由状态
enum RouteStatus { login, register, home, category, discovery, cart, member, detail, product_list, unknown }

///获取page对应的RouteStatus
RouteStatus getStatus(MaterialPage page) {
  if (page.child is LoginPage) {
    return RouteStatus.login;
  } else if (page.child is RegisterPage) {
    return RouteStatus.register;
  } else if (page.child is BottomNavigator) {
    return RouteStatus.home;
  } else if (page.child is CategoryPage) {
    return RouteStatus.category;
  }  else if (page.child is DiscoveryPage) {
    return RouteStatus.discovery;
  }  else if (page.child is CartPage) {
    return RouteStatus.cart;
  }  else if (page.child is MemberPage) {
    return RouteStatus.member;
  } else if (page.child is DetailPage) {
    return RouteStatus.detail;
  } else if (page.child is ProductListPage) {
    return RouteStatus.product_list;
  } else {
    return RouteStatus.unknown;
  }
}


///路由信息
class RouteStatusInfo {

  //路由状态
  final RouteStatus routeStatus;

  //页面
  final Widget page;

  RouteStatusInfo(this.routeStatus, this.page);
}


class ZeroNavigator extends _RouteJumpListener {

  RouteJumpListener _routeJump;

  List<RouteChangeListener> _listeners = [];
  
  RouteStatusInfo _current;

  //首页底部tab
  RouteStatusInfo _bottomTab;

  ///首页底部tab切换监听
  void onBottomTabChange(int index, Widget page) {
    _bottomTab = RouteStatusInfo(RouteStatus.home, page);
    _notify(_bottomTab);
  }

  ///注册路由跳转逻辑
  void registerRouteJump(RouteJumpListener routeJumpListener) {
    this._routeJump = routeJumpListener;
  }

  ///监听路由页面跳转
  void addListener(RouteChangeListener listener) {
    if (!_listeners.contains(listener)) {
      _listeners.add(listener);
    }
  }

  ///移除监听
  void removeListener(RouteChangeListener listener) {
    _listeners.remove(listener);
  }

  @override
  void onJumpTo(RouteStatus routeStatus, {Map args}) {
    _routeJump.onJumpTo(routeStatus, args: args);
  }



  ///通知路由页面变化
  void notify(List<MaterialPage> currentPages, List<MaterialPage> prePages) {
    if (currentPages == prePages) return;
    var current =
        RouteStatusInfo(getStatus(currentPages.last), currentPages.last.child);
    _notify(current);
  }


  void _notify(RouteStatusInfo current) {
    if(current.page is BottomNavigator && _bottomTab != null) {
      //如果打开的是首页，则明确到首页具体的tab
      current = _bottomTab;
    }

    _listeners.forEach((listener) {
      listener(current, _current);
    });
    _current = current;
  }



  //单例实现
  static ZeroNavigator _instance;
  ZeroNavigator._();
  static ZeroNavigator getInstance() {
    if(_instance == null) {
      _instance = ZeroNavigator._();
    }
    return _instance;
  }
}



///抽象类供ExNavigator实现
abstract class _RouteJumpListener {
  void onJumpTo(RouteStatus routeStatus, {Map args});
}

typedef OnJumpTo = void Function(RouteStatus routeStatus, {Map args});

///定义路由跳转逻辑要实现的功能
class RouteJumpListener {
  final OnJumpTo onJumpTo;
  RouteJumpListener({this.onJumpTo});
}