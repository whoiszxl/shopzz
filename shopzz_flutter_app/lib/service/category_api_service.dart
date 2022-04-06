import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/response/banner_response.dart';
import 'package:shopzz_flutter_app/entity/response/category_response.dart';
import 'package:shopzz_flutter_app/http/api_urls.dart';
import 'package:shopzz_flutter_app/http/http_manager.dart';

///分类页接口服务
class CategoryApiService extends GetxService {

  ///获取分类树
  Future<CategoryResponse> getCategoryTree() async {
    var result = await HttpManager.getInstance().get(url: ApiUrls.categoryTree);
    return CategoryResponse.fromJson(result);
  }

  ///获取分类页banner
  Future<BannerResponse> getCategoryBanner() async {
    var result = await HttpManager.getInstance().post(url: ApiUrls.homeBanner);
    return BannerResponse.fromJson(result);
  }


}