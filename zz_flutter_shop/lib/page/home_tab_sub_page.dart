import 'package:flutter/material.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';

///home主页 订阅tab页面
class HomeTabSubPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _HomeTabSubPageState();
  }
}

class _HomeTabSubPageState extends State<HomeTabSubPage> with AutomaticKeepAliveClientMixin{

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    super.build(context);
    return Container(color: ColorManager.grey);
  }

  @override
  bool get wantKeepAlive => true;
}