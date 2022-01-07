import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/controller/category_page_controller.dart';
import 'package:zz_flutter_shop/page/widget/category/category_app_bar.dart';
import 'package:zz_flutter_shop/page/widget/category/one_category_tab.dart';
import 'package:zz_flutter_shop/page/widget/category/two_category_tab.dart';

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
    //_categoryPageController.getCategoryTree(_refreshController);
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Text("分类"),
    );
  }

  _buildHome(BuildContext context) {
    return Obx(() {
      var categorys = _categoryPageController.categorys;
      return Scaffold(
        appBar: categoryAppBar(context),
        body: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[

            Expanded(
                flex: 2,
                child: OneCategoryTab(
                    categorys: categorys,
                    onChildClick: (index) {
                      _categoryPageController.children = categorys[index].children;
                    })
            ),

            Expanded(
                flex: 5,
                child: TwoCategoryTab(
                  cates: _categoryPageController.children,
                  //banners: banners,
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