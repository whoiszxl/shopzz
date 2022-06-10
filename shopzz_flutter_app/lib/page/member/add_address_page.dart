import 'package:flutter/material.dart';

///添加地址
class AddAddressPage extends StatefulWidget {
  const AddAddressPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _AddAddressPageState();
  }
}

class _AddAddressPageState extends State<AddAddressPage>{

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
            Text("添加地址")
          ]
      ),
    );
  }
}