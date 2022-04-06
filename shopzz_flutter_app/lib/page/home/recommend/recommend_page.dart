
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:shopzz_flutter_app/controller/recommend_page_controller.dart';
import 'package:shopzz_flutter_app/entity/response/banner_response.dart';
import 'package:shopzz_flutter_app/entity/response/column_detail_response.dart';
import 'package:shopzz_flutter_app/entity/response/home_recommend_response.dart';
import 'package:shopzz_flutter_app/page/home/widgets/banner_bar.dart';
import 'package:shopzz_flutter_app/page/home/widgets/home_grid_navigator.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/utils/image_util.dart';
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
    _recommendPageController.getHomeRecommendList(_refreshController);
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
      onRefresh: () {
        _recommendPageController.refreshBannerResponse(_refreshController);
        _recommendPageController.getFirstColumnDetail(_refreshController);
        _recommendPageController.refreshHomeRecommendList(_refreshController);
      },
      onLoading: () {
        _recommendPageController.getHomeRecommendList(_refreshController);
      },
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
        return SingleChildScrollView(
          child: Column(
            children: <Widget>[

              //轮播图组件
              BannerBar(bannerList: bannerList),

              //nav组件
              SizedBox(
                height: 168,
                child: HomeGridNavigator(navigatorList: navList, gridHeight: 150, gridWidth: _screenWidth),
              ),

              //专栏广告栏组件
              _columnBar(_recommendPageController.columnDetailResponse.value, () {
                showToast("点击了");
              }),

              //专栏一
              Padding(padding: const EdgeInsets.only(right: 5, left: 5), child: _columnOne(_recommendPageController.columnDetailResponse.value)),


              //推荐商品列表，暂用专栏SPU代替
              _recommendSpuList(_recommendPageController.recommendList),

            ],
          ),
        );
      }

    });
  }

  ///推荐SPU列表
  _recommendSpuList(List<HomeRecommendEntity> recommendEntityList) {
    return StaggeredGridView.countBuilder(
      shrinkWrap: true,
      physics: const NeverScrollableScrollPhysics (),
      padding: const EdgeInsets.only(top: 10, left: 4, right: 4),
      crossAxisCount: 2,
      itemCount: recommendEntityList.length,
      itemBuilder: (BuildContext context, int index) {
        return InkWell(
          onTap: () {
            showToast("点击了" + recommendEntityList[index].name);
          },

          child: _homeRecommendCard(recommendEntityList[index]),
        );
      },

      staggeredTileBuilder: (int index) {
        return const StaggeredTile.fit(1);
      },
    );
  }


  ///首页推荐商品列表卡片组件
  _homeRecommendCard(HomeRecommendEntity recommendEntity) {
    final size = MediaQuery.of(context).size;
    return SizedBox(
      height: 244,
      child: Card(
        margin: const EdgeInsets.only(left: 4, right: 4, bottom: 4),
        child: ClipRRect(
          borderRadius: BorderRadius.circular(5),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [_itemImage(size, recommendEntity.defaultPic), _infoText(recommendEntity.name, recommendEntity.defaultPrice.toStringAsFixed(2).toString())],
          ),
        ),
      ),
    );
  }

  //专栏ID为1的SPU列表
  _columnOne(ColumnDetailResponse columnDetail) {
    return Column(
      children: <Widget>[
        Container(
          color: ColorManager.white,
          padding: const EdgeInsets.only(top: 10, bottom: 10),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: <Widget>[
              Padding(padding: const EdgeInsets.only(left: 5), child: Text(columnDetail.name, style: const TextStyle(color: ColorManager.black, fontSize: 14)),),
              const Padding(padding: EdgeInsets.only(right: 5), child: Text("查看更多>", style: TextStyle(color: ColorManager.fontGrey, fontSize: 11)),),
            ],
          ),
        ),
        Container(
          color: ColorManager.white,
          child: SingleChildScrollView(
            scrollDirection: Axis.horizontal,
            child: Row(
              children: columnDetail.spuList.map((ColumnSpuEntity spu) {
                return InkWell(
                  onTap: () {
                    showToast("点击了" + spu.name);
                  },
                  child: _columnCard(spu),
                );
              }).toList(),
            ),
          ),
        )
      ],
    );
  }



  ///专栏item卡片
  Widget _columnCard(ColumnSpuEntity hotItem) {

    return Card(
      child: SizedBox(
        width: 110,
        height: 160,
        child: Column(
          children: <Widget>[

            //SPU图片
            CachedNetworkImage(imageUrl: hotItem.defaultPic, fit: BoxFit.cover),

            //SPU名称
            Container(
              alignment: Alignment.centerLeft,
              padding: const EdgeInsets.only(right: 3, left: 5, top: 3),
              child: Text(
                hotItem.name,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(color: ColorManager.black, fontSize: 10),
              ),
            ),

            //SPU价格
            Padding(
              padding: const EdgeInsets.only(right: 4, left: 4, top: 4),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  Text.rich(
                    TextSpan(
                        text: '¥',
                        children: [TextSpan(text: (hotItem.defaultPrice).toStringAsFixed(2).toString())],
                        style: const TextStyle(color: ColorManager.main, fontSize: 12)),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  ///专栏广告栏
  _columnBar(ColumnDetailResponse column, onTap) {
    return InkWell(
      onTap: onTap,

      child: Container(
        height: 100,
        padding: const EdgeInsets.only(top: 0, bottom: 5, right: 5, left: 5),
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

  _itemImage(Size size, String url) {
    return Stack(
      children: [
        cachedImage(url, width: size.width / 2 - 10, height: 180),
        Positioned(
            left: 0,
            right: 0,
            bottom: 0,
            child: Container(
              padding: const EdgeInsets.only(left: 8, right: 8, bottom: 3, top: 5),
              decoration: const BoxDecoration(
                //渐变效果
                  gradient: LinearGradient(
                      begin: Alignment.bottomCenter,
                      end: Alignment.topCenter,
                      colors: [Colors.black38, Colors.transparent])),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: const [
                  Text('')
                ],
              ),
            ))
      ],
    );
  }


  _infoText(String name, String price) {
    return Expanded(
        child: Container(
          padding: const EdgeInsets.only(top: 4, left: 8, right: 8, bottom: 10),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              //商品名称
              Text(
                name,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(fontSize: 10, color: Colors.black87),
              ),

              //商品价格
              Text(
                "售价:" + price,
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(fontSize: 10, color: Colors.redAccent, fontWeight: FontWeight.w500),
              )
            ],
          ),
        ));
  }
  @override
  bool get wantKeepAlive => true;
}