import 'package:flutter/material.dart';

class DiscoveryPage extends StatefulWidget {
  @override
  _DiscoveryPageState createState() => _DiscoveryPageState();
}

class _DiscoveryPageState extends State<DiscoveryPage> {
  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Container(
        child: Center(
          child: Text('发现页面'),
        ),
      ),
    );
  }
}