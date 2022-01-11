import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/controller/home_page_controller.dart';
import 'package:zz_flutter_shop/entity/response/home_app_index_response.dart';
import 'package:zz_flutter_shop/page/widget/home_banner.dart';
import 'package:zz_flutter_shop/page/widget/home_grid_navigator.dart';
import 'package:zz_flutter_shop/page/widget/home_header.dart';
import 'package:zz_flutter_shop/page/widget/home_recommend_card.dart';
import 'package:zz_flutter_shop/utils/loading_util.dart';

///home主页 主tab页面
class HomeTabCorePage extends StatefulWidget {

  ///内容高度
  final double contentHeight;

  final PageController pageController;

  const HomeTabCorePage({Key key,this.contentHeight,this.pageController}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _HomeTabCorePageState();
  }
}

class _HomeTabCorePageState extends State<HomeTabCorePage> with AutomaticKeepAliveClientMixin<HomeTabCorePage>{

  final PageController _pageController = PageController(keepPage: true);
  final RefreshController _refreshController = RefreshController(initialRefresh: false);
  final HomePageController _homePageController = Get.find<HomePageController>();

  double _screenWidth;

  //在tab切换时保持页面状态
  @override
  bool get wantKeepAlive => true;

  @override
  void initState() {
    super.initState();
    _homePageController.refreshHomeRecommendAppIndex(_refreshController);
    _homePageController.getHotRecommendList(_refreshController);
  }

  @override
  void dispose() {
    super.dispose();
    _pageController.dispose();
    _refreshController.dispose();
  }
  @override
  Widget build(BuildContext context) {
    super.build(context);
    //获取屏幕的初始化高度和宽度
    _screenWidth = MediaQuery.of(context).size.width;
    return SmartRefresher(
      controller: _refreshController,
      enablePullUp: true,
      enablePullDown: true,
      onRefresh: () { _homePageController.refreshHomeRecommendAppIndex(_refreshController); },
      onLoading: () {},
      child: _buildHome(context),
    );
  }

  _buildHome(BuildContext context) {
    return Obx(() {
      List<NavigationEntity> navList = _homePageController.navigationList;
      List<BannerEntity> bannerList = _homePageController.bannerList;

      if(_homePageController.navigationList.isEmpty || _homePageController.bannerList.isEmpty) {
        return normalLoading();
      }else {
        return Column(children: [
          //头部布局
          Expanded(child: CustomScrollView(
            slivers: <Widget>[
              //header头部，scan和search框等控件
              const HomeHeader(),

              SliverToBoxAdapter(
                child: Column(
                  children: <Widget>[

                    //banner轮播控件
                    Container(
                      height: 168,
                      alignment: Alignment.topCenter,

                      decoration: const BoxDecoration(
                        image: DecorationImage(
                          image: NetworkImage('http://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/bg1.jpg'),
                          fit: BoxFit.fitWidth,
                          alignment: Alignment.bottomCenter
                        ),
                      ),

                      child: HomeBanner(bannerList, bannerWidth: _screenWidth - 20),
                    ),

                    //nav控件
                    Container(
                      height: 168,
                      decoration: const BoxDecoration(
                          image: DecorationImage(
                            image: NetworkImage('http://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/bg1.jpg'),
                            fit: BoxFit.fitWidth,
                            alignment: Alignment.bottomCenter
                          )),
                      child: HomeGridNavigator(navigatorList: navList, gridHeight: 150, gridWidth: _screenWidth),
                    ),

                    Container(
                      decoration: const BoxDecoration(
                          image: DecorationImage(
                              image: NetworkImage('http://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/bg2.jpg'),
                              fit: BoxFit.cover,
                              alignment: Alignment.bottomCenter
                          )),
                      child: StaggeredGridView.countBuilder(
                        shrinkWrap: true,
                        physics: const NeverScrollableScrollPhysics (),
                        padding: const EdgeInsets.only(top: 10, left: 4, right: 4),
                        crossAxisCount: 2,
                        itemCount: _homePageController.hotRecommendList.length,
                        itemBuilder: (BuildContext context, int index) {
                          return HomeRecommendCard(recommendContent:  _homePageController.hotRecommendList[index]);
                        },

                        staggeredTileBuilder: (int index) {
                          return const StaggeredTile.fit(1);
                        },
                      ),
                    ),

                  ],
                ),
              ),

              //瀑布流热门推荐列表


              //瀑布流精品推荐列表


              //个性化推荐列表


            ],
          )),
        ]);
      }
    });
  }
}