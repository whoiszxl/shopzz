import 'package:flutter/material.dart';
import 'package:flutter_mall/model/product_search_result_model.dart';
import 'package:flutter_mall/widget/product_grid_card.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

class GridProductView extends StatelessWidget {
  ProductSearchResultModel productSearchResult;

  GridProductView(this.productSearchResult, {Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return productSearchResult.content.length == 0
        ? Center(child: CircularProgressIndicator())
        : Padding(
            padding: EdgeInsets.only(left: 10, right: 10),
            child: StaggeredGridView.countBuilder(
              shrinkWrap: true,
              physics: const NeverScrollableScrollPhysics(),
              padding: EdgeInsets.only(top: 10, left: 10, right: 10),
              crossAxisCount: 2,
              itemCount: productSearchResult.content.length,
              itemBuilder: (BuildContext context, int index) {
                return ProductCard(productInfo: productSearchResult.content[index]);
              },
              staggeredTileBuilder: (int index) {
                return StaggeredTile.fit(1);
              },
            ));
  }

}
