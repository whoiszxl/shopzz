import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/dao/product_dao.dart';
import 'package:flutter_mall/model/product_search_result_model.dart';
import 'package:flutter_mall/util/toast.dart';
import 'package:flutter_mall/widget/home_appbar.dart';
import 'package:flutter_mall/widget/navigation_bar.dart';
import 'package:flutter_mall/widget/product/filter_param_tab.dart';
import 'package:flutter_mall/widget/product/grid_product_view.dart';
import 'package:flutter_mall/widget/product/list_product_view.dart';

///商品搜索列表页
class ProductListPage extends StatefulWidget {
  //用户输入的搜索关键词
  final String query;

  ProductListPage({Key key, this.query}) : super(key: key);

  @override
  _ProductListPageState createState() => _ProductListPageState();
}

class _ProductListPageState extends State<ProductListPage>
    with AutomaticKeepAliveClientMixin, SingleTickerProviderStateMixin {
  //分页参数，默认0
  int page = 0;

  //默认升序 1升序 -1降序
  int priceSort = 1;

  //是否展示网格布局
  bool showGrid = true;

  @override
  bool get wantKeepAlive => true;




  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    super.build(context);
    return Scaffold(
      backgroundColor: Color.fromRGBO(244, 245, 245, 1.0),
      body: FutureBuilder(
        future: _loadData(),
        builder: (context, snapshot) {
          if(snapshot.hasData) {
            //获取接口数据
            List<dynamic> results = snapshot.data;
            ProductSearchResultModel productSearchResult = results[0];

            if(productSearchResult.content.length == 0) {
              return Center(child: Text("木有搜到商品"));
            }

            showToast(productSearchResult?.content[0]?.name);
            return Container(
              color: Colors.white,
              child: Column(
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
                      onTabStyleChangedListener: (position) {
                        setState(() {
                          showGrid = !showGrid;
                        });
                      },
                    ),
                  ),

                  Expanded(
                    child: Container(
                      child: NotificationListener(
                        onNotification: _onScroll,
                        child: showGrid ? GridProductView(productSearchResult) : ListProductView(productSearchResult),
                      ),
                    ),
                  )
                ],
              ),
            );
          }else {
            return Center(child: Text("数据加载中"));
          }
        },
      )
    );
  }

  bool _onScroll(Notification notification) {
  }

  ///加载数据
  Future _loadData() async {
    return Future.wait([ProductDao.searchProduct(widget.query, priceSort)]);
  }
}
