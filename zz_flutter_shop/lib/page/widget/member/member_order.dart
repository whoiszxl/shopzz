import 'package:flutter/material.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';

///会员订单组件
class MemberOrder extends StatelessWidget {

  MemberOrder({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    return Container(
      height: 80,
      margin: const EdgeInsets.only(top: 8),
      padding: const EdgeInsets.only(left: 10, right: 10),
      decoration: const BoxDecoration(color: Colors.white, borderRadius: const BorderRadius.all(const Radius.circular(10))),

      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        crossAxisAlignment: CrossAxisAlignment.center,

        children: <Widget>[

          orderTab(Icons.inbox_outlined, "待付款", Colors.black, () {

          }),
          orderTab(Icons.topic_outlined, "待收货", Colors.black, () {

          }),
          orderTab(Icons.comment_bank_outlined, "待评价", Colors.black, () {

          }),
          orderTab(Icons.swap_horizontal_circle_outlined, "退换/售后", Colors.black, () {

          }),
          orderTab(Icons.collections_bookmark_outlined, "我的订单", ColorManager.orange, () {

          }),

        ],
      ),
    );

  }


  orderTab(IconData tabIcon, String tabName, Color color, Function function) {
    return Expanded(
      flex: 1,
      child: GestureDetector(
        onTap: function,
        child: Container(
          height: 60,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: <Widget>[
              //ICON
              Icon(tabIcon, size: 30, color: color),
              //文字

              Text(
                tabName,
                style: TextStyle(color: color, fontSize: 10, fontWeight: FontWeight.normal),
              )

            ],
          ),
        ),
      ),
    );

  }

}