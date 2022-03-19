
import 'package:flutter/material.dart';
import 'package:flutter_mall/model/product_detail_model.dart';
import 'package:flutter_mall/util/toast.dart';
import 'package:flutter_swiper/flutter_swiper.dart';

class ProductImageBanner extends StatelessWidget {
  final List<Images> productImages;
  final double bannerHeight;
  final EdgeInsetsGeometry padding;

  const ProductImageBanner(this.productImages, {Key key, this.bannerHeight = 400, this.padding}) : super(key: key);

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
      itemCount: productImages.length,
      autoplay: true,
      autoplayDelay: 10000,
      itemBuilder: (BuildContext context, int index) {
        return _image(productImages[index]);
      },

      pagination: SwiperPagination(
          alignment: Alignment.bottomCenter,
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
  _image(Images image) {
    return InkWell(
      onTap: () {
        _handleClick(image);
      },

      child: Container(
        padding: padding,
        child: ClipRRect(
          borderRadius: BorderRadius.all(Radius.circular(6)),
          child: Image.network(image.imgUrl, fit: BoxFit.fill),
        ),
      ),
    );
  }

  ///创建banner图点击事件
  void _handleClick(Images image) {
    showToast("点击啦" + image.imgName);
  }
}