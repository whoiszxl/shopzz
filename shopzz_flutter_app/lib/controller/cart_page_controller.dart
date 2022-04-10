import 'dart:async';

import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/response/cart_detail_response.dart';
import 'package:shopzz_flutter_app/service/cart_api_service.dart';

class CartPageController extends GetxController {

  final CartApiService cartApiService = Get.put<CartApiService>(CartApiService());

  final cartDetailResponse = CartDetailResponse().obs;

  final isEdit = false.obs;

  final isAllChecked = false.obs;

  final totalAmount = 0.0.obs;

  final totalQuantity = 0.obs;

  List<CartItemEntity> currentCheckedCartItemList = <CartItemEntity>[].obs;

  ///添加购物车
  Future<bool> cartAdd(String skuCode, int quantity) async {
    return await cartApiService.cartAdd(skuCode, quantity);
  }

  ///选中或取消选中购物车里的商品
  Future<bool> cartCheck(int skuId, int isChecked) async {
    return await cartApiService.cartCheck(skuId, isChecked);
  }

  ///清空当前登录用户的购物车
  Future<bool> cartClear() async {
    return await cartApiService.cartClear();
  }

  ///删除购物车里的商品
  Future<bool> cartDelete(List skuIdList) async {
    return await cartApiService.cartDelete(skuIdList);
  }

  ///获取当前登录用户的购物车信息
  Future<bool> cartDetail() async {
    var cartDetail = await cartApiService.cartDetail();
    cartDetailResponse.value = cartDetail;
    totalAmount.value = cartDetail.totalAmount;
    isEdit.value = false;
    totalQuantity.value = cartDetail.skuCount;
    return true;
  }

  ///更新购物车SKU数量
  Future<bool> cartUpdateQuantity(int skuId, int quantity) async {
    return await cartApiService.cartUpdateQuantity(skuId, quantity);
  }
}