import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';

///会员页面appbar
memberAppBar(BuildContext context) {

  return Padding(
    padding: const EdgeInsets.only(left: 10, right: 25),
    child: Row(
      mainAxisAlignment: MainAxisAlignment.end,
      children: [

        //跳转到我的二维码界面
        Padding(
          padding: const EdgeInsets.only(left: 8),
          child: InkWell(
            child: const Icon(Icons.qr_code_outlined,color: ColorManager.grey),
            onTap: () {
              //跳转到我的二维码页面
              Get.toNamed(Routers.qrcode);
            },
          ),
        ),

        //跳转到设置界面
        Padding(
          padding: const EdgeInsets.only(left: 8),
          child: InkWell(
            child: const Icon(Icons.settings_outlined,color: ColorManager.grey),
            onTap: () {
              Get.toNamed(Routers.setting);
            },
          ),
        ),
      ],
    ),
  );


}