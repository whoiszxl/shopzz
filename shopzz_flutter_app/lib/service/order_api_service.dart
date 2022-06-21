import 'dart:collection';

import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:shopzz_flutter_app/entity/dto/error_dto.dart';
import 'package:shopzz_flutter_app/http/api_urls.dart';
import 'package:shopzz_flutter_app/http/http_manager.dart';

///地址接口服务
class OrderApiService extends GetxService {

  ///新增地址
  Future<bool> orderSubmit(String addressId, String orderComment, String couponId, String finalPrice) async {
    Map<String, Object> params = HashMap();
    params["addressId"] = addressId;
    params["orderComment"] = orderComment;
    params["couponId"] = couponId;
    params["finalPrice"] = finalPrice;
    var result = await HttpManager.getInstance().post(url: ApiUrls.orderSubmit, data: params);

    if(result is ErrorDTO) {
      showToast(result.errorMessage);
      return false;
    }

    return true;
  }
}