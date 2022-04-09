


import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/dto/error_dto.dart';
import 'package:shopzz_flutter_app/entity/response/coupon_response.dart';
import 'package:shopzz_flutter_app/entity/response/spu_detail_response.dart';
import 'package:shopzz_flutter_app/http/api_urls.dart';
import 'package:shopzz_flutter_app/http/http_manager.dart';

///优惠券接口服务
class CouponApiService extends GetxService {


  ///获取全场通用优惠券
  Future<CouponResponse> getCouponAllUnlimited() async {
    var result = await HttpManager.getInstance().get(url: ApiUrls.couponAllUnlimited);
    return CouponResponse.fromJson(result);
  }

  Future<bool> receiveCoupon(String couponId) async {
    var result = await HttpManager.getInstance().post(url: ApiUrls.receiveCoupon + couponId);
    if(result is ErrorDTO) {
      return false;
    }
    return true;
  }
}