
import 'package:flutter/material.dart';
import 'package:flutter_swiper/flutter_swiper.dart';
import 'package:oktoast/oktoast.dart';
import 'package:zz_flutter_shop/entity/response/product_detail_response.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';

class ProductImageBanner extends StatelessWidget {
  final List<Images> productImages;
  final double bannerHeight;
  final EdgeInsetsGeometry padding;

  const ProductImageBanner(this.productImages, {Key key, this.bannerHeight = 400, this.padding}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: bannerHeight,
      child: _banner(context),
    );
  }

  ///创建banner组件
  _banner(context) {
    var right = 10 + (padding?.horizontal ?? 0) / 2;
    return Stack(
      children: [

        //在点击一个商品后，再点击另外一个商品，swiper会快速不正常滚动 https://segmentfault.com/q/1010000021562438
        Swiper(
          itemCount: productImages.length,
          autoplay: true,
          autoplayDelay: 10000,
          index: 0,
          itemBuilder: (BuildContext context, int index) {
            return _image(productImages[index]);
          },

          pagination: SwiperPagination(
              alignment: Alignment.bottomCenter,
              margin: EdgeInsets.only(right: right, bottom: 10),
              builder: const DotSwiperPaginationBuilder(
                  color: Colors.white60,
                  size: 6,
                  activeSize: 7
              )
          ),
        ),

        Positioned(
          top: 2,
          left: 3,
          child: InkWell(
            child: const Icon(Icons.keyboard_return),
            onTap: () {
              Navigator.pop(context);
            },
          ),
        ),
      ],
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
          borderRadius: const BorderRadius.all(Radius.circular(6)),
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