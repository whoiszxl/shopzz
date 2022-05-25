
import 'dart:collection';

import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/dto/error_dto.dart';
import 'package:shopzz_flutter_app/entity/response/seckill_item_list_response.dart';
import 'package:shopzz_flutter_app/entity/response/seckill_list_response.dart';
import 'package:shopzz_flutter_app/http/api_urls.dart';
import 'package:shopzz_flutter_app/http/http_manager.dart';

///秒杀接口服务
class SeckillApiService extends GetxService {

  ///获取秒杀活动列表
  Future<SeckillListResponse> getSeckillList() async {
    var result = await HttpManager.getInstance().post(url: ApiUrls.seckillList);
    return SeckillListResponse.fromJson(result);
  }

  ///通过秒杀活动ID获取秒杀商品列表
  Future<SeckillItemListResponse> getSeckillItemList(String seckillId) async {
    var result = await HttpManager.getInstance().post(url: ApiUrls.seckillItemList + seckillId);
    return SeckillItemListResponse.fromJson(result);
  }

  Future<dynamic> seckillOrderSubmit(num seckillId, num seckillItemId) async {
    Map<String, Object> data = HashMap();
    data["seckillId"] = seckillId;
    data["seckillItemId"] = seckillItemId;
    data["quantity"] = 1;
    var result = await HttpManager.getInstance().post(url: ApiUrls.seckillOrderSubmit, data: data);
    return result;
  }

  Future<dynamic> seckillOrderResult(String seckillItemId, String taskId) async {
    Map<String, Object> data = HashMap();
    data["seckillItemId"] = seckillItemId;
    data["taskId"] = taskId;
    var result = await HttpManager.getInstance().post(url: ApiUrls.seckillOrderResult, data: data);
    return result;
  }
}