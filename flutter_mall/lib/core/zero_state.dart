
import 'package:flutter/cupertino.dart';
import 'package:flutter_mall/util/log_util.dart';

abstract class ZeroState<T extends StatefulWidget> extends State<T> {

  @override
  void setState(fn) {
    if(mounted) {
      super.setState(fn);
    }else {
      Log.info("页面已销毁，本次setState不执行：${toString()}");
    }

  }
}