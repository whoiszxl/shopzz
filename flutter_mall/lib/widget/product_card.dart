
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/model/product_model.dart';
import 'package:flutter_mall/model/product_search_result_model.dart';
import 'package:flutter_mall/navigator/zero_navigator.dart';
import 'package:flutter_mall/util/view_util.dart';

///商品搜索结果列表卡片
class ProductCard extends StatelessWidget {

  final ProductInfo productInfo;

  const ProductCard({Key key, this.productInfo}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () {
        ZeroNavigator.getInstance().onJumpTo(RouteStatus.detail, args: {'productModel': ProductModel(productInfo.id)});
      },

      child: SizedBox(
        height: 300,
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
        cachedImage(productInfo.defaultPic, width: size.width / 2 - 10, height: 200),
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
          padding: EdgeInsets.only(top: 4, left: 4, right: 4, bottom: 7),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              //商品名称
              Text(
                productInfo.name,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
                style: TextStyle(fontSize: 12, color: Colors.black87),
              ),

              //商品价格
              Row(
                children: [
                  Text(
                    "¥",
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                    style: TextStyle(fontSize: 10, color: Colors.redAccent, fontWeight: FontWeight.w500),
                  ),
                  Text(
                    productInfo.defaultPrice.toString(),
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                    style: TextStyle(fontSize: 13, color: Colors.redAccent, fontWeight: FontWeight.w500),
                  )
                ],
              ),

              //评价数和好评率
              Text(
                "10000+条评论 99%好评",
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                style: TextStyle(fontSize: 10, color: Colors.grey),
              ),
            ],
          ),
        ));
  }
}