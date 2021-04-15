
import 'package:flutter/material.dart';
import 'package:flutter_easyrefresh/easy_refresh.dart';
import 'package:flutter_mall/dao/home_dao.dart';
import 'package:flutter_mall/model/index_banner_model.dart';
import 'package:flutter_mall/model/recommend_model.dart';
import 'package:flutter_mall/util/color.dart';
import 'package:flutter_mall/widget/home_appbar.dart';
import 'package:flutter_mall/widget/home_grid_navigator.dart';
import 'package:flutter_mall/widget/navigation_bar.dart';
import 'package:flutter_mall/widget/recommend_card.dart';
import 'package:flutter_mall/widget/zero_banner.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> with AutomaticKeepAliveClientMixin {

  @override
  bool get wantKeepAlive =>true;

  bool isNav = true;

  ScrollController scrollController = ScrollController();

  bool loading = false;

  @override
  void initState() {
    scrollController.addListener(() {
      var dis = scrollController.position.maxScrollExtent -
          scrollController.position.pixels;
      print('dis:$dis');
      //当距离底部不足300时加载更多
      if (dis < 300 &&
          !loading &&
          //fix 当列表高度不满屏幕高度时不执行加载更多
          scrollController.position.maxScrollExtent != 0) {
        print('------_loadData---');
      }
    });
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
    scrollController.dispose();
  }


  GlobalKey<RefreshIndicatorState> _easyRefreshKey =new GlobalKey<RefreshIndicatorState>();
  GlobalKey<MaterialFooterWidgetState> _footerKey = new GlobalKey<MaterialFooterWidgetState>();

  @override
  Widget build(BuildContext context) {
    super.build(context);
    return Scaffold(
      backgroundColor: Color.fromRGBO(244, 245, 245, 1.0),
      body: FutureBuilder(
        future: _loadData(),
        builder: (context, snapshot) {
          if(snapshot.hasData) {

            //future中的_loadData可以加载多个接口，多个接口请求的数据会保存
            //在snapshot的data中，并以数组的方式保存所有接口请求的结果。
            List<dynamic> results = snapshot.data;
            IndexBannerModel indexBannerModel = results[0];
            List<Banners> bannerList = indexBannerModel.banners;
            List<Navigations> navigatorList = indexBannerModel.navigations;

            RecommendModel hotRecommendList = results[1];
            RecommendModel niceRecommendList = results[2];

            return EasyRefresh(
              footer: ClassicalFooter(
                  key:_footerKey,
                  bgColor:Colors.white,
                  textColor: primary,
                  noMoreText: '没有了',
                  loadingText: '加载中',
                  loadReadyText:'上拉加载....'
              ),
              child: ListView(
                shrinkWrap: true,
                children: <Widget>[
                  //沉浸式Appbar
                  NavigationBar(
                    height: 50,
                    child: homeAppBar(),
                    color: Colors.white,
                    statusStyle: StatusStyle.DARK_CONTENT,
                  ),

                  //banner轮播
                  Padding(
                    padding: EdgeInsets.only(left: 7, right: 7, top: 5),
                    child: ZeroBanner(bannerList),
                  ),

                  //导航小组件
                  HomeGridNavigator(navigatorList: navigatorList),

                  //瀑布流热门推荐列表
                  StaggeredGridView.countBuilder(
                    shrinkWrap: true,
                    physics: const NeverScrollableScrollPhysics (),
                    padding: EdgeInsets.only(top: 10, left: 10, right: 10),
                    crossAxisCount: 2,
                    itemCount: hotRecommendList.content.length,
                    itemBuilder: (BuildContext context, int index) {
                      return RecommendCard(recommendContent: hotRecommendList.content[index]);
                    },

                    staggeredTileBuilder: (int index) {
                      return StaggeredTile.fit(1);
                    },
                  ),

                  //瀑布流精品推荐列表
                  StaggeredGridView.countBuilder(
                    shrinkWrap: true,
                    physics: const NeverScrollableScrollPhysics (),
                    padding: EdgeInsets.only(top: 10, left: 10, right: 10),
                    crossAxisCount: 2,
                    itemCount: hotRecommendList.content.length,
                    itemBuilder: (BuildContext context, int index) {
                      return RecommendCard(recommendContent: niceRecommendList.content[index]);
                    },

                    staggeredTileBuilder: (int index) {
                      return StaggeredTile.fit(1);
                    },
                  )
                ],
              ),
            );
          }else {
            return Center(
              child: Text("没有数据"),
            );
          }
        },
      ),
    );
  }

  ///加载数据
  Future _loadData() async{

    return Future.wait([
      HomeDao.getIndexBanner(),      //获取主页banner和nav导航组件
      HomeDao.getRecommendByType(1), //获取热门商品列表
      HomeDao.getRecommendByType(2)  //获取推荐商品列表
    ]);
  }
}