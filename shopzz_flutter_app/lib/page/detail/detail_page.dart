import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:shopzz_flutter_app/controller/cart_page_controller.dart';
import 'package:shopzz_flutter_app/controller/product_page_controller.dart';
import 'package:shopzz_flutter_app/page/detail/widgets/attr_label.dart';
import 'package:shopzz_flutter_app/page/detail/widgets/attr_modal.dart';
import 'package:shopzz_flutter_app/page/detail/widgets/product_footer.dart';
import 'package:shopzz_flutter_app/page/detail/widgets/product_image_banner.dart';
import 'package:shopzz_flutter_app/page/detail/widgets/product_many_text_bar.dart';
import 'package:shopzz_flutter_app/utils/loading_util.dart';

///SPU详情页面
class DetailPage extends StatefulWidget {

  const DetailPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _DetailPageState();
  }
}

class _DetailPageState extends State<DetailPage> with SingleTickerProviderStateMixin {

  //当前选中的SPUID，从上一个页面传过来
  String currentSpuId;

  final RefreshController _refreshController = RefreshController(initialRefresh: false);

  final ProductPageController _productPageController = Get.put(ProductPageController());
  final CartPageController _cartPageController = Get.put(CartPageController());


  @override
  void initState() {
    //获取从上一个页面传递过来的spuId
    Map<String, String> getParams = Get.parameters;
    currentSpuId = getParams['spuId'];
    _productPageController.productDetail(currentSpuId);
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
    _refreshController.dispose();
  }
  @override
  Widget build(BuildContext context) {
    return Obx(() {
      //存在校验
      if(_productPageController.spuDetailResponse.value == null || _productPageController.spuDetailResponse.value.spuVO == null) {
        return normalLoading();
      }

      return Scaffold(

        bottomSheet: ProductFooter(
            () {
              if(!_productPageController.isAllChecked.value) {
                showModalBottomSheet(
                    context: context,
                    backgroundColor: Colors.white,
                    isScrollControlled: true,
                    elevation: 10,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(15),
                    ),
                    builder: (BuildContext context) {
                      return AttrModal(
                          spuId: currentSpuId,
                          specificationList: _productPageController.spuDetailResponse.value.spuAttributeGroupVOList);

                    });
              }else {
                _cartPageController.cartAdd(_productPageController.skuCode.value, 1);
              }
            },
            () {
              if(!_productPageController.isAllChecked.value) {
                showModalBottomSheet(
                    context: context,
                    backgroundColor: Colors.white,
                    isScrollControlled: true,
                    elevation: 10,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(15),
                    ),
                    builder: (BuildContext context) {
                      return AttrModal(
                          spuId: currentSpuId,
                          specificationList: _productPageController.spuDetailResponse.value.spuAttributeGroupVOList);

                    });
              }
            }
        ),
        body: SmartRefresher(
          enablePullDown: true,
          enablePullUp: true,
          header: const ClassicHeader(),
          footer: const ClassicFooter(),
          controller: _refreshController,
          onRefresh: () {
            _productPageController.productDetail(currentSpuId);
            _refreshController.refreshCompleted();
          },
          child: _body(),
        ),
      );
    });
  }


  _body() {
    return ListView(
      shrinkWrap: true,
      children: <Widget>[
        //商品详情图片
        Padding(
          padding: const EdgeInsets.only(left: 5, right: 5, top: 0),
          child: ProductImageBanner(_productPageController.spuDetailResponse.value.images),
        ),

        //价格
        Padding(
            padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
            child: productPriceBar(_productPageController.spuDetailResponse.value.spuVO.defaultPrice.toString())
        ),

        //标题
        Padding(
            padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
            child: InkWell(
              onTap: () {
                //复制商品标题到剪切板
                Clipboard.setData(ClipboardData(text: _productPageController.spuDetailResponse.value.spuVO.name));
                showToast("复制成功");
              },
              child: productTitleBar(_productPageController.spuDetailResponse.value.spuVO.name),
            )
        ),

        //副标题
        Padding(
          padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
          child: productSubTitleBar(
              _productPageController.spuDetailResponse.value.spuVO.subName),
        ),

        //TODO 促销bar
        Padding(
          padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
          child: productPromotionBar("满额减", "满199减20"),
        ),

        //TODO 邮费信息
        Padding(
          padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
          child: postageInfoBar("免邮，自营仓发货"),
        ),

        //配件
        Padding(
          padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
          child: packageList(
              _productPageController.spuDetailResponse.value.spuVO.packageList),
        ),

        //销售属性选择框
        Padding(
          padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
          child: AttrLabel(
              spuId: currentSpuId,
              spuEntity: _productPageController.spuDetailResponse.value.spuVO,
              attrGroupList: _productPageController.spuDetailResponse.value.spuAttributeGroupVOList),
        ),

        //TODO 配送选择框
        Padding(
          padding: const EdgeInsets.only(top: 10, left: 10, right: 10),
          child: addressSet(
              "湖南省长沙市岳麓区梅溪湖街道", "现货", "18:00前付款，预计周日(10月20日)送达"),
        ),


        //TODO 商品评价
        Padding(
          padding: const EdgeInsets.only(top: 15, left: 10, right: 10),
          child: productComment(1980, 99.5),
        ),
      ],

    );
  }

}