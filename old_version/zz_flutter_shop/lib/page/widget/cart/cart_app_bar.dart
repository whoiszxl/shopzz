
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:zz_flutter_shop/controller/cart_page_controller.dart';

cartAppBar(BuildContext context) {

  return AppBar(
    flexibleSpace: const Image(
      image: AssetImage('images/cart_bg.jpg'),
      fit: BoxFit.fitWidth,
    ),
    backgroundColor: Colors.transparent,

    elevation: 0, //阴影的高度,默认在导航栏下有4高度阴影

    //qrcode扫描按钮
    leading: Container(
      padding: const EdgeInsets.only(left: 5),
      child: const Center(child: Text("购物车", style: TextStyle(fontSize: 16))),
    ),

    title: const Text(""),
    actions: <Widget>[

      TextButton(child: const Center(child: Text("编辑", style: TextStyle(fontSize: 13))),
        onPressed: () {
          bool value = Get.find<CartPageController>().isEdit.value;
          Get.find<CartPageController>().isEdit.value = !value;
        }
      ),
      IconButton(icon: const Icon(Icons.lock_clock_outlined),
        onPressed: () {
          //todo 跳转到消息页面
          showToast("消息");
        },
      ),
    ],
  );

}