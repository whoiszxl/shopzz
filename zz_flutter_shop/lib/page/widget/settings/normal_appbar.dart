import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';

///普通appbar
normalAppBar(BuildContext context, String title, {Color titleColor = ColorManager.black}) {

  return Padding(
    padding: const EdgeInsets.only(left: 10, right: 25),
    child: Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [

        Padding(
          padding: const EdgeInsets.only(left: 8),
          child: InkWell(
            child: const Icon(Icons.navigate_before,color: ColorManager.grey),
            onTap: () {
              Navigator.pop(context);
            },
          ),
        ),

        Padding(
          padding: const EdgeInsets.only(left: 8),
          child: Text(title, style: TextStyle(fontSize: 20, color: titleColor))
        ),

        const Text(""),
      ],
    ),
  );


}