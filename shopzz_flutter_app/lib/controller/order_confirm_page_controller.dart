import 'dart:async';

import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/response/cart_detail_response.dart';
import 'package:shopzz_flutter_app/service/cart_api_service.dart';
import 'package:shopzz_flutter_app/service/order_api_service.dart';

class PayPageController extends GetxController {

  final currentAddressId = "".obs;

  final currentCouponId = "".obs;

  final isEdit = false.obs;

  final loadingFlag = true.obs;

  final submitFlag = false.obs;

  ///添加购物车
  Future<bool> orderSubmit(String addressId, String orderComment, String couponId, String finalPrice) async {
    loadingFlag.value = true;
    bool submitFlag = await Get.find<OrderApiService>().orderSubmit(addressId, orderComment, couponId, finalPrice);
    loadingFlag.value = false;
    this.submitFlag.value = submitFlag;
  }
}