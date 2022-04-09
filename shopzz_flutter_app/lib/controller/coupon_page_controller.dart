
import 'dart:async';

import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:shopzz_flutter_app/entity/response/coupon_response.dart';
import 'package:shopzz_flutter_app/service/coupon_api_service.dart';

class CouponPageController extends GetxController {

  List<CouponEntity> couponList = <CouponEntity>[].obs;


  ///获取全场无分类限制优惠券
  Future<bool> getCouponAllUnlimited(RefreshController refreshController) async {
    var result = await Get.find<CouponApiService>().getCouponAllUnlimited();
    couponList.clear();
    couponList.addAll(result.couponList);
    refreshController.loadComplete();
    return true;
  }


  ///领取优惠券
  Future<bool> receiveCoupon(String couponId) async {
    return await Get.find<CouponApiService>().receiveCoupon(couponId);
  }
}