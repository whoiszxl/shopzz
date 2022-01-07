import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_swiper/flutter_swiper.dart';
import 'package:oktoast/oktoast.dart';
import 'package:zz_flutter_shop/entity/response/home_app_index_response.dart';
class HomeBanner extends StatelessWidget {
  final List<BannerEntity> bannerList;
  final double bannerHeight;
  final double bannerWidth;
  final EdgeInsetsGeometry padding;

  const HomeBanner(this.bannerList, {Key key, this.bannerHeight = 160, this.bannerWidth = 380, this.padding}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: bannerWidth,
      height: bannerHeight,
      child: _banner(),
    );
  }

  ///创建banner组件
  _banner() {
    return Swiper(
      //水平滚动
      scrollDirection: Axis.horizontal,
      //banner的个数
      itemCount: bannerList.length,
      //自动播放
      autoplay: true,
      itemHeight: bannerHeight,
      itemWidth: bannerWidth,
      itemBuilder: (BuildContext context, int index) {
        return _image(bannerList[index]);
      },

      pagination: const SwiperPagination(
          alignment: Alignment.bottomRight,
          margin: EdgeInsets.only(bottom: 10),
          builder: DotSwiperPaginationBuilder(
              color: Colors.white60,
              size: 6,
              activeSize: 7,
          )
      ),
    );
  }

  ///通过接口数据创建图片控件
  _image(BannerEntity bannerEntity) {
    return InkWell(
      onTap: () {
        _handleClick(bannerEntity);
      },

      child: Container(
        padding: padding,
        child: ClipRRect(
          borderRadius: const BorderRadius.all(Radius.circular(6)),
          child: CachedNetworkImage(
              imageUrl: bannerEntity.pic,
              fit: BoxFit.cover,
              errorWidget: (context, url, error) => const Icon(Icons.error),
              imageBuilder: (context, imageProvider) => Container(
                decoration: BoxDecoration(
                  borderRadius: const BorderRadius.all(Radius.circular(6)),
                  image: DecorationImage(
                      image: imageProvider,
                      fit: BoxFit.cover,
                  ),
                ),
              ),
          ),
        ),
      ),
    );
  }

  ///创建banner图点击事件
  void _handleClick(BannerEntity bannerEntity) {
    if(bannerEntity.url.startsWith("http")) {
      //TODO 做http跳转
      if (kDebugMode) {
        print('name:${bannerEntity.name} ,url:${bannerEntity.url}');
      }
    }else {
      showToast("点击啦");
    }
  }
}
