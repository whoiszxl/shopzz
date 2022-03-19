
import 'package:flutter/material.dart';
import 'package:flutter_mall/core/zero_base_page_refresh_state.dart';
import 'package:flutter_mall/dao/product_dao.dart';
import 'package:flutter_mall/model/product_search_result_model.dart';
import 'package:flutter_mall/widget/product_grid_card.dart';
import 'package:flutter_mall/widget/product_list_card.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

class GridAndListWidget extends StatefulWidget {

  //价格排序  降序:-1 升序:1
  final int priceSort;

  //搜索关键字
  final String query;

  //展示类型
  final String type;

  const GridAndListWidget({Key key, @required this.priceSort, @required this.query, @required this.type}) : super(key: key);

  @override
  _GridAndListWidgetState createState() => _GridAndListWidgetState();
}

class _GridAndListWidgetState extends ZeroBasePageRefreshState<ProductSearchResultModel, ProductInfo, GridAndListWidget> {

  @override
  get contentChild =>
      StaggeredGridView.countBuilder(
        shrinkWrap: true,
        physics: const AlwaysScrollableScrollPhysics(),
        padding: EdgeInsets.only(top: 10, left: 10, right: 10),
        controller: scrollController,
        crossAxisCount: 2,
        itemCount: dataList.length,
        itemBuilder: (BuildContext context, int index) {
          if(widget.type == "grid") {
            return ProductCard(productInfo : dataList[index]);
          }else if(widget.type == "list") {
            return ProductListCard(productInfo : dataList[index]);
          }

          return ProductCard(productInfo : dataList[index]);
        },

        staggeredTileBuilder: (int index) {
          if(widget.type == "grid") {
            return StaggeredTile.fit(1);
          }else if(widget.type == "list") {
            return StaggeredTile.fit(2);
          }
          return StaggeredTile.fit(1);
        },
      );


  @override
  Future<ProductSearchResultModel> getData(int pageIndex, int pageSize) async {
    ProductSearchResultModel result = await ProductDao.searchProduct(widget.query, widget.priceSort, pageIndex, pageSize);
    return result;
  }

  @override
  List<ProductInfo> parseList(ProductSearchResultModel result) {
    return result.content;
  }
}
