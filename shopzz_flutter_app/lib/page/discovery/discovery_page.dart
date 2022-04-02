import 'package:flutter/material.dart';

///发现页面
class DiscoveryPage extends StatefulWidget {
  const DiscoveryPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _DiscoveryPageState();
  }
}

class _DiscoveryPageState extends State<DiscoveryPage>{

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