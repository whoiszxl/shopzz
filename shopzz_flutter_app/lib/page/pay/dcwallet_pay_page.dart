import 'package:flutter/material.dart';

///数字货币支付页面
class DcwalletPayPage extends StatefulWidget {
  const DcwalletPayPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _DcwalletPayPageState();
  }
}

class _DcwalletPayPageState extends State<DcwalletPayPage>{

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
            Center(
              child: Text("数字货币支付"),
            )
          ]
      ),
    );
  }
}