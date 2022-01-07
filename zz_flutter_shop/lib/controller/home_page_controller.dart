
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/entity/response/home_app_index_response.dart';
import 'package:zz_flutter_shop/entity/response/home_recommend_response.dart';
import 'package:zz_flutter_shop/service/home_api_service.dart';

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
class HomePageController extends GetxController {

  List<BannerEntity> bannerList = <BannerEntity>[].obs;
  List<NavigationEntity> navigationList = <NavigationEntity>[].obs;

  List<RecommendEntity> hotRecommendList = <RecommendEntity>[].obs;
  List<RecommendEntity> niceRecommendList = <RecommendEntity>[].obs;

  ///获取热门和精品推荐商品列表
  Future<bool> getHotRecommendList(RefreshController refreshController) async {
    var result = await Get.find<HomeApiService>().getHotRecommendList();
    hotRecommendList.clear();
    niceRecommendList.clear();
    hotRecommendList.addAll(result.hotRecommendList);
    niceRecommendList.addAll(result.hotRecommendList);
    refreshController.loadComplete();
    return true;
  }

  ///获取首页的推荐banner和nav
  Future<bool> getHomeRecommendAppIndex(RefreshController refreshController) async {
    var result = await Get.find<HomeApiService>().homeRecommendAppIndex();
    bannerList.clear();
    navigationList.clear();
    bannerList.addAll(result.banners);
    navigationList.addAll(result.navigations);
    refreshController.loadComplete();
    return true;
  }

  void refreshHomeRecommendAppIndex(RefreshController refreshController) async {
    bannerList.clear();
    navigationList.clear();
    await getHomeRecommendAppIndex(refreshController);
    refreshController.refreshCompleted();
  }

}