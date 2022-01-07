
import 'dart:async';

import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/entity/dto/category_tree_model.dart';
import 'package:zz_flutter_shop/entity/response/home_app_index_response.dart';
import 'package:zz_flutter_shop/service/category_api_service.dart';

class CategoryPageController extends GetxController {

  List<Categorys> categoryList = <Categorys>[].obs;

  List<Children> childrenList = <Children>[].obs;

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