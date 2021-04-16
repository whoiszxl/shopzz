import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/widget/grid_and_list_widget.dart';
import 'package:flutter_mall/widget/home_appbar.dart';
import 'package:flutter_mall/widget/navigation_bar.dart';
import 'package:flutter_mall/widget/product/filter_param_tab.dart';

///商品搜索列表页
class ProductListPage extends StatefulWidget {
  //用户输入的搜索关键词
  final String query;

  ProductListPage({Key key, this.query}) : super(key: key);

  @override
  _ProductListPageState createState() => _ProductListPageState();
}

class _ProductListPageState extends State<ProductListPage>
    with TickerProviderStateMixin {
  //分页参数，默认0
  int page = 0;

  //是否展示网格布局
  bool showGrid = true;

  //默认升序 1升序 -1降序
  int priceSort = 1;


  ValueChanged<int> priceSortCallback;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color.fromRGBO(244, 245, 245, 1.0),
      body: Column(
        children: <Widget>[

          //搜索栏
          NavigationBar(
            height: 50,
            child: homeAppBar(context),
            color: Colors.white,
            statusStyle: StatusStyle.DARK_CONTENT,
          ),

          //过滤栏
          Container(
            height: 48,
            color: Colors.white,
            child: FilterParamTab(
              onTabChangedListener: (position) {},
              clickItemPositionCallback: (position) {
                setState(() {
                  priceSort = -priceSort;
                });


              },
              onTabStyleChangedListener: (position) {
                setState(() {
                  showGrid = !showGrid;
                });
              },
            ),
          ),

          showGrid ? Flexible(child: GridAndListWidget(priceSort: priceSort, query: widget.query, type: "grid"))
              : Flexible(child: GridAndListWidget(priceSort: priceSort, query: widget.query, type: "list"))

        ],
      ),

    );
  }

}
