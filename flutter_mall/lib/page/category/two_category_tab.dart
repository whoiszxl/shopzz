import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter_mall/model/category_tree_model.dart';
import 'package:flutter_mall/model/index_banner_model.dart';
import 'package:flutter_mall/navigator/zero_navigator.dart';
import 'package:flutter_mall/util/view_util.dart';
import 'package:flutter_mall/widget/zero_banner.dart';

///二级分类与三级分类展示栏目
class TwoCategoryTab extends StatefulWidget {

  final Null Function(int) onChildClick;

  List<Children> cates;

  List<Banners> banners;

  TwoCategoryTab({Key key, this.banners, @required this.cates, @required this.onChildClick}) : super(key: key);

  @override
  TwoCategoryTabState createState() => TwoCategoryTabState();
}

class TwoCategoryTabState extends State<TwoCategoryTab> {

  int activeIndex = 0;

  bool loading = false;

  List<String> mockCateImages = [
    "https://img10.360buyimg.com/focus/s140x140_jfs/t11515/106/2342900928/7913/5a606e3f/5a168e7eNdd5d0121.png",
    "https://img20.360buyimg.com/focus/s140x140_jfs/t14146/201/916948302/5272/8ae80f8e/5a168e78N78b24a8e.jpg",
    "https://img14.360buyimg.com/focus/s140x140_jfs/t13657/281/912225045/5343/3109ae02/5a167b73Na69aae95.jpg",
    "https://img13.360buyimg.com/focus/s140x140_jfs/t1/97678/19/13946/1935/5e5f1631E94c6e613/8b0b4c154d39f97a.jpg",
    "https://img20.360buyimg.com/focus/s140x140_jfs/t11881/31/2355374158/3676/22da94de/5a16a5f0Nc6b32dda.jpg",
    "https://img20.360buyimg.com/focus/s140x140_jfs/t12112/355/904591745/4308/6201dffe/5a16a5aaNdac2fe89.jpg",
    "https://img20.360buyimg.com/focus/s140x140_jfs/t13636/255/952363201/5216/92808140/5a16a5f6Nad15240a.jpg",
    "https://img10.360buyimg.com/focus/s140x140_jfs/t13378/75/927818435/4870/595967f9/5a16a5b5N4075325c.jpg",
    "https://img30.360buyimg.com/focus/s140x140_jfs/t12139/119/906364755/3899/3e9bfbca/5a16a5adN67c1a043.jpg",
    "https://img13.360buyimg.com/focus/s140x140_jfs/t11734/189/2377033150/4145/b78bfcf/5a16a594Ncb41c95a.jpg",
    "https://img20.360buyimg.com/focus/s140x140_jfs/t13420/80/910285628/2736/5f202fd8/5a16a59eNadecac30.jpg",
    "https://img20.360buyimg.com/focus/s140x140_jfs/t13219/359/921087235/4445/bb62050e/5a16a592N7e614f3d.jpg",
    "https://img14.360buyimg.com/focus/s140x140_jfs/t12511/359/951124397/4655/1f4b2503/5a16a585N73dd2d73.jpg",
    "https://img11.360buyimg.com/focus/s140x140_jfs/t14092/4/926363914/5049/8bf96ca4/5a16a57dNed5b68f6.jpg",
    "https://img14.360buyimg.com/focus/s140x140_jfs/t12031/206/932335399/3567/d6d59ad9/5a16a578N283a0f75.jpg",
    "https://img11.360buyimg.com/focus/s140x140_jfs/t13687/323/893934580/3715/4657a7e6/5a16a56dN9e6309e0.jpg",
    "https://img12.360buyimg.com/focus/s140x140_jfs/t12571/97/935919461/4129/7e1b9069/5a16a566N8fb4f9ae.jpg",
    "https://img11.360buyimg.com/focus/s140x140_jfs/t12292/173/915309013/5554/78f4ab5e/5a16a560Nc7626d33.jpg",
    "https://img12.360buyimg.com/focus/s140x140_jfs/t11455/75/2350304185/6636/e301e187/5a16a58cNb6b027df.jpg",
    "https://img20.360buyimg.com/focus/s140x140_jfs/t12499/54/910206832/5998/f91002f8/5a16a588Nff477d9d.jpg",
    "https://img30.360buyimg.com/focus/s140x140_jfs/t12583/285/927237069/5490/e730e27e/5a16a572N83179fdc.jpg",
    "https://img30.360buyimg.com/focus/s140x140_jfs/t1/99545/38/13753/2529/5e5f1d8aE48ce0285/97e6d73f5438a246.jpg"

  ];
  var rng = new Random();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width - 80,
      height: MediaQuery.of(context).size.height - 150,
      color: Colors.white70,
      child: SingleChildScrollView(
        child: Column(
          children: [
            widget.banners != null ? ZeroBanner(widget.banners, bannerHeight: 100, padding: EdgeInsets.only(left: 10, right: 10, top: 10)) : SizedBox(),
            ...twoCategoryList()
          ],
        ),
      ),
    );
  }

  ///二级分类与三级分类共同展示
  List<Widget> twoCategoryList() {
    if (widget.cates == null) {
      return <Widget>[Center(
        child: CircularProgressIndicator(),
      )];
    }

    //遍历二级分类
    return widget.cates.map((twoCate) {

      return Container(
        margin: EdgeInsets.all(10),
        color: Colors.white,
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
                    ZeroNavigator.getInstance().onJumpTo(RouteStatus.product_list, args: {'query': threeCate.name});
                  },
                  child: Container(
                    color: Colors.white,
                    alignment: Alignment.center,
                    child: Column(
                      children: <Widget>[
                        //cachedImage(mockCateImages[rng.nextInt(mockCateImages.length)], height: 70, width: 60, fit: BoxFit.fill),
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
      padding: EdgeInsets.only(left: 10),
      alignment: Alignment.centerLeft,
      color: Colors.white,
      child: Text(name, style: TextStyle(fontSize: 12, fontWeight: FontWeight.bold)),
    );
  }
  ///三级分类名称
  threeCategoryName(String name) {
    return Container(
      height: 20,
      alignment: Alignment.center,
      child: Text(
        name,
        style: TextStyle(fontWeight: FontWeight.normal, fontSize: 11, color: Colors.grey),
      ),
    );
  }

}