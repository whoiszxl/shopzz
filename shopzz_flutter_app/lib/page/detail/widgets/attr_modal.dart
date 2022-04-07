import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:shopzz_flutter_app/controller/product_page_controller.dart';
import 'package:shopzz_flutter_app/entity/response/spu_detail_response.dart';
import 'package:shopzz_flutter_app/page/detail/widgets/bottom_button.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';

///属性弹出框实际布局
class AttrModal extends StatefulWidget {

  final String spuId;
  final List<SpuAttributeGroupEntity> specificationList;

  const AttrModal({Key key, this.spuId, this.specificationList}) : super(key: key);

  @override
  _AttrModalState createState() => _AttrModalState();
}

class _AttrModalState extends State<AttrModal> {

  final ProductPageController _productPageController = Get.put<ProductPageController>(ProductPageController());

  @override
  Widget build(BuildContext context) {
    var height = MediaQuery.of(context).size.height;
    return SizedBox(
      height: height * 0.75,
      child: Stack(
        children: <Widget>[
          SizedBox(
            height: height * 0.75 - 60 - MediaQuery.of(context).padding.bottom,
            child: SingleChildScrollView(
              physics: const AlwaysScrollableScrollPhysics(),
              child: Column(
                children: <Widget>[
                  _topInfo(context),
                  _bottomAttrGroup(context),
                ],
              ),
            ),
          ),
          Positioned(
            left: 0,
            right: 0,
            bottom: 10,
            child: BottomButton(
              text: '确定',
              handleOk: () => Get.back(),
            ),
          ),
        ],
      ),
    );
  }

  Widget _topInfo(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(left: 15, right: 15, top: 15, bottom: 0),
      height: 120,
      child: Stack(
        children: <Widget>[

          //右上角关闭按钮
          Positioned(
            top: 0,
            right: 0,
            child: InkWell(
              onTap: () => Navigator.pop(context),
              child: const Icon(Icons.close, color: ColorManager.fontGrey, size: 24.0),
            ),
          ),

          //商品SPU默认图
          Positioned(
            top: 0,
            left: 0,
            child: CachedNetworkImage(
              imageUrl: _productPageController.spuDetailResponse.value.spuVO.defaultPic,
              height: 120,
              width: 120,
            ),
          ),

          //自营
          Positioned(
            top: 17,
            left: 135,
            child: Container(
              padding: const EdgeInsets.symmetric(vertical: 1.5, horizontal: 7),
              decoration: BoxDecoration(
                color: ColorManager.main,
                borderRadius: BorderRadius.circular(10.0),
              ),
              child: const Image(image: AssetImage('assets/images/ziying.png'), width: 25, height: 20),
            ),
          ),


          //价格
          Positioned(
            top: 42,
            left: 135,
            child: RichText(
              text: TextSpan(
                  text: '￥',
                  style: const TextStyle(color: ColorManager.main, fontSize: 12, fontWeight: FontWeight.w500,
                  ),
                  children: <TextSpan>[
                    TextSpan(
                      text: _productPageController.spuDetailResponse.value.spuVO.defaultPrice.toStringAsFixed(2).toString(),
                      style: const TextStyle(
                        color: ColorManager.main,
                        fontSize: 18,
                        fontWeight: FontWeight.w500,
                      ),
                    )
                  ]),
            ),
          ),

          //TODO 数量
          const Positioned(
            top: 100,
            left: 135,
            child: Text('数量', style: TextStyle(color: ColorManager.main, fontSize: 14, fontWeight: FontWeight.normal)),
          ),

        ],
      ),
    );
  }

  Widget _bottomAttrGroup(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(left: 15, right: 15, top: 0, bottom: 15),
      child: Column(
        children: widget.specificationList.asMap().keys.map((index) {
          var groupItem = widget.specificationList[index];
          return Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Container(
                padding: const EdgeInsets.only(bottom: 15, top: 15),
                child: Text(
                  groupItem.keyName,
                  style: const TextStyle(
                    color: ColorManager.fontGrey,
                    fontSize: 14,
                    fontWeight: FontWeight.normal,
                  ),
                ),
              ),
              SizedBox(
                width: MediaQuery.of(context).size.width - 30,
                child: Wrap(
                  spacing: 15.0, // 主轴(水平)方向间距
                  runSpacing: 15.0,
                  alignment: WrapAlignment.start, //沿主轴方向居中
                  children: <Widget>[
                    ...groupItem.spuAttrList.asMap().keys.map((index2) {
                      var spuAttr = groupItem.spuAttrList[index2];
                      return myChoiceChipLabel(
                          text: spuAttr?.value,
                          isSelected: _productPageController.selectedIdList.contains(spuAttr?.valueId),
                          onSelected: (isSelected) {
                            setState(() {
                              _productPageController.selectedSkuCodeList[index] = spuAttr?.keyId.toString() + "-" + spuAttr?.valueId.toString();
                              _productPageController.selectedIdList[index] = spuAttr?.valueId;

                              var map = _productPageController.selectedSkuCodeList.where((element) => element != "-1");
                              _productPageController.skuCode.value = widget.spuId + "\$" + map.join("#");
                              debugPrint("skuCode= " + _productPageController.skuCode.value);


                              if(map.length == widget.specificationList.length) {
                                _productPageController.isAllChecked.value = true;
                              }

                              debugPrint("是否全选" + _productPageController.isAllChecked.value.toString());
                            });

                          }
                      );
                    })
                  ],
                ),
              ),
            ],
          );
        }).toList(),
      ),
    );
  }

  ///属性小标签
  myChoiceChipLabel({String text, bool isSelected, double width, double height = 26, void Function(bool) onSelected}) {
    return SizedBox(
        height: height,
        child: ChoiceChip(
          padding: const EdgeInsets.fromLTRB(5, 0, 5, 0),
          pressElevation: 0,
          selectedColor: ColorManager.white38,
          selected: isSelected,
          backgroundColor: ColorManager.white70,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10),
            side: BorderSide(color: isSelected ? Colors.red : Colors.transparent, width: 1),
          ),
          label: SizedBox(
            width: width,
            child: Text(text, textAlign: TextAlign.center, style: TextStyle(fontSize: 12, color: isSelected ? Colors.red : Colors.black)),
          ),

          onSelected: onSelected,
        )
    );
  }
}