
import 'package:flutter/cupertino.dart';

///商品搜索列表页
class ProductListPage extends StatefulWidget {

  String query;

  ProductListPage(this.query);

  @override
  _ProductListPageState createState() => _ProductListPageState();
}


class _ProductListPageState extends State<ProductListPage> {


  @override
  Widget build(BuildContext context) {
    return Container(
      height: 400,
      child: GridView.count(
        crossAxisCount: 2,
        mainAxisSpacing: 10,
        crossAxisSpacing: 10,
        padding: EdgeInsets.all(10),
        children: <Widget>[
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
          Text(widget.query),
        ],
      ),
    );
  }
}