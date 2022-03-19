import 'package:flutter/services.dart';
import 'package:oktoast/oktoast.dart';

class ClipboardUtil {

  ///判断类型设置值到sp
  static copy(String text) {
    showToast("复制成功");
    Clipboard.setData(ClipboardData(text: text));
  }

}