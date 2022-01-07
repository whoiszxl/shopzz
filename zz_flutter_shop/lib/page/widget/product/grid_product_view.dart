import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import 'package:zz_flutter_shop/entity/response/search_response.dart';
import 'package:zz_flutter_shop/page/widget/product/product_grid_card.dart';

// ignore: must_be_immutable
class GridProductView extends StatelessWidget {

  SearchResponse searchResponse;

  GridProductView(this.searchResponse, {Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return searchResponse.records.isEmpty
        ? const Center(child: CircularProgressIndicator())
        : Padding(
        padding: const EdgeInsets.only(left: 10, right: 10),
        child: StaggeredGridView.countBuilder(
          shrinkWrap: true,
          physics: const NeverScrollableScrollPhysics(),
          padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
          crossAxisCount: 2,
          itemCount: searchResponse.records.length,
          itemBuilder: (BuildContext context, int index) {
            return ProductGridCard(productInfo: searchResponse.records[index]);
          },
          staggeredTileBuilder: (int index) {
            return const StaggeredTile.fit(1);
          },
        ));
  }

}
