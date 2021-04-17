import 'package:flutter/material.dart';
import 'package:flutter_mall/cache/sp_cache.dart';
import 'package:flutter_mall/config/no_login_route_config.dart';
import 'package:flutter_mall/dao/member_dao.dart';
import 'package:flutter_mall/http/core/zero_error.dart';
import 'package:flutter_mall/http/core/zero_net.dart';
import 'package:flutter_mall/model/product_model.dart';
import 'package:flutter_mall/navigator/bottom_navigator.dart';
import 'package:flutter_mall/navigator/zero_navigator.dart';
import 'package:flutter_mall/page/detail_page.dart';
import 'package:flutter_mall/page/login_page.dart';
import 'package:flutter_mall/page/product_list_page.dart';
import 'package:flutter_mall/page/register_page.dart';
import 'package:flutter_mall/util/color.dart';
import 'package:flutter_mall/util/toast.dart';

void main() {
  runApp(ZeroApp());
}

class ZeroApp extends StatefulWidget {

  @override
  _ZeroAppState createState() => _ZeroAppState();
}

class _ZeroAppState extends State<ZeroApp> {

  ZeroRouteDelegate _routeDelegate = new ZeroRouteDelegate();

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<SpCache>(
      //初始化cache
      future: SpCache.preInit(),
      builder: (BuildContext context, AsyncSnapshot<SpCache> snapshot) {

        var widget = snapshot.connectionState == ConnectionState.done ?
        Router(routerDelegate: _routeDelegate) : Scaffold(body: Center(child: CircularProgressIndicator(),));
        return MaterialApp(
          home: widget,
          theme: ThemeData(primarySwatch: white),
        );
      },
    );
  }

}


class ZeroRouteDelegate extends RouterDelegate<ZeroRoutePath>
    with ChangeNotifier, PopNavigatorRouterDelegateMixin<ZeroRoutePath> {
  final GlobalKey<NavigatorState> navigatorKey;

  RouteStatus _routeStatus = RouteStatus.home;
  List<MaterialPage> pages = [];
  ProductModel productModel;
  String searchQuery;

  ZeroRouteDelegate() : navigatorKey = GlobalKey<NavigatorState>() {
    //路由跳转
    ZeroNavigator.getInstance().registerRouteJump(
        RouteJumpListener(onJumpTo: (RouteStatus routeStatus, {Map args}) {
          _routeStatus = routeStatus;
          if(routeStatus == RouteStatus.detail) {
            this.productModel = args['productModel'];
          }

          if(routeStatus == RouteStatus.product_list) {
            this.searchQuery = args['query'];
          }

          notifyListeners();
        }));

    ZeroNet.getInstance().setErrorInterceptor((error) {
      if(error is NeedLogin) {
        SpCache.getInstance().setString(MemberDao.LOCAL_TOKEN, null);
        ZeroNavigator.getInstance().onJumpTo(RouteStatus.login);
      }

    });
  }

  @override
  Future<void> setNewRoutePath(ZeroRoutePath path) async {}

  bool get hasLogin => MemberDao.getLocalToken() != null;

  ///获取路由状态，如果页面不是注册状态并且未登录都强制跳登录，如果币种模型不为空则跳转币种详情
  RouteStatus get routeStatus {
    if (!noLoginRoute.contains(_routeStatus) && !hasLogin) {
      return _routeStatus = RouteStatus.login;
    } else if (productModel != null) {
      return _routeStatus = RouteStatus.detail;
    } else {
      return _routeStatus;
    }
  }

  @override
  Widget build(BuildContext context) {
    //获取当前路由状态在页面List中的位置
    var index = getPageIndex(pages, routeStatus);
    List<MaterialPage> tempPages = pages;

    if(index != -1) {
      //此时说明需要打开的页面在List中已经存在，则需要将这个页面和其上面所有页面都出栈,此处要求栈内只有一个同页面的实例。
      tempPages = tempPages.sublist(0, index);
    }

    var page;
    switch(routeStatus) {
      case RouteStatus.home:
        pages.clear();
        page = pageWrap(BottomNavigator());
        break;
      case RouteStatus.detail:
        page = pageWrap(DetailPage(productModel));
        break;
      case RouteStatus.product_list:
        page = pageWrap(ProductListPage(query: searchQuery));
        break;
      case RouteStatus.login:
        page = pageWrap(LoginPage());
        break;
      case RouteStatus.register:
        page = pageWrap(RegisterPage());
        break;
      default:
        page = pageWrap(BottomNavigator());
    }

    //重新创建一个数组，否则pages因引用没有改变路由不会生效
    tempPages = [...tempPages, page];
    //通知路由发生变化
    ZeroNavigator.getInstance().notify(tempPages, pages);

    pages = tempPages;

    return WillPopScope(
      //fix Android物理返回键，无法返回上一页问题@https://github.com/flutter/flutter/issues/66349
      onWillPop: () async => !await navigatorKey.currentState.maybePop(),
      child: Navigator(
        key: navigatorKey,
        pages: pages,
        onPopPage: (route, result) {
          if(route.settings is MaterialPage) {
            //登录页未登录返回拦截
            if ((route.settings as MaterialPage).child is LoginPage) {
//              if (!hasLogin) {
//                showErrorToast("请先登录");
//                return false;
//              }
            }
          }
          //执行返回操作
          if (!route.didPop(result)) {
            return false;
          }
          var tempPages = [...pages];
          pages.removeLast();
          //通知路由发生变化
          ZeroNavigator.getInstance().notify(pages, tempPages);
          return true;
        },
      ),
    );
  }
}

///定义路由数据，path
class ZeroRoutePath {
  final String location;

  ZeroRoutePath.home() : location = "/";

  ZeroRoutePath.detail() : location = "/detail";
}
