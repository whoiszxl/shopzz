
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/controller/product_page_controller.dart';
import 'package:zz_flutter_shop/events/price_change_event.dart';
import 'package:zz_flutter_shop/page/widget/product/product_grid_card.dart';
import 'package:zz_flutter_shop/page/widget/product/product_list_card.dart';
import 'package:zz_flutter_shop/router/application.dart';

class GridAndListWidget extends StatefulWidget {

  //价格排序  降序:-1 升序:1
  final int priceSort;

  //搜索关键字
  final String keywords;

  //展示类型
  final String type;

  const GridAndListWidget({Key key, @required this.priceSort, @required this.keywords, @required this.type}) : super(key: key);

  @override
  _GridAndListWidgetState createState() => _GridAndListWidgetState();
}

class _GridAndListWidgetState extends State<GridAndListWidget> with AutomaticKeepAliveClientMixin {

  ScrollController scrollController = ScrollController();

  final ProductPageController _productPageController = Get.put<ProductPageController>(ProductPageController());

  final RefreshController _refreshController = RefreshController(initialRefresh: false);

  @override
  void initState() {
    super.initState();
    _productPageController.refreshProductSearchList(widget.keywords, widget.priceSort);
    _refreshController.refreshCompleted();

    Application.eventBus.on<PriceChangeEvent>().listen((event) {
      _productPageController.refreshProductSearchList(widget.keywords, widget.priceSort);
      _refreshController.refreshCompleted();
    });
  }

  @override
  Widget build(BuildContext context) {
    super.build(context);
    return Obx(() {
      return SmartRefresher(
        enablePullDown: true,
        enablePullUp: true,
        onRefresh: _onRefresh,
        onLoading: _onLoading,
        header: const ClassicHeader(
          refreshingText: "刷新中",
          releaseText: "刷新中",
          completeText: "刷新完成",
        ),
        footer: const ClassicFooter(
          loadingText: "加载中",
          failedText: "加载失败",
          idleText: "加载中",
          canLoadingText: "加载中",
          noDataText: "没有更多商品了",
        ),
        controller: _refreshController,
        child: _son(_productPageController.productInfo),
      );
    });
  }


  _son(dataList) {
    return StaggeredGridView.countBuilder(
      shrinkWrap: true,
      physics: const AlwaysScrollableScrollPhysics(),
      padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
      controller: scrollController,
      crossAxisCount: 2,
      itemCount: dataList.length,
      itemBuilder: (BuildContext context, int index) {
        if(widget.type == "grid") {
          return ProductGridCard(productInfo : dataList[index]);
        }else if(widget.type == "list") {
          return ProductListCard(productInfo : dataList[index]);
        }

        return ProductGridCard(productInfo : dataList[index]);
      },

      staggeredTileBuilder: (int index) {
        if(widget.type == "grid") {
          return const StaggeredTile.fit(1);
        }else if(widget.type == "list") {
          return const StaggeredTile.fit(2);
        }
        return const StaggeredTile.fit(1);
      },
    );
  }


  void _onRefresh() async{
    _productPageController.refreshProductSearchList(widget.keywords, widget.priceSort);
    _refreshController.refreshCompleted();
  }

  void _onLoading() async{
    bool flag = await _productPageController.productSearch(widget.keywords, widget.priceSort);
    if(flag) {
      _refreshController.loadComplete();
    }
    _refreshController.loadNoData();


  }


  @override
  bool get wantKeepAlive => true;



}
