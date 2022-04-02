import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:shopzz_flutter_app/controller/recommend_page_controller.dart';
import 'package:shopzz_flutter_app/entity/response/banner_response.dart';
import 'package:shopzz_flutter_app/entity/response/column_detail_response.dart';
import 'package:shopzz_flutter_app/page/home/widgets/banner_bar.dart';
import 'package:shopzz_flutter_app/page/home/widgets/home_grid_navigator.dart';
import 'package:shopzz_flutter_app/utils/loading_util.dart';


///HOME主页 推荐tab页面
class RecommendPage extends StatefulWidget {

  const RecommendPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _RecommendPageState();
  }
}

class _RecommendPageState extends State<RecommendPage> with TickerProviderStateMixin, AutomaticKeepAliveClientMixin {

  final PageController _pageController = PageController(keepPage: true);
  final RefreshController _refreshController = RefreshController(initialRefresh: false);
  final RecommendPageController _recommendPageController = Get.find<RecommendPageController>();

  double _screenWidth;

  @override
  void initState() {
    _recommendPageController.refreshBannerResponse(_refreshController);
    _recommendPageController.getFirstColumnDetail(_refreshController);
    super.initState();
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
      onRefresh: () { _recommendPageController.refreshBannerResponse(_refreshController); },
      onLoading: () {  },
      child: _build(context),
    );
  }

  _build(BuildContext context) {
    return Obx(() {
      List<NavigationEntity> navList = _recommendPageController.navigationList;
      List<BannerEntity> bannerList = _recommendPageController.bannerList;

      if(navList.isEmpty || navList.isEmpty || _recommendPageController.columnDetailResponse.value.id == null) {
        return normalLoading();
      }else {
        return Column(
          children: <Widget>[

            //轮播图组件
            BannerBar(bannerList: bannerList),

            //nav组件
            SizedBox(
              height: 168,
              child: HomeGridNavigator(navigatorList: navList, gridHeight: 150, gridWidth: _screenWidth),
            ),

            //专栏卡片组件
            _columnBar(_recommendPageController.columnDetailResponse.value)

          ],
        );
      }

    });
  }

  _columnBar(ColumnDetailResponse column) {
    return InkWell(
      onTap: () {
      },

      child: Container(
        height: 100,
        padding: const EdgeInsets.only(top: 10, bottom: 15, right: 5, left: 5),
        child: ClipRRect(
          borderRadius: const BorderRadius.all(Radius.circular(6)),
          child: CachedNetworkImage(
            imageUrl: column.enterImg,
            fit: BoxFit.cover,
            errorWidget: (context, url, error) => const Icon(Icons.error),
            imageBuilder: (context, imageProvider) => Container(
              decoration: BoxDecoration(
                borderRadius: const BorderRadius.all(Radius.circular(6)),
                image: DecorationImage(
                  image: imageProvider,
                  fit: BoxFit.cover,
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }

  @override
  bool get wantKeepAlive => true;
}