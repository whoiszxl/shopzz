import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:shopzz_flutter_app/page/home/guess/guess_page.dart';
import 'package:shopzz_flutter_app/page/home/recommend/recommend_page.dart';
import 'package:shopzz_flutter_app/page/home/widgets/search_bar.dart';
import 'package:shopzz_flutter_app/page/home/widgets/tab_bar.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';

///首页页面
class HomePage extends StatefulWidget {
  const HomePage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _HomePageState();
  }
}

class _HomePageState extends State<HomePage> with SingleTickerProviderStateMixin, AutomaticKeepAliveClientMixin{

  final Map<String, List> tabViewKey = {};

  TabController _tabController;

  List<String> tabs = [];

  List<Widget> views = [];

  List<String> images = [];

  @override
  void initState() {
    //初始化数据

    tabViewKey['推荐'] = [const RecommendPage(), ""];
    tabViewKey['猜你喜欢'] = [const GuessPage(), ""];
    tabViewKey['手机'] = [const GuessPage(), ""];

    tabViewKey.forEach((key, value) {
      tabs.add(key);
      views.add(value[0]);
      images.add(value[1]);
    });

    _tabController = TabController(length: tabs.length, vsync: this);

    //配置单次回调，在frame绘制完成后
    WidgetsBinding.instance?.addPostFrameCallback((_bottomBarLayout) {
      //配置系统的bar样式
      SystemChrome.setSystemUIOverlayStyle(const SystemUiOverlayStyle(
        statusBarColor: Colors.transparent,
        statusBarIconBrightness: Brightness.light,
      ));
    });
    super.initState();
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    super.build(context);
    GlobalKey _appBarKey = GlobalKey();
    return Scaffold(
      backgroundColor: ColorManager.white,
      appBar: AppBar(
        key: _appBarKey,
        backgroundColor: ColorManager.main,
        iconTheme: const IconThemeData(),
        elevation: 0.0,
        titleSpacing: 0.0,
        title: const SearchBar(),

        bottom: PreferredSize(
          child: HomeTabBar(
            tabController: _tabController,
            tabs: tabs,
            onTapCallback: () {

            },
          ),
          preferredSize: const Size.fromHeight(30.0),
        ),

      ),
      body: TabBarView(
        controller: _tabController,
        children: views,
      )
    );
  }

  @override
  bool get wantKeepAlive => true;
}