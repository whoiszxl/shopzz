
import 'package:flutter/material.dart';
import 'package:zz_flutter_shop/events/price_change_event.dart';
import 'package:zz_flutter_shop/page/widget/product/filter_param_tab.dart';
import 'package:zz_flutter_shop/page/widget/product/grid_and_list_widget.dart';
import 'package:zz_flutter_shop/page/widget/product/my_navigation_bar.dart';
import 'package:zz_flutter_shop/page/widget/product/search_appbar.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/application.dart';


///商品搜索列表页
class ProductListPage extends StatefulWidget {

  const ProductListPage({Key key}) : super(key: key);

  @override
  _ProductListPageState createState() => _ProductListPageState();
}

class _ProductListPageState extends State<ProductListPage> with TickerProviderStateMixin {

  //是否展示网格布局
  bool showGrid = true;

  //默认升序 1升序 -1降序
  int priceSort = 1;

  String keywords = "";


  ValueChanged<int> priceSortCallback;

  @override
  void initState() {
    super.initState();

    Map<String,String> getParams = Get.parameters;
    keywords = getParams['keywords'];
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color.fromRGBO(244, 245, 245, 1.0),
      body: Column(
        children: <Widget>[

          //搜索栏
          MyNavigationBar(
            height: 50,
            child: searchAppBar(context, keywords),
            color: ColorManager.white,
            statusStyle: StatusStyle.LIGHT_CONTENT,
          ),

          //过滤栏
          Container(
            height: 48,
            color: ColorManager.white,
            child: FilterParamTab(
              onTabChangedListener: (position) {},
              clickItemPositionCallback: (position) {
                setState(() {
                  priceSort = -priceSort;
                });
                Application.eventBus.fire(PriceChangeEvent());
              },
              onTabStyleChangedListener: (position) {
                setState(() {
                  showGrid = !showGrid;
                });
              },
            ),
          ),

          showGrid ? Flexible(child: GridAndListWidget(priceSort: priceSort, keywords: keywords, type: "grid"))
              : Flexible(child: GridAndListWidget(priceSort: priceSort, keywords: keywords, type: "list"))

        ],
      ),

    );
  }

}
