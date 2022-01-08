import 'package:get/get.dart';
import 'package:zz_flutter_shop/entity/response/home_app_index_response.dart';
import 'package:zz_flutter_shop/entity/response/home_recommend_response.dart';
import 'package:zz_flutter_shop/http/http_manager.dart';
import 'package:zz_flutter_shop/service/api_urls.dart';

///首页接口服务
class HomeApiService extends GetxService {

  ///获取首页轮播图列表和nav列表
  Future<HomeAppIndexResponse> homeRecommendAppIndex() async {
    var result = await HttpManager.getInstance().post(url: ApiUrls.homeRecommendAppIndex);
    return HomeAppIndexResponse.fromJson(result);
  }

  ///获取首页精品和热门商品列表
  Future<HomeRecommendResponse> getHotRecommendList() async {
    var result = await HttpManager.getInstance().post(url: ApiUrls.homeRecommendProductList);
    return HomeRecommendResponse.fromJson(result);
  }


  ///通过轮播图类型获取轮播图列表
  Future<HomeAppIndexResponse> getBannersByType(int type) async {
    var result = await HttpManager.getInstance().post(url: ApiUrls.bannerByType + type.toString());
    return HomeAppIndexResponse.fromJson(result);
  }
}