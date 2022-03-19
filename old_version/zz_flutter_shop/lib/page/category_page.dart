import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/controller/category_page_controller.dart';
import 'package:zz_flutter_shop/page/widget/category/category_app_bar.dart';
import 'package:zz_flutter_shop/page/widget/category/one_category_tab.dart';
import 'package:zz_flutter_shop/page/widget/category/two_category_tab.dart';
import 'package:zz_flutter_shop/utils/loading_util.dart';

///分类页面
class CategoryPage extends StatefulWidget {

  const CategoryPage({Key key}) : super(key: key);

  @override
  _CategoryPageState createState() => _CategoryPageState();
}

class _CategoryPageState extends State<CategoryPage> with AutomaticKeepAliveClientMixin {

  final RefreshController _refreshController = RefreshController(initialRefresh: false);
  final CategoryPageController _categoryPageController = Get.find<CategoryPageController>();


  @override
  bool get wantKeepAlive => true;

  @override
  void initState() {
    super.initState();
    //初始化加载分类树
    _categoryPageController.getCategoryTree(_refreshController);
    _categoryPageController.getCategoryBanner(_refreshController);
  }

  @override
  Widget build(BuildContext context) {
    super.build(context);
    return Obx(() {
      if(_categoryPageController.categoryList.isEmpty || _categoryPageController.bannerList.isEmpty) {
        return normalLoading();
      }

      return Scaffold(
        appBar: categoryAppBar(context),
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
                    })
            ),

            Expanded(
                flex: 5,
                child: TwoCategoryTab(
                  childrenList: _categoryPageController.childrenList,
                  banners: _categoryPageController.bannerList,
                  onChildClick: (index) {

                  },
                )
            ),
          ],
        ),
      );
    });
  }


}