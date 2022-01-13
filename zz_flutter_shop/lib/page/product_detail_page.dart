import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/controller/cart_page_controller.dart';
import 'package:zz_flutter_shop/controller/product_page_controller.dart';
import 'package:zz_flutter_shop/page/widget/product/my_navigation_bar.dart';
import 'package:zz_flutter_shop/page/widget/product_detail/product_footer.dart';
import 'package:zz_flutter_shop/page/widget/product_detail/product_image_banner.dart';
import 'package:zz_flutter_shop/page/widget/product_detail/product_price_bar.dart';
import 'package:zz_flutter_shop/page/widget/product_detail/sale_attr_selector.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/utils/loading_util.dart';

///商品详情页
class ProductDetailPage extends StatefulWidget {

  const ProductDetailPage({Key key}) : super(key: key);

  @override
  _ProductDetailPageState createState() => _ProductDetailPageState();
}

class _ProductDetailPageState extends State<ProductDetailPage> with SingleTickerProviderStateMixin {

  String currentSkuId;

  String currentProductId;

  double appBarOpacity = 0;

  final RefreshController _refreshController = RefreshController(initialRefresh: false);

  final ProductPageController _productPageController = Get.put<ProductPageController>(ProductPageController());

  final CartPageController _cartPageController = Get.find<CartPageController>();

  @override
  void initState() {
    super.initState();

    Future.delayed(Duration.zero, () {
      Map<String, String> getParams = Get.parameters;
      currentProductId = getParams['productId'];
      _productPageController.productDetail(currentProductId);
      _refreshController.refreshCompleted();
    });


  }

  @override
  void dispose() {
    super.dispose();
    _refreshController.dispose();
  }

  void _onRefresh() async{
    _productPageController.productDetail(currentProductId);
    _refreshController.refreshCompleted();
  }

  @override
  Widget build(BuildContext context) {

      return Obx(() {
        if(_productPageController.productDetailResponse.value == null
            || _productPageController.productDetailResponse.value.productVO == null) {
          return normalLoading();
        }else {
          return Scaffold(
              bottomSheet: ProductFooter(() {
                _cartPageController.cartAdd(int.parse(currentSkuId), 1);
                showToast("添加成功");
              }),
              body: SmartRefresher(
                enablePullDown: true,
                enablePullUp: true,
                header: const ClassicHeader(),
                footer: const ClassicFooter(),
                controller: _refreshController,
                onRefresh: _onRefresh,
                child: _body(),
              )
          );
        }
      });

  }

  _body() {
    return ListView(
      shrinkWrap: true,
      children: <Widget>[
        //Appbar
        const MyNavigationBar(
          height: 0,
          color: ColorManager.white,
          statusStyle: StatusStyle.DARK_CONTENT,
        ),

        //商品详情图片
        Padding(
          padding: const EdgeInsets.only(left: 5, right: 5, top: 0),
          child: ProductImageBanner(_productPageController.productDetailResponse.value.images),
        ),

        //价格
        Padding(
            padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
            child: productPriceBar(_productPageController.productDetailResponse.value.productVO.defaultPrice.toString())
        ),

        //标题
        Padding(
            padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
            child: productTitleBar(_productPageController.productDetailResponse.value.productVO.name)
        ),

        //副标题
        Padding(
          padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
          child: productSubTitleBar(
              _productPageController.productDetailResponse.value.productVO.subName),
        ),

        //促销bar
        Padding(
          padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
          child: productPromotionBar("满额减", "满199减20"),
        ),

        //邮费信息
        Padding(
          padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
          child: postageInfoBar("免邮，自营仓发货"),
        ),

        //服务保障
        Padding(
          padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
          child: serviceGuarantees(
              _productPageController.productDetailResponse.value.productVO.serviceGuarantees),
        ),

        //销售属性选择框
        Padding(
          padding: const EdgeInsets.only(top: 15, left: 10, right: 10),
          child: SaleAttrSelector(_productPageController.productDetailResponse.value.saleAttrGroup,
              _productPageController.productDetailResponse.value.skus, _productPageController.productDetailResponse.value.productVO, (skuId) {
                setState(() {
                  currentSkuId = skuId;
                });
              }),
        ),

        //配送选择框
        Padding(
          padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
          child: addressSet(
              "湖南省长沙市岳麓区梅溪湖街道", "现货", "18:00前付款，预计周日(10月20日)送达"),
        ),


        //商品评价
        Padding(
          padding: const EdgeInsets.only(top: 15, left: 10, right: 10),
          child: productComment(1980, 99.5),
        ),
      ],

    );
  }

  navigatorActionBar({IconData icon, double opacity = 1, Function onTap, double iconSize = 28}) {
    return GestureDetector(
      child: Container(
        width: 30,
        height: 30,
        decoration: BoxDecoration(
          color: Color.fromRGBO(99, 99, 99, 1 - opacity),
          borderRadius: const BorderRadius.all(
            Radius.circular(15),
          ),
        ),
        child: Icon(
          icon,
          color: opacity == 1
              ? ColorManager.main
              : ColorManager.white,
          size: iconSize,
        ),
      ),
      onTap: onTap,
    );
  }

}