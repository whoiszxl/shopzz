import 'package:flutter/material.dart';
import 'package:flutter_mall/util/color.dart';
import 'package:flutter_mall/util/toast.dart';

///登录输入框自定义widget
class NormalInput extends StatefulWidget {

  //标题名称
  final String title;
  //提示
  final String hint;

  //内容改变回调
  final ValueChanged<String> onChanged;

  //对焦回调
  final ValueChanged<bool> focusChanged;

  //行边距
  final bool lineStretch;

  //是否是密码
  final bool obscureText;

  //键盘类型
  final TextInputType keyboardType;

  const NormalInput(this.title, this.hint,
      {Key key,
        this.onChanged,
        this.focusChanged,
        this.lineStretch = false,
        this.obscureText = false,
        this.keyboardType})
      : super(key: key);

  @override
  _NormalInputState createState() => _NormalInputState();

}

class _NormalInputState extends State<NormalInput> {
  final _focusNode = FocusNode();

  @override
  void initState() {
    super.initState();

    _focusNode.addListener(() {
      print("Has focus: ${_focusNode.hasFocus}");
      if(widget.focusChanged != null) {
        widget.focusChanged(_focusNode.hasFocus);
      }
    });
  }

  @override
  void dispose() {
    _focusNode.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Column(

      children: [
        Row(
          children: [
            Container(
              padding: EdgeInsets.only(left: 15),
              width: 100,
              child: Text(
                widget.title,
                style: TextStyle(fontSize: 16),
              ),
            ),
            _input()
          ],
        ),
        
        Padding(
          padding: EdgeInsets.only(left: !widget.lineStretch ? 15 : 0),
          child: Divider(
            height: 1,
            thickness: 0.5,
          ),
        )
      ],

    );
  }

  _input(){
    return Expanded(
        child: TextField(
          focusNode: _focusNode,
          onChanged: widget.onChanged,
          obscureText: widget.obscureText,
          keyboardType: widget.keyboardType,
          autofocus: !widget.obscureText,
          cursorColor: primary,
          style: TextStyle(
            fontSize: 16, color: Colors.black, fontWeight: FontWeight.w300
          ),

          decoration: InputDecoration(
            contentPadding: EdgeInsets.only(left: 20, right: 20),
            border: InputBorder.none,
            hintText: widget.hint ?? '',
            hintStyle: TextStyle(fontSize: 15, color: Colors.grey)
          ),
        )
    );
  }

  _btn(){
    return Expanded(
        child: MaterialButton(
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
          height: 45,
          onPressed: () {
            showToast("点击咯");
          },
          disabledColor: primary[50],
          color: primary,
          child: Text('点击发送', style: TextStyle(color: Colors.white, fontSize: 16)),
        )
    );
  }
}