
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:zz_flutter_shop/controller/cart_page_controller.dart';
import 'package:zz_flutter_shop/entity/dto/cholice_chip_select.dart';

import 'package:zz_flutter_shop/entity/response/product_detail_response.dart';
import 'package:zz_flutter_shop/page/widget/product_detail/product_footer.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';

class SaleAttrSelector extends StatefulWidget {

  final List<SaleAttrGroup> saleAttrGroup;

  final List<Skus> skus;

  final ProductVO productVO;

  final ValueChanged<String> skuIdChangeCallback;

  const SaleAttrSelector(this.saleAttrGroup, this.skus, this.productVO, this.skuIdChangeCallback, {Key key}) : super(key: key);

  @override
  _SaleAttrSelector createState() => _SaleAttrSelector();
}


class _SaleAttrSelector extends State<SaleAttrSelector> {

  String attrKey = "选择";

  String attrValue = "请选择规格数量";

  List<String> _selectedButtonIdList;
  String currentSkuId;
  List<String> currentSelectedAttr;

  final CartPageController _cartPageController = Get.find<CartPageController>();

  double _screenHeight;
  double _screenWidth;

  @override
  void initState() {
    super.initState();

    SchedulerBinding.instance.addPostFrameCallback((_) {
      setDefaultSelectButton();
      currentSelectedAttr = List(10);

      _screenHeight = MediaQuery.of(context).size.height;
      _screenWidth = MediaQuery.of(context).size.width;
    });
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return InkWell(
      child: RichText(
        text: TextSpan(
          children: [
            TextSpan(
                text: attrKey,
                style: const TextStyle(fontSize: 11, color: ColorManager.grey)
            ),
            const WidgetSpan(child: SizedBox(width: 6)),
            TextSpan(
                text: attrValue,
                style: const TextStyle(
                    fontWeight: FontWeight.w500, fontSize: 11, color: Colors.black87)),
          ],
        ),
      ),
      onTap: () {
        //弹出SKU选择框
        showCupertinoModalPopup(
            context: context,
            builder: (BuildContext context) {
              return Container(
                decoration: const BoxDecoration(
                    color: ColorManager.white,
                    borderRadius: BorderRadius.only(
                      topLeft: Radius.circular(10),
                      topRight: Radius.circular(10),
                    )
                ),

                padding: const EdgeInsets.all(15),
                child: SizedBox(
                  width: _screenWidth,
                  height: _screenHeight * 0.8,
                  child: Material(
                    color: ColorManager.white,
                    child: Stack(children: <Widget>[
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          Expanded(child: Column(
                            mainAxisAlignment: MainAxisAlignment.start,
                            crossAxisAlignment: CrossAxisAlignment.start,

                            children: [
                              //构建图片头
                              imageHeader(widget.productVO, currentSkuId == null ? null : widget.skus.firstWhere((element) => element.id.toString() == currentSkuId), _screenWidth, _screenHeight),
                              //属性选择组件
                              attrSelect(widget.saleAttrGroup),

                            ],
                          )),
                          //footer
                          ProductFooter(() {
                            if(currentSkuId != null) {
                              showToast("加购成功");
                              _cartPageController.cartAdd(int.parse(currentSkuId), 1);
                            }
                          })
                        ],
                      )


                    ],)
                  ),
                ),
              );
            });
      },
    );

  }

  //属性选择组件
  Widget attrSelect(List<SaleAttrGroup> saleAttrGroup) {
    return LimitedBox(
      maxHeight: _screenHeight * 0.8 - 120,
      child: SingleChildScrollView(
        child: Column(
          children: <Widget>[
            //进行遍历sale attr
            ...saleAttrGroup.asMap().keys.map((index) {
              return Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  //展示销售属性名称
                  attrNameTitle(title: saleAttrGroup[index].attrName),

                  //展示销售属性值
                  ChoiceChipSelect(
                    groupIndex: index,
                  selectedIdListValueChanged: (list) {
                    setState(() {
                      _selectedButtonIdList = list;
                    });
                  },
                    selectOptions: widget.saleAttrGroup[index].attrValues?.map((sku) {
                      return SelectOption(id: sku.skuIds, label: sku.attributeValue, value: sku.skuIds);
                    })?.toList(),
                    selectedIdList: _selectedButtonIdList,
                    onSelect: (selected, isSelected) {
                      setState(() {

                      });
                      _selectedButtonIdList[index] = selected?.id;
                      currentSelectedAttr[index] = selected?.value;

                      List<Set<String>> getSkuIdTmp = <Set<String>>[];
                      for (var selected in currentSelectedAttr) {
                        if(selected != null) {
                          getSkuIdTmp.add(selected.split(",").toSet());
                        }
                      }

                      Set<String> finalSkuId = getSkuIdTmp[0];
                      for(int i = 0; i < getSkuIdTmp.length - 1; i++) {
                        finalSkuId = finalSkuId.intersection(getSkuIdTmp[i+1]);
                      }

                      currentSkuId = finalSkuId.first;
                      widget.skuIdChangeCallback(currentSkuId);

                      //选中了修改详情页的提示标语
                      attrKey = "已选";
                      for (var element in widget.skus) {
                        if(element.id.toString() == currentSkuId) {
                          attrValue = element.skuName;
                        }
                      }
                    },
                  )

                ],
              );
            })
          ],
        ),
      ),
    );
  }

  /*
   * 设置默认选中按钮
   */
  setDefaultSelectButton() {
    _selectedButtonIdList = widget.saleAttrGroup?.map((attrs) {
          return attrs.attrValues[0].skuIds;
        })?.toList();
  }


}



