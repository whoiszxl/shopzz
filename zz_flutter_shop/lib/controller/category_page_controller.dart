
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/entity/dto/category_tree_model.dart';
import 'package:zz_flutter_shop/service/category_api_service.dart';

class CategoryPageController extends GetxController {

  List<Categorys> categorys = <Categorys>[].obs;

  List<Children> children = <Children>[].obs;

  ///获取首页的推荐banner和nav
  Future<bool> getCategoryTree(RefreshController refreshController) async {
    var result = await Get.find<CategoryApiService>().getCategoryTree();
    categorys.clear();
    categorys.addAll(result.categorys);
    refreshController.loadComplete();
    return true;
  }
}