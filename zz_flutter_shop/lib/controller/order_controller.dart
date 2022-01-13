
import 'dart:async';

import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';
import 'package:zz_flutter_shop/service/order_api_service.dart';

class OrderController extends GetxController {

  final orderId = "".obs;


  ///订单提交，返回订单号
  Future<bool> orderSubmit(String addressId, {String orderComment = ""}) async {
    var result = await Get.find<OrderApiService>().orderSubmit(addressId, orderComment: orderComment);
    if(result != null && result.isNotEmpty) {
      orderId.value = result;
      //跳转到shopzz收银台
      Get.toNamed(Routers.payCounter);
    }else {
      showToast("订单提交失败");
    }
  }

}