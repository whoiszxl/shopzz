import 'package:flutter/material.dart';
import 'package:flutter_mall/util/color.dart';

///自定义商品价格Bar
productPriceBar(String productPrice) {
  return Row(
    children: <Widget>[
      Expanded(
        flex: 1,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.end,
          children: <Widget>[
            Text("¥$productPrice",
                style: TextStyle(
                    color: Colors.red,
                    fontSize: 22,
                    fontWeight: FontWeight.w700)),
            SizedBox(
              width: 5,
            ),
            Text("¥${double.parse(productPrice) + 300.99}",
                style: TextStyle(
                    color: Colors.black38,
                    fontSize: 12,
                    decoration: TextDecoration.lineThrough)),
          ],
        ),
      ),
    ],
  );
}

///主标题bar
productTitleBar(String title) {
  return RichText(
    text: TextSpan(
      children: [
        WidgetSpan(
          child: Image(image: AssetImage('images/ziying.png'), width: 25, height: 20),
        ),
        WidgetSpan( child: SizedBox( width: 5, )),

        TextSpan(text: title, style: TextStyle(fontWeight: FontWeight.w600)),
      ],
    ),
  );
}

///副标题bar
productSubTitleBar(String subTitle) {
  return RichText(
    text: TextSpan(
      children: [
        TextSpan(
            text: subTitle,
            style: TextStyle(
                fontWeight: FontWeight.w500, fontSize: 11, color: Colors.grey)),
      ],
    ),
  );
}


///促销bar
productPromotionBar(String logo, String title) {
  return Row(
    mainAxisAlignment: MainAxisAlignment.spaceBetween,
    children: [
      RichText(
        text: TextSpan(children: [
          WidgetSpan(
            child: new Container(
              padding: const EdgeInsets.only(left: 2, right: 2, top: 0, bottom: 1),
              decoration:
                  BoxDecoration(border: Border.all(color: Colors.redAccent)),
              child: Text("满减", style: TextStyle(
                color: Colors.redAccent,
                fontSize: 10,
                fontWeight: FontWeight.w600,
              )),
            ),
          ),
          WidgetSpan(child: SizedBox(width: 3)),
          TextSpan(
              text: title,
              style: TextStyle(
                  fontWeight: FontWeight.w500,
                  color: Colors.grey,
                  fontSize: 11)),
        ]),
      ),


      Row(
        children: [
          Text("查看更多", style: TextStyle(fontSize: 10, color: Colors.grey)),
          Icon(Icons.keyboard_arrow_right, color: Colors.grey)
        ],
      ),
    ],
  );
}


///邮费信息
postageInfoBar(String postageInfo) {
  return RichText(
    text: TextSpan(
      children: [
        TextSpan(
          text: "邮费",
          style: TextStyle(fontSize: 11, color: Colors.grey)
        ),
        WidgetSpan(child: SizedBox(width: 6)),
        TextSpan(
            text: postageInfo,
            style: TextStyle(
                fontWeight: FontWeight.w500, fontSize: 11, color: Colors.black87)),
      ],
    ),
  );
}

///服务保障
serviceGuarantees(String info) {
  return RichText(
    text: TextSpan(
      children: [
        TextSpan(
            text: "保障",
            style: TextStyle(fontSize: 11, color: Colors.grey)
        ),
        WidgetSpan(child: SizedBox(width: 6)),
        TextSpan(
            text: info,
            style: TextStyle(
                fontWeight: FontWeight.w500, fontSize: 11, color: Colors.black87)),
      ],
    ),
  );
}

///地址设计
addressSet(String address, String type, String message) {
  return RichText(
    text: TextSpan(
      children: [
        WidgetSpan(child: Column(
          children: [
            Text("送至",
                style: TextStyle(fontSize: 11, color: Colors.grey)
            ),
            Text("")
          ],
        )),
        
        WidgetSpan(child: SizedBox(width: 6)),

        WidgetSpan(child: Column(
          children: [
            Row(
              children: [
                Text(address, style: TextStyle(fontWeight: FontWeight.w500, fontSize: 11, color: Colors.black87), textAlign: TextAlign.start),
                Text("")
              ],
            ),
            Row(
              children: [
                Text(type, style: TextStyle(fontWeight: FontWeight.w600, fontSize: 11, color: primary)),
                SizedBox(width: 3),
                Text(message, style: TextStyle(fontWeight: FontWeight.w500, fontSize: 11, color: Colors.black87)),
              ],
            )
          ],
        ))
      ],
    ),
  );
}

///评价框
productComment(int commentCount, double niceCommentRate) {
  return Row(
    mainAxisAlignment: MainAxisAlignment.spaceBetween,
    children: [
      RichText(
        text: TextSpan(children: [
          WidgetSpan(
            child: new Container(
              padding: const EdgeInsets.only(left: 2, right: 2, top: 0, bottom: 1),
              child: Text("评价 " + commentCount.toString() + "+ | 好评率 " + niceCommentRate.toString(), style: TextStyle(
                fontSize: 11,
                fontWeight: FontWeight.w500,
              )),
            ),
          ),
          WidgetSpan(child: SizedBox(width: 3)),
        ]),
      ),


      Row(
        children: [
          Text("查看更多", style: TextStyle(fontSize: 11, color: Colors.grey)),
          Icon(Icons.keyboard_arrow_right, color: Colors.grey)
        ],
      ),
    ],
  );
}