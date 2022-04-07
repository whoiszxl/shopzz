import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:shopzz_flutter_app/controller/product_page_controller.dart';
import 'package:shopzz_flutter_app/entity/response/spu_detail_response.dart';
import 'package:shopzz_flutter_app/page/detail/widgets/attr_modal.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';

///属性label框，点击时弹出modal选择属性
class AttrLabel extends StatefulWidget {
  final SpuEntity spuEntity;
  final String spuId;
  final List<SpuAttributeGroupEntity> attrGroupList;

  const AttrLabel({Key key, this.spuId, this.spuEntity, this.attrGroupList}) : super(key: key);

  @override
  _AttrModal createState() => _AttrModal();

}

class _AttrModal extends State<AttrLabel> {

  final ProductPageController _productPageController = Get.put<ProductPageController>(ProductPageController());

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      child: InkWell(
        onTap: () => openModal(context),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[

            const Text('规格', style: TextStyle(color: ColorManager.fontGrey, fontSize: 11)),

            const SizedBox(width: 6),

            _productPageController.isAllChecked.value ?
            "全选了" : const Text('请选择规格与数量', style: TextStyle(color: ColorManager.black, fontSize: 11))
          ],
        ),
      ),
    );
  }

  ///打开弹出框
  void openModal(BuildContext context) {
    showModalBottomSheet(
        context: context,
        backgroundColor: ColorManager.white,
        isScrollControlled: true,
        elevation: 10,
        shape: const RoundedRectangleBorder(borderRadius: BorderRadius.only(topRight: Radius.circular(15), topLeft: Radius.circular(15))),
        builder: (BuildContext context) {

          //弹出框实际布局
          return AttrModal(
              spuId: widget.spuId,
              specificationList: _productPageController.spuDetailResponse.value.spuAttributeGroupVOList
          );

        });
  }

}

