import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';

///带缓存的image
Widget cachedImage(String url, {double width, double height, BoxFit fit}) {
  return CachedNetworkImage(
      height: height,
      width: width,
      fit: fit,
      placeholder: (
          BuildContext context,
          String url,
          ) =>
          Container(color: Colors.grey[200]),
      errorWidget: (
          BuildContext context,
          String url,
          dynamic error,
          ) =>
          Icon(Icons.error),
      imageUrl: url);
}

///黑色线性渐变
blackLinearGradient({bool fromTop = false}) {
  return LinearGradient(
      begin: fromTop ? Alignment.topCenter : Alignment.bottomCenter,
      end: fromTop ? Alignment.bottomCenter : Alignment.topCenter,
      colors: [
        Colors.black54,
        Colors.black45,
        Colors.black38,
        Colors.black26,
        Colors.black12,
        Colors.transparent
      ]);
}

///border线
borderLine(BuildContext context, {bottom: true, top: false}) {
  BorderSide borderSide = BorderSide(width: 0.5, color: Colors.grey[200]);
  return Border(
    bottom: bottom ? borderSide : BorderSide.none,
    top: top ? borderSide : BorderSide.none,
  );
}

///间距
SizedBox hiSpace({double height: 1, double width: 1}) {
  return SizedBox(height: height, width: width);
}
///底部阴影
BoxDecoration bottomBoxShadow() {
  return BoxDecoration(color: Colors.white, boxShadow: [
    BoxShadow(
        color: Colors.grey[100],
        offset: Offset(0, 5), //xy轴偏移
        blurRadius: 5.0, //阴影模糊程度
        spreadRadius: 1 //阴影扩散程度
    )
  ]);
}