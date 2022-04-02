import 'package:flutter/material.dart';
import 'package:shopzz_flutter_app/entity/response/banner_response.dart';
import 'package:shopzz_flutter_app/page/home/widgets/home_banner.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/widgets/arc_clipper.dart';

///轮播bar组件
class BannerBar extends StatelessWidget {

  final List<BannerEntity> bannerList;

  const BannerBar({Key key, this.bannerList}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    return Stack(
      children: <Widget>[
        ClipPath(
          clipper: ArcClipper(),

          child: DecoratedBox(
            decoration: const BoxDecoration(
                gradient: LinearGradient(
                  begin: Alignment.topRight,
                  colors: <Color>[ColorManager.main, ColorManager.main],
                )
            ),
            child: Container(
              height: 145.0,
            ),
          ),
        ),
        Align(
          alignment: Alignment.center,
          child: Container(
            padding: const EdgeInsets.only(top: 10, bottom: 15),
            child: HomeBanner(bannerList),
          ),
        ),
      ],
    );
  }
}