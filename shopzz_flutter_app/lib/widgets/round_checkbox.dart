
import 'package:flutter/material.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';

class RoundCheckbox extends StatefulWidget {
  bool value = false;

  Function(bool) onChanged;
  final Color activeColor;
  final Color checkColor;
  final Color borderColor;
  final Color activeBorderColor;
  final Color backgroundColor;
  final double width;
  final double height;
  final double borderWidth;
  final double elevation;
  final double checkSize;
  final Widget checkIcon;

  RoundCheckbox({
    Key key,
    @required this.value,
    this.onChanged,
    this.activeColor = const Color(0xFFFFFFFF),
    this.checkColor = const Color(0xFF333333),
    this.width = 20,
    this.height = 20,
    this.borderWidth = 1,
    this.borderColor = const Color(0xFF878787),
    this.activeBorderColor = const Color(0xFF333333),
    this.backgroundColor = const Color(0xFFFFFFFF),
    this.checkIcon,
    this.checkSize = 14,
    this.elevation = 1,
  }) : super(key: key);

  @override
  _RoundCheckboxState createState() => _RoundCheckboxState();
}

class _RoundCheckboxState extends State<RoundCheckbox> {
  bool _value = false;
  @override
  void initState() {
    super.initState();
    _value = widget.value;
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
        onTap: () {
          setState(() {
            _value = !_value;
          });
          widget.onChanged(_value);
        },
        child: Container(
          height: widget.height,
          width: widget.width,
          alignment: Alignment.center,
          decoration: BoxDecoration(
            color: _value ? widget.activeColor : widget.backgroundColor,
            shape: BoxShape.circle,
            border: Border.all(
              width: widget.borderWidth,
              color: _value ? widget.activeBorderColor : widget.borderColor,
            ),
            boxShadow: [
              BoxShadow(
                color: ColorManager.white,
                offset: const Offset(0, 0), //阴影xy轴偏移量
                blurRadius: 10.0, //阴影模糊程度
                spreadRadius: widget.elevation, //阴影扩散程度
              )
            ],
          ),
          child: _value
              ? (widget.checkIcon ?? Icon(
            Icons.check,
            color: widget.checkColor,
            size: widget.checkSize,
          ))
              : Container(),
        ));
  }
}