
import 'package:flutter_mall/http/core/zero_net.dart';
import 'package:flutter_mall/http/request/product/product_detail_request.dart';
import 'package:flutter_mall/model/product_detail_model.dart';
import 'package:flutter_mall/util/log_util.dart';

///商品dao数据访问层
class ProductDao {

  ///获取商品详情
  static Future getProductDetail(int productId) async {
    ProductDetailRequest productDetailRequest = new ProductDetailRequest();
    productDetailRequest.pathParams = productId;

    var result = await ZeroNet.getInstance().request(productDetailRequest);
    Log.debug("banner list" + result['data'].toString());
    return ProductDetailModel.fromJson(result['data']);
  }
}