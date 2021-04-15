
import 'package:flutter_mall/http/core/zero_net.dart';
import 'package:flutter_mall/http/request/product/product_detail_request.dart';
import 'package:flutter_mall/http/request/product/product_search_result_request.dart';
import 'package:flutter_mall/model/product_detail_model.dart';
import 'package:flutter_mall/model/product_search_result_model.dart';
import 'package:flutter_mall/util/log_util.dart';

///商品dao数据访问层
class ProductDao {

  ///获取商品详情
  static Future getProductDetail(int productId) async {
    ProductDetailRequest productDetailRequest = new ProductDetailRequest();
    productDetailRequest.pathParams = productId;

    var result = await ZeroNet.getInstance().request(productDetailRequest);
    Log.debug("product detail list: " + result['data'].toString());
    return ProductDetailModel.fromJson(result['data']);
  }


  ///获取商品搜索列表
  static Future searchProduct(String keywords, int priceSort) async {
    ProductSearchResultRequest productSearchResultRequest = new ProductSearchResultRequest();
    productSearchResultRequest.addParam("keywords", keywords).addParam("priceSort", priceSort);

    var result = await ZeroNet.getInstance().request(productSearchResultRequest);
    Log.debug("search product list: " + result['data'].toString());
    return ProductSearchResultModel.fromJson(result['data']);
  }
}