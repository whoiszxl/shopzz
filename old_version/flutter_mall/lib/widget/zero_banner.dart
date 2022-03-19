
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/model/index_banner_model.dart';
import 'package:flutter_mall/util/toast.dart';
import 'package:flutter_swiper/flutter_swiper.dart';
class ZeroBanner extends StatelessWidget {
  final List<Banners> bannerList;
  final double bannerHeight;
  final EdgeInsetsGeometry padding;

  const ZeroBanner(this.bannerList, {Key key, this.bannerHeight = 160, this.padding}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      height: bannerHeight,
      child: _banner(),
    );
  }

  ///创建banner组件
  _banner() {
    var right = 10 + (padding?.horizontal ?? 0) / 2;
    return Swiper(
      itemCount: bannerList.length,
      autoplay: true,
      itemBuilder: (BuildContext context, int index) {
        return _image(bannerList[index]);
      },

      pagination: SwiperPagination(
          alignment: Alignment.bottomRight,
          margin: EdgeInsets.only(right: right, bottom: 10),
          builder: DotSwiperPaginationBuilder(
              color: Colors.white60,
              size: 6,
              activeSize: 7
          )
      ),
    );
  }

  ///通过接口数据创建图片控件
  _image(Banners bannerModel) {
    return InkWell(
      onTap: () {
        _handleClick(bannerModel);
      },

      child: Container(
        padding: padding,
        child: ClipRRect(
          borderRadius: BorderRadius.all(Radius.circular(6)),
          child: Image.network(bannerModel.pic, fit: BoxFit.cover),
        ),
      ),
    );
  }

  ///创建banner图点击事件
  void _handleClick(Banners banners) {
    if(banners.pic.startsWith("http")) {
      //TODO 做http跳转
      print('name:${banners.name} ,url:${banners.url}');
    }else {
      showToast("点击啦");
    }
  }
}
