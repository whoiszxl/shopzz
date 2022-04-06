import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:shopzz_flutter_app/controller/category_page_controller.dart';
import 'package:shopzz_flutter_app/page/category/widgets/category_app_bar.dart';
import 'package:shopzz_flutter_app/page/category/widgets/one_category_tab.dart';
import 'package:shopzz_flutter_app/page/category/widgets/two_category_tab.dart';
import 'package:shopzz_flutter_app/page/home/widgets/search_bar.dart';
import 'package:shopzz_flutter_app/page/home/widgets/tab_bar.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/utils/loading_util.dart';

///分类页面
class CategoryPage extends StatefulWidget {
  const CategoryPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _CategoryPageState();
  }
}

class _CategoryPageState extends State<CategoryPage> with AutomaticKeepAliveClientMixin {

  final RefreshController _refreshController = RefreshController(initialRefresh: false);
  final CategoryPageController _categoryPageController = Get.find<CategoryPageController>();


  @override
  void initState() {
    super.initState();
    _categoryPageController.getCategoryTree(_refreshController);
    _categoryPageController.getCategoryBanner(_refreshController);
  }

  @override
  void dispose() {
    super.dispose();
    _refreshController.dispose();
  }
  @override
  Widget build(BuildContext context) {
    super.build(context);
    return Obx(() {
      if(_categoryPageController.categoryList.isEmpty || _categoryPageController.bannerList.isEmpty) {
        return normalLoading();
      }

      return Scaffold(
        appBar: AppBar(
          backgroundColor: ColorManager.main,
          iconTheme: const IconThemeData(),
          elevation: 0.0,
          titleSpacing: 0.0,
          title: const SearchBar()
        ),
        body: Row(
          mainAxisAlignment: MainAxisAlignment.start,

          children: <Widget>[
            Expanded(
              flex: 2,
              child: OneCategoryTab(
                categoryList: _categoryPageController.categoryList,
                onChildClick: (index) {
                  _categoryPageController.childrenList.clear();
                  _categoryPageController.childrenList.addAll(_categoryPageController.categoryList[index].children);
                },
              ),
            ),

            Expanded(
              flex: 5,
              child: TwoCategoryTab(
                childrenList: _categoryPageController.childrenList,
                banners: _categoryPageController.bannerList,
                onChildClick: (index) {
                  showToast("点击了");
                },
              ),
            )

          ],
        ),
      );
    });
  }

  @override
  bool get wantKeepAlive => true;

}