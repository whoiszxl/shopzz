import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/controller/order_controller.dart';
import 'package:zz_flutter_shop/page/widget/order/order_card.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';

///发现页面
class OrderListPage extends StatefulWidget {
  const OrderListPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _OrderListPageState();
  }
}

class _OrderListPageState extends State<OrderListPage> with TickerProviderStateMixin{

  final OrderController _orderController = Get.put<OrderController>(OrderController());



  @override
  void initState() {
    super.initState();
    Map<String,String> getParams = Get.parameters;
    _orderController.refreshOrderList(getParams['orderStatus']);
  }

  @override
  void dispose() {
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    final TabController tabController = TabController(length: 5, vsync: this);
    return Obx(() {
      return Scaffold(
        appBar: AppBar(
          backgroundColor: ColorManager.red,
          title: const Text('我的订单'),
          elevation: 0,
          actions: <Widget>[
            Container(
              margin: const EdgeInsets.only(left: 5, right: 5),
              child: const Icon(Icons.message_outlined, color: Colors.white),
            ),
          ],
          centerTitle: true,
          bottom: TabBar(
            physics: const NeverScrollableScrollPhysics(),
            indicatorSize: TabBarIndicatorSize.label,
            indicator: const UnderlineTabIndicator(
              insets: EdgeInsets.only(bottom: 8),
              borderSide: BorderSide(color: Colors.red, width: 3),
            ),
            labelStyle: const TextStyle(fontWeight: FontWeight.bold),
            unselectedLabelStyle: const TextStyle(fontWeight: FontWeight.normal),
            controller: tabController,
            onTap: (index) {
              //订单状态：订单状态，1：待付款，2：已取消，3：待发货，4：待收货，" +
              //            "5：已完成，6：售后中（退货申请待审核），7：交易关闭（退货审核不通过），" +
              //            "8：交易中（待寄送退货商品），9：售后中（退货商品待收货），10：售后中（退货待入库），" +
              //            "11：（1）售后中（退货已入库），12：交易关闭（完成退款）
              switch(index) {
                case 0:
                  _orderController.refreshOrderList("0");break;
                case 1:
                  _orderController.refreshOrderList("1");break;
                case 2:
                  _orderController.refreshOrderList("4");break;
                case 3:
                  _orderController.refreshOrderList("5");break;
                case 4:
                  _orderController.refreshOrderList("2");break;
              }
            },
            tabs: <Tab>[
              ...['全部', '待付款', '待收货', '已完成', '已取消'].map((item) => Tab(text: item)).toList(),
            ],
          ),
        ),
        body: _orderController.records.isNotEmpty ? TabBarView(
          controller: tabController,
          children: <Widget>[
            ...List.generate(5, (index) => ListView.builder(
                itemCount: _orderController.records.length,
                itemBuilder: (context, index) => OrderCard(_orderController.records[index])),
            )
          ],
        ): const Center(child: Text("无订单")),
      );
    });
  }
}