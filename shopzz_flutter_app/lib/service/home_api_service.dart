import 'dart:collection';

import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/response/banner_response.dart';
import 'package:shopzz_flutter_app/entity/response/column_detail_response.dart';
import 'package:shopzz_flutter_app/http/api_urls.dart';
import 'package:shopzz_flutter_app/http/http_manager.dart';

///首页接口服务
class HomeApiService extends GetxService {

  ///获取首页轮播图列表和nav列表
  Future<BannerResponse> getBannerResponse() async {
    var result = await HttpManager.getInstance().post(url: ApiUrls.homeBanner);
    return BannerResponse.fromJson(result);
  }

  ///通过专栏ID获取专栏详情
  Future<ColumnDetailResponse> getColumnDetailById(String columnId) async {
    Map<String, String> params = HashMap();
    params["id"] = columnId;
    var result = await HttpManager.getInstance().post(url: ApiUrls.columnDetailById, data: params);
    return ColumnDetailResponse.fromJson(result);
  }
}