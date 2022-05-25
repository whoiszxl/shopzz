import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'dart:io';

import 'package:shopzz_flutter_app/controller/seckill_page_controller.dart';

///秒杀结果页面
class SeckillResultPage extends StatefulWidget {
  const SeckillResultPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _SeckillResultPageState();
  }
}

class _SeckillResultPageState extends State<SeckillResultPage>{

  String seckillItemId;
  String taskId;

  final SeckillPageController _seckillPageController = Get.put(SeckillPageController());


  @override
  void initState() {
    super.initState();
    //获取从上一个页面传递过来的spuId
    Map<String, String> getParams = Get.parameters;
    seckillItemId = getParams['seckillItemId'];
    taskId = getParams['taskId'];



  }

  @override
  void dispose() {
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {



    return  Scaffold(
      body: Stack(
        children: [
          Text("秒杀结果页面" +seckillItemId.toString() + " -- " + taskId),

          InkWell(
            onTap: () {
              _seckillPageController.seckillOrderResult(seckillItemId, taskId);

            },
            child: Center(
              child: Text("获取结果"),
            ),
          )
        ]
      ),
    );
  }
}