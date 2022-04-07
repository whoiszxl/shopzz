


import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/response/spu_detail_response.dart';
import 'package:shopzz_flutter_app/http/api_urls.dart';
import 'package:shopzz_flutter_app/http/http_manager.dart';

///商品接口服务
class ProductApiService extends GetxService {


  ///通过SPU ID获取商详
  Future<SpuDetailResponse> productDetail(String spuId) async {
    var result = await HttpManager.getInstance().post(url: ApiUrls.spuDetail + spuId.toString());
    return SpuDetailResponse.fromJson(result);
  }

}