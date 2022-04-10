import 'package:flutter/material.dart';

class CartTag extends StatelessWidget {
  final Widget text;
  final Alignment alignment;
  final Color color;
  final Color borderColor;
  final double radius;
  final double height;
  final EdgeInsets padding;
  final EdgeInsets margin;
  final double borderWidth;
  CartTag(
      {Key key,
        this.text,
        this.alignment = Alignment.center,
        this.color = const Color(0xFFFFFFFF),
        this.radius = 20,
        this.padding = const EdgeInsets.fromLTRB(10, 0, 10, 0),
        this.margin = const EdgeInsets.all(0),
        this.height = 20,
        this.borderWidth = 1,
        this.borderColor = const Color(0xFF000000)})
      : super(key: key);
  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: alignment,
      padding: padding,
      margin: margin,
      height: height,
      decoration: BoxDecoration(
        color: color,
        borderRadius: BorderRadius.all(
          Radius.circular(radius),
        ),
        border: borderWidth == 0 ? null : Border.all(
          width: borderWidth,
          color: borderColor,
        ),
      ),
      child: text,
    );
  }
}