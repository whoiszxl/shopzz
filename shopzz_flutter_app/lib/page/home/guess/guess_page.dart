import 'package:flutter/material.dart';

///猜你喜欢页面
class GuessPage extends StatefulWidget {
  const GuessPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _GuessPageState();
  }
}

class _GuessPageState extends State<GuessPage>{

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
          Text("猜你喜欢页面")
        ]
      ),
    );
  }
}