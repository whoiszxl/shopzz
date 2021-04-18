import 'package:flutter/material.dart';
import 'package:flutter_mall/dao/category_dao.dart';
import 'package:flutter_mall/dao/home_dao.dart';
import 'package:flutter_mall/model/category_tree_model.dart';
import 'package:flutter_mall/model/index_banner_model.dart';
import 'package:flutter_mall/page/category/one_category_tab.dart';
import 'package:flutter_mall/page/category/two_category_tab.dart';
import 'package:flutter_mall/util/log_util.dart';
import 'package:flutter_mall/widget/category/category_app_bar.dart';
import 'package:flutter_mall/widget/loading.dart';

class CategoryPage extends StatefulWidget {
  @override
  _CategoryPageState createState() => _CategoryPageState();
}

class _CategoryPageState extends State<CategoryPage> with AutomaticKeepAliveClientMixin {

  bool loading  = false;

  List<Categorys> categorys;

  List<Children> children;

  List<Banners> banners;


  @override
  bool get wantKeepAlive => true;

  @override
  void initState() {
    loadData();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    if(loading) {
      return zeroLoading();
    }else {
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
                      setState(() {
                        children = categorys[index].children;
                      });
                    })
            ),

            Expanded(
                flex: 5,
                child: TwoCategoryTab(
                  cates: children,
                  banners: banners,
                  onChildClick: (index) {
                    Log.info(index);
                  },
                )
            ),
          ],
        ),
      );
    }
  }


  ///初始化总分类树数据
  Future<CategoryTreeModel> getCategoryTreeData() async {
    CategoryTreeModel result = await CategoryDao.getCategoryTree();
    return result;
  }

  ///初始化二级分类栏上的banner轮播图
  Future<IndexBannerModel> getBannerData() async {
    IndexBannerModel result = await HomeDao.getIndexBanner();
    return result;
  }

  ///加载数据
  Future<void> loadData() async {
    if(loading) {
      Log.info("还在加载中");
      return;
    }
    loading = true;

    try{
      var categoryTreeResult = await getCategoryTreeData();
      var bannerResult = await getBannerData();

      setState(() {
        categorys = categoryTreeResult.categorys;
        banners = bannerResult.banners;

        if(children == null) {
          children = categoryTreeResult.categorys[0].children;
        }
        loading = false;
      });

    } on Exception catch (e) {
      loading = false;
      Log.info("加载出错" + e.toString());
    }
  }
}