//销售属性的属性名标题组件
attrNameTitle({@required String title, Widget rightAction}) {
  return Container(
    margin: const EdgeInsets.fromLTRB(0, 10, 10, 10),
    child: Flex(
      direction: Axis.horizontal,
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        Text(title, style: const TextStyle(fontSize: 14, fontWeight: FontWeight.bold, decoration: TextDecoration.none, color: Colors.black)),
        Container(child: rightAction)
      ],
    ),
  );
}


//构建图片头
Widget imageHeader(ProductVO productVO, Skus skus, double _screenWidth, double _screenHeight) {
  return Container(
    height: 120,
    width: _screenWidth,
    padding: const EdgeInsets.only(bottom: 10),
    child: Row(crossAxisAlignment: CrossAxisAlignment.end, children: <Widget>[
      //sku圖片展示
      skuImage(skus == null ? productVO.defaultPic : skus.imgUrl),

      //sku標語展示
      Padding(
          padding: const EdgeInsets.only(left: 10),
          child: skuTitleInfo(productVO))
    ]),
  );
}

Widget skuTitleInfo(ProductVO productVO) {
  return Column(
    mainAxisAlignment: MainAxisAlignment.end,
    children: <Widget>[
      Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisAlignment: MainAxisAlignment.end,
        children: <Widget>[
          //价格text
          RichText(
              text: TextSpan(children: [
            const TextSpan(
              style: TextStyle(
                  color: Colors.red, fontSize: 14, fontWeight: FontWeight.bold),
              text: "¥",
            ),
            TextSpan(
              style: const TextStyle(
                  color: Colors.red, fontSize: 18, fontWeight: FontWeight.bold),
              text: productVO.defaultPrice.toString(),
            ),
          ])),

          //选择属性text
          Row(
            children: <Widget>[
              Text("重量: " + productVO.grossWeight.toString(),
                  style: const TextStyle(
                      color: ColorManager.grey,
                      fontSize: 10,
                      decoration: TextDecoration.none)),
              const SizedBox(width: 2),
              Text(
                "编号: " + productVO.id.toString(),
                style: const TextStyle(
                    color: ColorManager.grey,
                    fontSize: 10,
                    decoration: TextDecoration.none),
              )
            ],
          )
        ],
      ),
    ],
  );
}

Widget skuImage(String imageUrl) {
  return InkWell(
    child: Image.network(
      imageUrl,
      scale: 2,
    ),
    onTap: () {
      showToast("todo 展示大图");
    },
  );
}