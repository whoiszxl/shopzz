import 'package:flutter/material.dart';
import 'package:zz_flutter_shop/entity/response/search_response.dart';
import 'package:zz_flutter_shop/page/widget/product/little_tag.dart';
import 'package:zz_flutter_shop/utils/image_util.dart';

///商品搜索结果列表卡片
class ProductListCard extends StatelessWidget {

  final ProductInfo productInfo;

  const ProductListCard({Key key, this.productInfo}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return InkWell(
        onTap: () {
          //TODO 跳转
        },

        child: SizedBox(
          height: 140,
          child: Card(
            margin: const EdgeInsets.only(left: 4, right: 4, bottom: 4),
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
              padding: const EdgeInsets.only(left: 8, right: 8, bottom: 3, top: 5),
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
          padding: const EdgeInsets.only(top: 4, left: 4, right: 4, bottom: 7),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              //商品名称
              Text(
                productInfo.name,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(fontSize: 13, color: Colors.black87),
              ),

              //副名称
              Padding(
                padding: const EdgeInsets.only(top: 3),
                child: Text(
                  productInfo.subName,
                  maxLines: 1,
                  overflow: TextOverflow.ellipsis,
                  style: const TextStyle(fontSize: 10, color: Colors.grey),
                ),
              ),

              //商品价格
              Padding(
                padding: const EdgeInsets.only(top: 10),
                child: Row(
                  children: [
                    const Text("¥",style: TextStyle(fontSize: 10, color: Colors.redAccent, fontWeight: FontWeight.w500)),
                    Text(
                      productInfo.defaultPrice.toString(),
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                      style: const TextStyle(fontSize: 13, color: Colors.redAccent, fontWeight: FontWeight.w500),
                    ),
                    const Text(" 起", style: TextStyle(fontSize: 10, color: Colors.redAccent, fontWeight: FontWeight.w500))
                  ],
                ),
              ),

              //小标签
              Padding(
                padding: const EdgeInsets.only(top: 5),
                child: Row(
                  children: [
                    const Padding(padding: EdgeInsets.only(right: 2), child: Image(image: AssetImage('images/ziying.png'), width: 25, height: 20)),

                    Padding(padding: const EdgeInsets.only(right: 2), child:littleTag("满赠", Colors.redAccent)),

                    Padding(padding: const EdgeInsets.only(right: 2), child:littleTag("大牌补贴", Colors.redAccent)),

                  ],
                ),
              ),

              //评价数和好评率
              const Text(
                "10000+条评论 99%好评",
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(fontSize: 10, color: Colors.grey),
              ),
            ],
          ),
        ));
  }
}