import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/entity/dto/category_tree_model.dart';
import 'package:zz_flutter_shop/entity/response/home_app_index_response.dart';
import 'package:zz_flutter_shop/page/widget/home_banner.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/utils/image_util.dart';

///二级分类与三级分类展示栏目
class TwoCategoryTab extends StatefulWidget {

  final Null Function(int) onChildClick;

  List<Children> childrenList;

  List<BannerEntity> banners;

  TwoCategoryTab({Key key, this.banners, @required this.childrenList, @required this.onChildClick}) : super(key: key);

  @override
  TwoCategoryTabState createState() => TwoCategoryTabState();
}

class TwoCategoryTabState extends State<TwoCategoryTab> {


  ScrollController scrollController;

  @override
  void initState() {
    scrollController = ScrollController();
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
    scrollController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Obx(() => Container(
      width: MediaQuery.of(context).size.width - 80,
      height: MediaQuery.of(context).size.height - 150,
      color: ColorManager.white70,
      child: SingleChildScrollView(
        controller: scrollController,
        child: Column(
          children: [
            widget.banners != null ? HomeBanner(widget.banners, bannerHeight: 100, padding: const EdgeInsets.only(left: 10, right: 10, top: 10), showPagination: false,) : const SizedBox(),
            ...twoCategoryList()
          ],
        ),
      ),
    ));
  }

  ///二级分类与三级分类共同展示
  List<Widget> twoCategoryList() {
    if (widget.childrenList == null) {
      return <Widget>[

        const Center(child: CircularProgressIndicator())

      ];
    }

    //遍历二级分类
    return widget.childrenList.map((twoCate) {

      return Container(
        margin: const EdgeInsets.all(10),
        color: ColorManager.white,
        child: Column(
          children: <Widget>[

            //二级分类名称
            twoCategoryName(twoCate?.name),

            //三级分类图片与名称
            GridView.count(
              physics: const NeverScrollableScrollPhysics(),
              shrinkWrap: true,
              scrollDirection: Axis.vertical,
              crossAxisCount: 3,
              mainAxisSpacing: 1,
              crossAxisSpacing: 1,
              childAspectRatio: 1,


              children: twoCate?.children?.map((threeCate) {

                //构建三级分类样式
                return InkWell(
                  onTap: () {
                    //TODO 跳转到商品列表
                  },
                  child: Container(
                    color: ColorManager.white,
                    alignment: Alignment.center,
                    child: Column(
                      children: <Widget>[
                        cachedImage(threeCate.icon, height: 70, width: 60, fit: BoxFit.fill),

                        //三级分类名称
                        threeCategoryName(threeCate.name)
                      ],
                    ),
                  ),
                );
              })?.toList(),

            )
          ],
        ),
      );


    }).toList();
  }


  ///二级分类名称
  twoCategoryName(String name) {
    return Container(
      height: 30,
      padding: const EdgeInsets.only(left: 5),
      alignment: Alignment.centerLeft,
      color: ColorManager.white,
      child: Text(name, style: const TextStyle(fontSize: 12, fontWeight: FontWeight.bold)),
    );
  }
  ///三级分类名称
  threeCategoryName(String name) {
    return Expanded(child: Container(
      height: 25,
      alignment: Alignment.center,
      child: Text(
        name,
        style: const TextStyle(fontWeight: FontWeight.normal, fontSize: 10, color: ColorManager.black),
      ),
    ));
  }

}