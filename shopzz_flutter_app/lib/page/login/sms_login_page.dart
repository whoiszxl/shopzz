import 'package:flutter/material.dart';

///发现页面
class SmsLoginPage extends StatefulWidget {
  const SmsLoginPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _SmsLoginPageState();
  }
}

class _SmsLoginPageState extends State<SmsLoginPage>{

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
          Text("发现")
        ]
      ),
    );
  }
}