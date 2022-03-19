import 'package:flutter/material.dart';
import 'package:flutter_mall/util/color.dart';

///登录按钮
class LoginButton extends StatelessWidget {

  //按钮文字
  final String title;

  //是否开启
  final bool enable;

  //点击事件
  final VoidCallback onPressed;

  const LoginButton(this.title, {Key key, this.enable = true, this.onPressed})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return FractionallySizedBox(
      widthFactor: 1,
      child: MaterialButton(
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(6)),
        height: 45,
        onPressed: enable ? onPressed : null,
        disabledColor: primary[50],
        color: primary,
        child: Text(title, style: TextStyle(color: Colors.white, fontSize: 16)),
      ),
    );
  }
}