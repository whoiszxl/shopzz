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
          const Icon(Icons.error),
      imageUrl: url);
}