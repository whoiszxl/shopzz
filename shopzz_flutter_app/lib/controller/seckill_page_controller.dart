
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:shopzz_flutter_app/entity/dto/error_dto.dart';
import 'package:shopzz_flutter_app/entity/response/banner_response.dart';
import 'package:shopzz_flutter_app/entity/response/seckill_item_list_response.dart';
import 'package:shopzz_flutter_app/entity/response/seckill_list_response.dart';
import 'package:shopzz_flutter_app/service/seckill_api_service.dart';

///秒杀页面的getx控制器
class SeckillPageController extends GetxController {

  List<SeckillEntity> seckillList = <SeckillEntity>[].obs;
  List<BannerEntity> bannerList = <BannerEntity>[].obs;
  List<SeckillItemEntity> seckillItemList = <SeckillItemEntity>[].obs;

  ///获取秒杀活动列表
  Future<bool> getSeckillList(RefreshController refreshController) async {
    var result = await Get.find<SeckillApiService>().getSeckillList();
    seckillList.clear();
    bannerList.clear();
    seckillList.addAll(result.seckillList);

    for (var v in result.seckillList) {
      bannerList.add(BannerEntity(type: 666, pic: v.img, bizId: v.id));
    }

    refreshController.refreshCompleted();
    refreshController.loadComplete();
    return true;
  }

  ///获取秒杀商品列表
  Future<bool> getSeckillItemList(RefreshController refreshController, {String seckillId = "1"}) async {
    var result = await Get.find<SeckillApiService>().getSeckillItemList(seckillId);
    seckillItemList.clear();
    seckillItemList.addAll(result.seckillItemList);
    refreshController.loadComplete();
    return true;
  }


  ///秒杀订单异步提交
  Future<String> seckillOrderSubmit(num seckillId, num seckillItemId) async {
    var result = await Get.find<SeckillApiService>().seckillOrderSubmit(seckillId, seckillItemId);
    if(result is ErrorDTO) {
      showToast(result.errorMessage);
      return "0";
    }
    return result;
  }

  var currentSeckillOrderId = "".obs;

  ///秒杀订单异步查询
  Future<String> seckillOrderResult(String seckillItemId, String taskId) async {
    var result = await Get.find<SeckillApiService>().seckillOrderResult(seckillItemId, taskId);
    if(result is ErrorDTO) {
      showToast(result.errorMessage);
      return "0";
    }
    currentSeckillOrderId.value = result.toString();
    print(currentSeckillOrderId.value);
    return result.toString();
  }

}