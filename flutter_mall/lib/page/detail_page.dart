import 'package:flustars/flustars.dart';
import 'package:flutter/material.dart';
import 'package:flutter_easyrefresh/easy_refresh.dart';
import 'package:flutter_mall/dao/product_dao.dart';
import 'package:flutter_mall/model/product_detail_model.dart';
import 'package:flutter_mall/model/product_model.dart';
import 'package:flutter_mall/util/color.dart';
import 'package:flutter_mall/util/toast.dart';
import 'package:flutter_mall/widget/detail/product_footer.dart';
import 'package:flutter_mall/widget/detail/product_image_banner.dart';
import 'package:flutter_mall/widget/detail/product_price_bar.dart';
import 'package:flutter_mall/widget/detail/sale_attr_selector.dart';
import 'package:flutter_mall/widget/navigation_bar.dart';

class DetailPage extends StatefulWidget {

  final ProductModel productModel;
  const DetailPage(this.productModel);

  @override
  _DetailPageState createState() => _DetailPageState();
}

class _DetailPageState extends State<DetailPage> with SingleTickerProviderStateMixin {

  AnimationController _controller;

  String currentSkuId;

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(vsync: this);
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      bottomSheet: ProductFooter(() {
        if(currentSkuId != null) {
          showToast(currentSkuId);
        }
      }),
      body: FutureBuilder(
        future: _loadData(widget.productModel.productId),
        builder: (context, snapshot) {
          if(snapshot.hasData) {
            List<dynamic> results = snapshot.data;
            ProductDetailModel productDetailModel = results[0];

            return EasyRefresh(
              footer: ClassicalFooter(
                  bgColor:Colors.white,
                  textColor: primary,
                  noMoreText: '没有了',
                  loadingText: '加载中',
                  loadReadyText:'上拉加载....'
              ),
              child: ListView(
                shrinkWrap: true,
                children: <Widget>[

                  //沉浸式Appbar
                  NavigationBar(
                    height: 0,
                    color: Colors.white,
                    statusStyle: StatusStyle.DARK_CONTENT,
                  ),

                  //商品详情图片
                  Padding(
                    padding: EdgeInsets.only(left: 5, right: 5, top: 5),
                    child: ProductImageBanner(productDetailModel.images),
                  ),

                  //价格
                  Padding(
                    padding: EdgeInsets.only(top: 10, left: 10, right: 10),
                    child: productPriceBar(productDetailModel.productVO.defaultPrice)
                  ),

                  //标题
                  Padding(
                      padding: EdgeInsets.only(top: 10, left: 10, right: 10),
                      child: productTitleBar(productDetailModel.productVO.name)
                  ),

                  //副标题
                  Padding(
                    padding: EdgeInsets.only(top: 10, left: 10, right: 10),
                    child: productSubTitleBar(productDetailModel.productVO.subName),
                  ),

                  //促销bar
                  Padding(
                    padding: EdgeInsets.only(top: 10, left: 10, right: 10),
                    child: productPromotionBar("满额减", "满199减20"),
                  ),

                  //邮费信息
                  Padding(
                    padding: EdgeInsets.only(top: 10, left: 10, right: 10),
                    child: postageInfoBar("免邮，自营仓发货"),
                  ),

                  //服务保障
                  Padding(
                    padding: EdgeInsets.only(top: 10, left: 10, right: 10),
                    child: serviceGuarantees(productDetailModel.productVO.serviceGuarantees),
                  ),

                  //销售属性选择框
                  Padding(
                    padding: EdgeInsets.only(top: 15, left: 10, right: 10),
                    child: SaleAttrSelector(productDetailModel.saleAttrGroup, productDetailModel.skus, productDetailModel.productVO, (skuId) {
                      setState(() {
                        currentSkuId = skuId;
                      });
                    }),
                  ),


                ],

              ),
            );
          }else {
            return Center(
              child: Text("未找到数据"),
            );
          }
        },
      )
    );
  }


  ///加载数据
  Future _loadData(int productId) async{

    return Future.wait([
      ProductDao.getProductDetail(productId)
    ]);
  }
}