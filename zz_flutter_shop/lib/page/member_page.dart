import 'package:flutter/material.dart';

///设置页面
class MemberPage extends StatefulWidget {
  const MemberPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _MemberPageState();
  }
}

class _MemberPageState extends State<MemberPage> with AutomaticKeepAliveClientMixin,TickerProviderStateMixin {


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
    return  Scaffold(
      body: Stack(
        children: const [
          Text("member")
        ]
      ),
    );
  }

  @override
  bool get wantKeepAlive => true;
}