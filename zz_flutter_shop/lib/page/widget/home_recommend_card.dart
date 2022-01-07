
import 'package:flutter/material.dart';
import 'package:zz_flutter_shop/entity/response/home_recommend_response.dart';
import 'package:zz_flutter_shop/utils/image_util.dart';

class HomeRecommendCard extends StatelessWidget {

  final RecommendEntity recommendContent;

  const HomeRecommendCard({Key key, this.recommendContent}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return InkWell(
        onTap: () {
          //跳转到商详页面
        },

        child: SizedBox(
          height: 244,
          child: Card(
            margin: const EdgeInsets.only(left: 4, right: 4, bottom: 4),
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
        cachedImage(recommendContent.defaultPic, width: size.width / 2 - 10, height: 180),
        Positioned(
            left: 0,
            right: 0,
            bottom: 0,
            child: Container(
              padding: const EdgeInsets.only(left: 8, right: 8, bottom: 3, top: 5),
              decoration: const BoxDecoration(
                  //渐变效果
                  gradient: LinearGradient(
                      begin: Alignment.bottomCenter,
                      end: Alignment.topCenter,
                      colors: [Colors.black38, Colors.transparent])),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: const [
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
          padding: const EdgeInsets.only(top: 4, left: 8, right: 8, bottom: 10),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              //商品名称
              Text(
                recommendContent.productName,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(fontSize: 10, color: Colors.black87),
              ),

              //商品价格
              Text(
                "售价:" + recommendContent.defaultPrice.toString(),
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(fontSize: 11, color: Colors.redAccent, fontWeight: FontWeight.w500),
              )
            ],
          ),
        ));
  }
}