
import 'package:flutter/material.dart';
import 'package:flutter_mall/model/product_search_result_model.dart';

class ListProductView extends StatelessWidget {

  ProductSearchResultModel productSearchResult;

  ListProductView(this.productSearchResult, {Key key}): super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView(
      children: [
        ...productSearchResult.content.map((item) {
          return Text(item.name);
        })
      ],
    );
  }
}