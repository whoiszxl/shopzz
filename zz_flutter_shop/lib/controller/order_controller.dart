
import 'dart:async';

import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:zz_flutter_shop/entity/response/order_pay_dc_response.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';
import 'package:zz_flutter_shop/service/order_api_service.dart';

class OrderController extends GetxController {

  final orderId = "".obs;

  final orderPayDcResponse = OrderPayDcResponse().obs;

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

  ///发起数字货币支付
  ///orderId 订单ID
  ///payType 支付方式 [1:支付宝 2:微信 3:数字货币]
  ///dcName 数字货币名称 BTC, ETH, ERC20(SHOPZZ币)
  Future<bool> orderPayDc(String orderId, String dcName) async {
    var result = await Get.find<OrderApiService>().orderPayDc(orderId, dcName);
    if(result != null) {
      orderPayDcResponse.value = result;
      //TODO 跳转到DC收款二维码界面
      //Get.toNamed(Routers.payCounter);
    }else {
      showToast("发起支付失败");
    }
  }

}