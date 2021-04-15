import 'package:flutter/material.dart';
import 'package:flutter_mall/model/product_search_result_model.dart';

class GridProductView extends StatelessWidget {
  ProductSearchResultModel productSearchResult;

  GridProductView(this.productSearchResult, {Key key}): super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
        padding: EdgeInsets.only(top: 100),
        child: ListView(
          children: [
            ...productSearchResult.content.map((item) {
              return Text(item.name);
            })
          ],
        )
    );
  }
}
