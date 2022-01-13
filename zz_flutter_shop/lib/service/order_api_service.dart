import 'dart:collection';

import 'package:get/get.dart';
import 'package:zz_flutter_shop/entity/response/order_pay_dc_response.dart';
import 'package:zz_flutter_shop/http/http_manager.dart';
import 'package:zz_flutter_shop/service/api_urls.dart';

///订单接口服务
class OrderApiService extends GetxService {

  ///提交订单
  Future<String> orderSubmit(String addressId, {String orderComment = ""}) async {
    Map<String, Object> params = HashMap();
    params["addressId"] = addressId;
    params["orderComment"] = orderComment;
    var result = await HttpManager.getInstance().post(url: ApiUrls.orderSubmit, data: params);
    return result;
  }


  ///支付数字货币订单
  Future<OrderPayDcResponse> orderPayDc(String orderId, String dcName) async {
    Map<String, Object> params = HashMap();
    params["orderId"] = orderId;
    params["dcName"] = dcName;
    params["payType"] = 3;
    var result = await HttpManager.getInstance().post(url: ApiUrls.orderPay, data: params);
    return OrderPayDcResponse.fromJson(result);
  }



}