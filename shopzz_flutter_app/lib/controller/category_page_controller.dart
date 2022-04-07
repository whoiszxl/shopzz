
import 'dart:async';

import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:shopzz_flutter_app/entity/response/banner_response.dart';
import 'package:shopzz_flutter_app/entity/response/category_response.dart';
import 'package:shopzz_flutter_app/service/category_api_service.dart';

class CategoryPageController extends GetxController {

  List<CategoryEntity> categoryList = <CategoryEntity>[].obs;

  List<CategoryEntity> childrenList = <CategoryEntity>[].obs;

  List<BannerEntity> bannerList = <BannerEntity>[].obs;


  ///获取分类树
  Future<bool> getCategoryTree(RefreshController refreshController) async {
    var result = await Get.find<CategoryApiService>().getCategoryTree();
    categoryList.clear();
    categoryList.addAll(result.categorys);
    childrenList.addAll(result.categorys[0].children);
    refreshController.loadComplete();
    return true;
  }

  Future<bool> getCategoryBanner(RefreshController refreshController) async {
    var result = await Get.find<CategoryApiService>().getCategoryBanner();
    bannerList.clear();
    bannerList.addAll(result.banners);
    refreshController.loadComplete();
    return true;
  }

}