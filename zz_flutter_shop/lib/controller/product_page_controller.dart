
import 'package:get/get.dart';
import 'package:zz_flutter_shop/entity/response/product_detail_response.dart';
import 'package:zz_flutter_shop/entity/response/search_response.dart';
import 'package:zz_flutter_shop/service/product_api_service.dart';

class ProductPageController extends GetxController {

  List<ProductInfo> productInfo = <ProductInfo>[].obs;

  final productDetailResponse = ProductDetailResponse().obs;

  final productVO = ProductVO().obs;

  int page = 1;
  final int size = 10;


  ///通过productId获取商详
  Future<bool> productDetail(String productId) async {
    var result = await Get.find<ProductApiService>().productDetail(productId);
    productDetailResponse.value = result;
    productVO.value = result.productVO;
    return true;
  }

  ///搜索商品列表
  Future<bool> productSearch(String keywords, int priceSort) async {

    var result = await Get.find<ProductApiService>().productSearch(keywords, priceSort, page, size);

    if(result != null && result.records != null && result.records.isEmpty) {
      return false;
    }

    productInfo.addAll(result.records);
    page++;
    return true;
  }

  void refreshProductSearchList(String keywords, int priceSort) async {
    page = 1;
    productInfo.clear();
    await productSearch(keywords, priceSort);
  }
}