
import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/response/spu_detail_response.dart';
import 'package:shopzz_flutter_app/service/product_api_service.dart';

class ProductPageController extends GetxController {

  final spuDetailResponse = SpuDetailResponse().obs;

  List<String> selectedSkuCodeList = RxList.generate(10, (i) => "-1");

  List<int> selectedIdList = RxList.generate(10, (i) => -1);

  final isAllChecked = false.obs;

  final skuCode = "".obs;

  ///通过productId获取商详
  Future<bool> productDetail(String spuId) async {
    var result = await Get.find<ProductApiService>().productDetail(spuId);
    spuDetailResponse.value = result;
    return true;
  }

}