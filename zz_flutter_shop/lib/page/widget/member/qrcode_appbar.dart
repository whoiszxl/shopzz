import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';

///二维码页面appbar
qrcodeAppBar(BuildContext context) {

  return Padding(
    padding: const EdgeInsets.only(left: 10, right: 25),
    child: Row(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [

        //返回上一页
        Padding(
          padding: const EdgeInsets.only(left: 8),
          child: InkWell(
            child: const Icon(Icons.keyboard_return,color: ColorManager.grey),
            onTap: () {
              Navigator.pop(context);
            },
          ),
        ),
      ],
    ),
  );


}