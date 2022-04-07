import 'package:flutter/material.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';

///属性选择弹出框下方按钮
class BottomButton extends StatelessWidget {
  final String text;
  final Function handleOk;
  const BottomButton({Key key, this.handleOk, this.text}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () => handleOk(),
      child: Container(
        margin: EdgeInsets.only(bottom: MediaQuery.of(context).padding.bottom, left: 10, right: 10,),
        height: 60,
        decoration: BoxDecoration(
          gradient: const LinearGradient(
            colors: [ColorManager.main, ColorManager.main],
          ),
          borderRadius: BorderRadius.circular(15),
        ),
        child: Center(
          child: Text(text, style: const TextStyle(color: Colors.white, fontSize: 16, fontWeight: FontWeight.w500),
          ),
        ),
      )
    );
  }
}