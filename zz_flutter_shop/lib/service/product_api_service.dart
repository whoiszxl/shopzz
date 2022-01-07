import 'dart:collection';

import 'package:get/get.dart';
import 'package:zz_flutter_shop/entity/dto/category_tree_model.dart';
import 'package:zz_flutter_shop/entity/response/product_detail_response.dart';
import 'package:zz_flutter_shop/entity/response/search_response.dart';
import 'package:zz_flutter_shop/http/http_manager.dart';
import 'package:zz_flutter_shop/service/api_urls.dart';

///商品接口服务
class ProductApiService extends GetxService {

  ///商品数据库搜索
  //{
  //  "keywords": "小米",
  //  "page": 1,
  //  "priceSort": 1,
  //  "size": 10
  //}
  Future<SearchResponse> productSearch(String keywords, int priceSort, int page, int size) async {
    Map<String, Object> params = HashMap();
    params["page"] = page;
    params["size"] = size;
    params["keywords"] = keywords;
    params["priceSort"] = priceSort;
    var result = await HttpManager.getInstance().post(url: ApiUrls.productSearch, data: params);
    return SearchResponse.fromJson(result);
  }

  ///通过商品ID获取商详
  Future<ProductDetailResponse> productDetail(String productId) async {
    Map<String, String> params = HashMap();
    params["productId"] = productId;

    var result = await HttpManager.getInstance().post(url: ApiUrls.productDetail + productId.toString());
    return ProductDetailResponse.fromJson(result);
  }

}