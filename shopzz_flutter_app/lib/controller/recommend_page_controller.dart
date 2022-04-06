
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:shopzz_flutter_app/entity/response/banner_response.dart';
import 'package:shopzz_flutter_app/entity/response/column_detail_response.dart';
import 'package:shopzz_flutter_app/entity/response/home_recommend_response.dart';
import 'package:shopzz_flutter_app/service/home_api_service.dart';

///推荐页面的getx控制器
///定义obx的三种方式
// 第一种 使用 Rx{Type}
// final name = RxString('');
// final isLogged = RxBool(false);
// final count = RxInt(0);
// final balance = RxDouble(0.0);
// final items = RxList<String>([]);
// final myMap = RxMap<String, int>({});

// 第二种是使用 Rx，规定泛型 Rx<Type>。
// final name = Rx<String>('');
// final isLogged = Rx<Bool>(false);
// final count = Rx<Int>(0);
// final balance = Rx<Double>(0.0);
// final number = Rx<Num>(0)
// final items = Rx<List<String>>([]);
// final myMap = Rx<Map<String, int>>({});
// 自定义类 - 可以是任何类
// final user = Rx<User>();

// 第三种更实用、更简单、更可取的方法，只需添加 .obs 作为value的属性。
// final name = ''.obs;
// final isLogged = false.obs;
// final count = 0.obs;
// final balance = 0.0.obs;
// final number = 0.obs;
// final items = <String>[].obs;
// final myMap = <String, int>{}.obs;
// 自定义类 - 可以是任何类
// final user = User().obs;
class RecommendPageController extends GetxController {

  List<BannerEntity> bannerList = <BannerEntity>[].obs;
  List<NavigationEntity> navigationList = <NavigationEntity>[].obs;

  final columnDetailResponse = ColumnDetailResponse().obs;

  ///获取首页的推荐banner和nav
  Future<bool> getBannerResponse(RefreshController refreshController) async {
    var result = await Get.find<HomeApiService>().getBannerResponse();
    bannerList.addAll(result.banners);
    navigationList.addAll(result.navigations);
    refreshController.loadComplete();
    return true;
  }

  ///刷新首页的推荐banner和nav
  void refreshBannerResponse(RefreshController refreshController) async {
    bannerList.clear();
    navigationList.clear();
    await getBannerResponse(refreshController);
    refreshController.refreshCompleted();
  }


  ///获取推荐首页第一个专栏
  Future<bool> getFirstColumnDetail(RefreshController refreshController) async {
    var result = await Get.find<HomeApiService>().getColumnDetailById("1");
    columnDetailResponse.value = result;
    refreshController.refreshCompleted();
    return true;
  }


  int recommendListPage = 1;
  final int recommendListSize = 4;
  List<HomeRecommendEntity> recommendList = <HomeRecommendEntity>[].obs;

  Future<bool> getHomeRecommendList(RefreshController refreshController) async {
    var result = await Get.find<HomeApiService>().getHomeRecommendList(recommendListPage, recommendListSize);

    if(result == null || result.homeRecommendList.isEmpty) {
      refreshController.loadNoData();
      return false;
    }

    recommendList.addAll(result.homeRecommendList);
    recommendListPage++;
    refreshController.refreshCompleted();
    refreshController.loadComplete();
    return true;
  }

  void refreshHomeRecommendList(RefreshController refreshController) async {
    recommendListPage = 1;
    recommendList.clear();
    await getHomeRecommendList(refreshController);
  }


}