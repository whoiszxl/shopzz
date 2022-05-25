import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:shopzz_flutter_app/controller/seckill_page_controller.dart';
import 'package:shopzz_flutter_app/entity/response/banner_response.dart';
import 'package:shopzz_flutter_app/entity/response/seckill_item_list_response.dart';
import 'package:shopzz_flutter_app/page/home/seckill/widgets/seckill_list_card.dart';
import 'package:shopzz_flutter_app/page/home/widgets/banner_bar.dart';
import 'package:shopzz_flutter_app/utils/loading_util.dart';

///秒杀页面
class SeckillPage extends StatefulWidget {
  const SeckillPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _SeckillPageState();
  }
}

class _SeckillPageState extends State<SeckillPage> with TickerProviderStateMixin, AutomaticKeepAliveClientMixin {

  final PageController _pageController = PageController(keepPage: true);
  final RefreshController _refreshController = RefreshController(initialRefresh: false);
  final ScrollController _scrollController = ScrollController();
  final SeckillPageController _seckillPageController = Get.put(SeckillPageController());

  @override
  void initState() {
    _seckillPageController.getSeckillList(_refreshController);
    _seckillPageController.getSeckillItemList(_refreshController);
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
    _pageController.dispose();
    _refreshController.dispose();
    _scrollController.dispose();
  }
  @override
  Widget build(BuildContext context) {
    super.build(context);
    return SmartRefresher(
      scrollController: _scrollController,
      controller: _refreshController,
      enablePullUp: true,
      enablePullDown: true,
      onRefresh: () {
        _seckillPageController.getSeckillList(_refreshController);
      },
      onLoading: () {
        _seckillPageController.getSeckillList(_refreshController);
      },
      child: _build(context),
    );
  }

  _build(BuildContext context) {
    return Obx(() {
      List<BannerEntity> bannerList = _seckillPageController.bannerList;

      if(bannerList.isEmpty) {
        return normalLoading();
      }else {
        return SingleChildScrollView(
          child: Column(
            children: <Widget>[

              //轮播图组件
              BannerBar(bannerList: bannerList),

              _seckillItemList(_seckillPageController.seckillItemList)

            ],
          ),
        );
      }

    });
  }

  _seckillItemList(List<SeckillItemEntity> seckillItemList) {
    return StaggeredGridView.countBuilder(
      shrinkWrap: true,
      padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
      controller: _scrollController,
      crossAxisCount: 1,
      itemCount: seckillItemList.length,
      itemBuilder: (BuildContext context, int index) {
        return SeckillListCard(seckillItemEntity: seckillItemList[index]);
      },

      staggeredTileBuilder: (int index) {
        return const StaggeredTile.fit(2);
      },
    );

  }


  @override
  bool get wantKeepAlive => true;

}