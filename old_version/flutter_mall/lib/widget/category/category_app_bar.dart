
import 'package:flutter/material.dart';
import 'package:flutter_mall/page/my_search_delegate.dart';
import 'package:flutter_mall/util/toast.dart';

categoryAppBar(BuildContext context) {

  return AppBar(
    backgroundColor: Colors.white70, //导航栏和状态栏的的颜色
    elevation: 0, //阴影的高度,默认在导航栏下有4高度阴影

    //qrcode扫描按钮
    leading: IconButton(
      icon: Icon(Icons.crop_free, color: Color(0xFF787878)),
      onPressed: () {
        showToast("扫一扫");
      },
    ),

    title: Container(
      height: 40,
      decoration: BoxDecoration(
          borderRadius: BorderRadius.all(Radius.circular(50)),
          color: Color(0xFFf8f8f8)),
      child: TextField(
        onTap: () {
          showSearch(context: context, delegate: MySearchDelegate());
        },
        decoration: InputDecoration(
          hintText: '开启搜索吧!',
          hintStyle: TextStyle(
            fontSize: 14,
          ),
          suffixIcon: Icon(Icons.camera_alt),
          prefixIcon: Icon(
            Icons.search,
            color: Color(0xFF444444),
          ),
          border: InputBorder.none,
        ),
      ),
    ),
    actions: <Widget>[
      IconButton(icon: Icon(Icons.message, color: Colors.grey),
        onPressed: () {
          showToast("消息");
        },
      ),
    ],
  );

}