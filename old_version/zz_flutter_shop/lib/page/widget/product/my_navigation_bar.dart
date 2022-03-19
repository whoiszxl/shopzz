import 'dart:io';

import 'package:flutter/material.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';

enum StatusStyle { LIGHT_CONTENT, DARK_CONTENT }

///可自定义样式的导航栏
class MyNavigationBar extends StatelessWidget {
  final StatusStyle statusStyle;
  final Color color;
  final double height;
  final Widget child;

  const MyNavigationBar(
      {Key key,
        this.statusStyle = StatusStyle.DARK_CONTENT,
        this.color = ColorManager.white,
        this.height = 46,
        this.child})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    bool isWeb = true;

    try{
      var isAndroid = Platform.isAndroid;
      var isIOS = Platform.isIOS;
      if(isAndroid || isIOS) {
        isWeb = false;
      }
    }catch(e) {
      isWeb = true;
    }

    //状态栏高度
    var top = MediaQuery.of(context).padding.top;
    return Container(
      width: MediaQuery.of(context).size.width,
      height: top + height,
      child: child,
      padding: EdgeInsets.only(top: top),
      decoration: BoxDecoration(color: color),
    );
  }

}