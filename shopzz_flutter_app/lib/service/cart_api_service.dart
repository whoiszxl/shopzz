import 'dart:collection';

import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/response/cart_detail_response.dart';
import 'package:shopzz_flutter_app/http/api_urls.dart';
import 'package:shopzz_flutter_app/http/http_manager.dart';

///分类页接口服务
class CartApiService extends GetxService {


  ///添加购物车
  Future<bool> cartAdd(String skuCode, int quantity) async {
    Map<String, Object> params = HashMap();
    params["skuCode"] = skuCode;
    params["quantity"] = quantity;
    await HttpManager.getInstance().post(url: ApiUrls.cartAdd, data: params);
    return true;
  }

  ///选中或取消选中购物车里的商品
  Future<bool> cartCheck(int skuId, int isChecked) async {
    Map<String, Object> params = HashMap();
    params["skuId"] = skuId;
    params["isChecked"] = isChecked;
    await HttpManager.getInstance().post(url: ApiUrls.cartCheck, data: params);
    return true;
  }

  ///清空当前登录用户的购物车
  Future<bool> cartClear() async {
    await HttpManager.getInstance().post(url: ApiUrls.cartClean);
    return true;
  }

  ///删除购物车里的商品
  Future<bool> cartDelete(List skuIdList) async {
    Map<String, Object> params = HashMap();
    params["skuIdList"] = skuIdList;
    await HttpManager.getInstance().post(url: ApiUrls.cartDelete, data: params);
    return true;
  }

  ///获取当前登录用户的购物车信息
  Future<CartDetailResponse> cartDetail() async {
    var result = await HttpManager.getInstance().get(url: ApiUrls.cartDetail);
    return CartDetailResponse.fromJson(result);
  }

  ///更新购物车SKU数量
  Future<bool> cartUpdateQuantity(int skuId, int quantity) async {
    Map<String, Object> params = HashMap();
    params["skuId"] = skuId;
    params["quantity"] = quantity;
    await HttpManager.getInstance().post(url: ApiUrls.cartUpdateQuantity, data: params);
    return true;
  }

}