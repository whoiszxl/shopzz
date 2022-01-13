import 'package:flutter/material.dart';

Widget littleTag(String text, Color colors) {
  return Container(
      margin: const EdgeInsets.only(left: 2),
      padding: const EdgeInsets.only(left: 2, right: 2, top: 0, bottom: 0),
      decoration: BoxDecoration(border: Border.all(color: colors)),
      child: Text(text, style: TextStyle(
        color: colors,
        fontSize: 9,
        fontWeight: FontWeight.w600,
      )));
}