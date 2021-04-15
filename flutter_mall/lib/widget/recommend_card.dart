
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/model/product_model.dart';
import 'package:flutter_mall/model/recommend_model.dart';
import 'package:flutter_mall/navigator/zero_navigator.dart';
import 'package:flutter_mall/util/toast.dart';
import 'package:flutter_mall/util/view_util.dart';

class RecommendCard extends StatelessWidget {

  final Content recommendContent;

  const RecommendCard({Key key, this.recommendContent}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () {
        ZeroNavigator.getInstance().onJumpTo(RouteStatus.detail, args: {'productModel': ProductModel(recommendContent.productId)});
      },

      child: SizedBox(
        height: 274,
        child: Card(
          margin: EdgeInsets.only(left: 4, right: 4, bottom: 4),
          child: ClipRRect(
            borderRadius: BorderRadius.circular(5),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [_itemImage(context), _infoText()],
            ),
          ),
        ),
      )
    );
  }


  _itemImage(BuildContext context) {
    final size = MediaQuery.of(context).size;
    return Stack(
      children: [
        cachedImage(recommendContent.defaultPic, width: size.width / 2 - 10, height: 200),
        Positioned(
            left: 0,
            right: 0,
            bottom: 0,
            child: Container(
              padding: EdgeInsets.only(left: 8, right: 8, bottom: 3, top: 5),
              decoration: BoxDecoration(
                //渐变
                  gradient: LinearGradient(
                      begin: Alignment.bottomCenter,
                      end: Alignment.topCenter,
                      colors: [Colors.black38, Colors.transparent])),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text('')
                ],
              ),
            ))
      ],
    );
  }


  _infoText() {
    return Expanded(
        child: Container(
          padding: EdgeInsets.only(top: 4, left: 8, right: 8, bottom: 10),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              //商品名称
              Text(
                recommendContent.productName,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
                style: TextStyle(fontSize: 12, color: Colors.black87),
              ),

              //商品价格
              Text(
                "售价:" + recommendContent.defaultPrice.toString(),
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                style: TextStyle(fontSize: 13, color: Colors.redAccent, fontWeight: FontWeight.w500),
              )
            ],
          ),
        ));
  }
}