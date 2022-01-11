import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';

///设置页面appbar
settingsAppBar(BuildContext context) {

  return Padding(
    padding: const EdgeInsets.only(left: 10, right: 25),
    child: Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [

        Padding(
          padding: const EdgeInsets.only(left: 8),
          child: InkWell(
            child: const Icon(Icons.keyboard_return,color: ColorManager.grey),
            onTap: () {
              Navigator.pop(context);
            },
          ),
        ),

        const Padding(
          padding: EdgeInsets.only(left: 8),
          child: Text("设置", style: TextStyle(fontSize: 20),)
        ),

        const Text(""),
      ],
    ),
  );


}