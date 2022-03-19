import 'dart:collection';

import 'package:get/get.dart';
import 'package:zz_flutter_shop/entity/dto/category_tree_model.dart';
import 'package:zz_flutter_shop/entity/response/cart_detail_response.dart';
import 'package:zz_flutter_shop/entity/response/home_app_index_response.dart';
import 'package:zz_flutter_shop/http/http_manager.dart';
import 'package:zz_flutter_shop/service/api_urls.dart';

///分类页接口服务
class CartApiService extends GetxService {

  ///获取分类树
  Future<CategoryTreeModel> getCategoryTree() async {
    var result = await HttpManager.getInstance().get(url: ApiUrls.categoryTree);
    return CategoryTreeModel.fromJson(result);
  }

  ///获取分类页banner
  Future<HomeAppIndexResponse> getCategoryBanner() async {
    var result = await HttpManager.getInstance().post(url: ApiUrls.homeRecommendAppIndex);
    return HomeAppIndexResponse.fromJson(result);
  }

  ///添加购物车
  Future<bool> cartAdd(int skuId, int quantity) async {
    Map<String, Object> params = HashMap();
    params["skuId"] = skuId;
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
    await HttpManager.getInstance().post(url: ApiUrls.cartClear);
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