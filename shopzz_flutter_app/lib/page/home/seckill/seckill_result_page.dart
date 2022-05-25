import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'dart:io';

import 'package:shopzz_flutter_app/controller/seckill_page_controller.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/router/router_manager.dart';
import 'package:shopzz_flutter_app/widgets/base_scaffold.dart';
import 'package:shopzz_flutter_app/widgets/my_app_bar.dart';

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
  final RefreshController _refreshController = RefreshController(initialRefresh: false);
  final ScrollController _scrollController = ScrollController();

  @override
  void initState() {
    super.initState();
    //获取从上一个页面传递过来的spuId
    Map<String, String> getParams = Get.parameters;
    seckillItemId = getParams['seckillItemId'];
    taskId = getParams['taskId'];
    _seckillPageController.seckillOrderResult(seckillItemId, taskId);
  }

  @override
  void dispose() {
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    return SmartRefresher(
      scrollController: _scrollController,
      controller: _refreshController,
      enablePullUp: true,
      enablePullDown: true,
      onRefresh: () {
        _seckillPageController.seckillOrderResult(seckillItemId, taskId);
        _refreshController.refreshCompleted();
      },
      onLoading: () {
        _seckillPageController.seckillOrderResult(seckillItemId, taskId);
        _refreshController.loadComplete();
      },
      child: _build(context),
    );

  }

  _build(context) {
    return BaseScaffold(
      appBar: MyAppBar(
        title: const Text("秒杀结果", style: TextStyle(fontSize: 16)),
        leadingType: AppBarBackType.Back,
        backgroundColor: ColorManager.main,
        elevation: 0,
      ),

      body: Stack(

          children: [
            Obx(() {
              return Container(
                  color: ColorManager.fieldBg,
                  padding: const EdgeInsets.only(top: 100),
                  child: Column(
                    children: <Widget>[
                      Padding(
                          padding: const EdgeInsets.only(bottom: 1),
                          child: Text(_seckillPageController.currentSeckillOrderId.value == "0" ? "很遗憾没有秒杀到" : "恭喜你秒杀到啦\n订单号:" + _seckillPageController.currentSeckillOrderId.value, style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold))
                      ),

                      Center(
                        child: SizedBox(
                          width: MediaQuery.of(context).size.width - 50,
                          height: 400,
                          child: const Padding(padding: EdgeInsets.only(right: 2), child: Image(image: AssetImage('assets/images/seckill_result.png'), width: 25, height: 20)),
                        ),),

                      _seckillPageController.currentSeckillOrderId.value == "0" ? null : ElevatedButton(
                        onPressed: () {
                          _seckillPageController.seckillOrderResult(seckillItemId, taskId);
                        },
                        child: const Text("点击去支付"),
                        style: ElevatedButton.styleFrom(
                          textStyle: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                          minimumSize: const Size(250, 50),
                          primary: ColorManager.main,
                          onSurface: Colors.blue,
                          shadowColor: Colors.grey,
                          elevation: 5,
                          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(5)),
                          tapTargetSize: MaterialTapTargetSize.padded,
                        ),
                      )
                    ],
                  )
              );

            })

          ]
      ),
    );
  }
}