
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/model/product_model.dart';
import 'package:flutter_mall/model/product_search_result_model.dart';
import 'package:flutter_mall/navigator/zero_navigator.dart';
import 'package:flutter_mall/util/view_util.dart';
import 'package:flutter_mall/widget/little_tag.dart';

///商品搜索结果列表卡片
class ProductListCard extends StatelessWidget {

  final ProductInfo productInfo;

  const ProductListCard({Key key, this.productInfo}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () {
        ZeroNavigator.getInstance().onJumpTo(RouteStatus.detail, args: {'productModel': ProductModel(productInfo.id)});
      },

      child: SizedBox(
        height: 140,
        child: Card(
          margin: EdgeInsets.only(left: 4, right: 4, bottom: 4),
          child: ClipRRect(
            borderRadius: BorderRadius.circular(5),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [_itemImage(context), _infoText()],
            ),
          ),
        ),
      )
    );
  }


  _itemImage(BuildContext context) {
    return Stack(
      children: [
        cachedImage(productInfo.defaultPic, fit: BoxFit.fill),
        Positioned(
            child: Container(
              padding: EdgeInsets.only(left: 8, right: 8, bottom: 3, top: 5),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  //可以添加一些遮罩样式
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
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              //商品名称
              Text(
                productInfo.name,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
                style: TextStyle(fontSize: 13, color: Colors.black87),
              ),

              //副名称
              Padding(
                padding: EdgeInsets.only(top: 3),
                child: Text(
                  productInfo.subName,
                  maxLines: 1,
                  overflow: TextOverflow.ellipsis,
                  style: TextStyle(fontSize: 10, color: Colors.grey),
                ),
              ),

              //商品价格
              Padding(
                padding: EdgeInsets.only(top: 10),
                child: Row(
                  children: [
                    Text("¥",style: TextStyle(fontSize: 10, color: Colors.redAccent, fontWeight: FontWeight.w500)),
                    Text(
                      productInfo.defaultPrice.toString(),
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                      style: TextStyle(fontSize: 13, color: Colors.redAccent, fontWeight: FontWeight.w500),
                    ),
                    Text(" 起", style: TextStyle(fontSize: 10, color: Colors.redAccent, fontWeight: FontWeight.w500))
                  ],
                ),
              ),

              //小标签
              Padding(
                padding: EdgeInsets.only(top: 5),
                child: Row(
                  children: [
                    Padding(padding: EdgeInsets.only(right: 2), child: Image(image: AssetImage('images/ziying.png'), width: 25, height: 20)),

                    Padding(padding: EdgeInsets.only(right: 2), child:littleTag("满赠", Colors.redAccent)),

                    Padding(padding: EdgeInsets.only(right: 2), child:littleTag("大牌补贴", Colors.redAccent)),

                  ],
                ),
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