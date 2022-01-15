import 'dart:collection';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:zz_flutter_shop/controller/cart_page_controller.dart';
import 'package:zz_flutter_shop/controller/order_controller.dart';
import 'package:zz_flutter_shop/entity/response/order_list_detail_response.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';

class OrderCard extends StatelessWidget {
  final Order order;
  final double height;
  final double width;

  final CartPageController _cartPageController = Get.find<CartPageController>();

  final OrderController _orderController = Get.find<OrderController>();


  OrderCard(this.order, {Key key, this.width, this.height}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return Container(
      height: height,
      width: width,
      padding: const EdgeInsets.all(10),
      margin: const EdgeInsets.only(top: 10),
      decoration: BoxDecoration(
        color: Theme.of(context).cardColor,
        borderRadius: BorderRadius.circular(10),
      ),
      child: Column(
        children: <Widget>[
          SizedBox(
            height: 40,
            child: Row(
              children: <Widget>[
                Expanded(
                  child: Row(children: const <Widget>[
                    Padding(padding: EdgeInsets.only(right: 2), child: Image(image: AssetImage('images/ziying.png'), width: 25, height: 20)),
                    Text('shopzz自营', style: TextStyle(fontWeight: FontWeight.bold)),
                    Icon(Icons.navigate_next, size: 16)
                  ]),
                ),

                SizedBox(
                  width: 60,
                  child: FlatButton(
                    onPressed: null,
                    child: Text(getByStatus(order.orderStatus), style: const TextStyle(color: ColorManager.red)),
                    padding: const EdgeInsets.only(left: 5, right: 5),
                  ),
                ),

                order.orderStatus == 2 ? Container(
                  height: 25,
                  width: 25,
                  decoration: BoxDecoration(
                      color: const Color(0xFFF6F6F6),
                      borderRadius: BorderRadius.circular(12.5)),
                  child: InkWell(
                    child: const Icon(CupertinoIcons.delete, size: 16),
                    onTap: () {
                      showToast("点击删除");
                    },
                  ),
                ) : const Text(""),
              ],
            ),
          ),
          SizedBox(
            height: 100,
            child: Stack(children: <Widget>[
              SizedBox(
                width: double.infinity,
                child: ListView.builder(
                  itemCount: order.orderItemVOList.length,
                  scrollDirection: Axis.horizontal,
                  itemBuilder: (context, index) {
                    return Container(
                      child: Image.network(order.orderItemVOList[index].skuPic),
                      margin: index == 4
                          ? const EdgeInsets.only(right: 80)
                          : EdgeInsets.zero,
                    );
                  },
                ),
              ),
              Positioned(
                top: 10,
                right: 80,
                child: Container(
                  width: 0,
                  height: 80,
                  decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(20),
                      boxShadow: const <BoxShadow>[
                        BoxShadow(
                            color: Colors.black,
                            spreadRadius: 1,
                            blurRadius: 10)
                      ]),
                ),
              ),
              Positioned(
                right: 0,
                top: 0,
                child: Container(
                  width: 80,
                  height: 100,
                  decoration: const BoxDecoration(
                    gradient: LinearGradient(
                      colors: [
                        Color.fromRGBO(255, 255, 255, 0.98),
                        Color.fromRGBO(255, 255, 255, 1)
                      ],
                      begin: Alignment.centerLeft,
                      end: Alignment.centerRight,
                    ),
                  ),
                  child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        Text(
                          '¥' + order.totalAmount.toString(),
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                        Text('共' + order.orderItemVOList.length.toString() + '件', textAlign: TextAlign.right)
                      ]),
                ),
              ),
            ]),
          ),

          SizedBox(
            height: 20,
            child: Row(children: [Text(order.orderItemVOList[0].skuName)],),
          ),
          SizedBox(
            height: 40,
            child: Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: <Widget>[
                  SizedBox(
                    height: 30,
                    width: 80,
                    child: order.orderStatus == 1 ? _orderButton('去支付', () {
                      _cartPageController.totalAmount.value = order.totalAmount;
                      _orderController.orderId.value = order.id.toString();
                      Get.toNamed(Routers.payCounter);

                    }) :
                    (order.orderStatus == 2 ? _orderButton('删除订单', () {}) :
                    ((order.orderStatus == 3 || order.orderStatus == 4) ? _orderButton('查看物流', () {}) :
                    (order.orderStatus == 5 ? _orderButton('再来一单', () {}) :
                    _orderButton('售后服务', () {
                      showToast("获取售后服务");
                    })))),
                  ),
                ]),
          ),
        ],
      ),
    );
  }

  _orderButton(String title, Function func) {
    return FlatButton(
      onPressed: func,
      child: Text(title, style: const TextStyle(color: Colors.red, fontWeight: FontWeight.bold)),
      color: ColorManager.white,
      padding: EdgeInsets.zero,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20),
        side: const BorderSide(color: ColorManager.red, width: 0.5),
      ),
    );
  }

  String getByStatus(int orderStatus) {
    switch(orderStatus) {
      case 1:
        return "待付款";
      case 2:
        return "已取消";
      case 3:
        return "待发货";
      case 4:
        return "待收货";
      case 5:
        return "已完成";

    }
  }
}