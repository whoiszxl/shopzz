import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/controller/cart_page_controller.dart';
import 'package:zz_flutter_shop/controller/member_address_controller.dart';
import 'package:zz_flutter_shop/controller/order_controller.dart';
import 'package:zz_flutter_shop/entity/response/cart_detail_response.dart';
import 'package:zz_flutter_shop/page/widget/address/default_address.dart';
import 'package:zz_flutter_shop/page/widget/confirm_order/order_confirm_footer.dart';
import 'package:zz_flutter_shop/page/widget/product/little_tag.dart';
import 'package:zz_flutter_shop/page/widget/product/my_navigation_bar.dart';
import 'package:zz_flutter_shop/page/widget/settings/normal_appbar.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';
import 'package:zz_flutter_shop/utils/image_util.dart';
import 'package:zz_flutter_shop/utils/loading_util.dart';

///订单确认页面
class OrderConfirmPage extends StatefulWidget {
  const OrderConfirmPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _OrderConfirmPageState();
  }
}

class _OrderConfirmPageState extends State<OrderConfirmPage>{

  final MemberAddressController memberAddressController = Get.put<MemberAddressController>(MemberAddressController());

  final CartPageController cartPageController = Get.find<CartPageController>();

  final OrderController orderController = Get.put<OrderController>(OrderController());

  @override
  void initState() {
    super.initState();

    //获取当前用户的默认地址和最新的购物车信息
    memberAddressController.getMemberAddressList();
    cartPageController.cartDetail();
  }

  @override
  void dispose() {
    super.dispose();
  }


  @override
  Widget build(BuildContext context) {
    return Obx(() {
      if(memberAddressController.memberAddressResponse.value == null
      || memberAddressController.memberAddressResponse.value.mainAddress == null
      || cartPageController.cartDetailResponse.value == null
      || cartPageController.cartDetailResponse.value.cartItemVOList == null) {
        return normalLoading();
      }else {
        return Scaffold(
          bottomSheet: OrderConfirmFooter(() {
            orderController.orderSubmit(memberAddressController.memberAddressResponse.value.mainAddress.id.toString());
          }),
          body: Column(
              children: [

                //appbar
                MyNavigationBar(
                  height: 50,
                  child: normalAppBar(context, "确认订单"),
                  color: ColorManager.main,
                  statusStyle: StatusStyle.LIGHT_CONTENT,
                ),


                Padding(
                  padding: const EdgeInsets.only(top: 0, left: 5, right: 5),
                  child: SingleChildScrollView(
                    child: Column(
                      children: <Widget>[

                        //默认地址组件
                        Padding(
                          padding: const EdgeInsets.only(top: 10),
                          child: defaultAddressTile(memberAddressController.memberAddressResponse.value.mainAddress),
                        ),

                        //购物车概览组件
                        Padding(
                          padding: const EdgeInsets.only(top: 0),
                          child: cartDescTile(cartPageController.cartDetailResponse.value.cartItemVOList),
                        ),

                      ],
                    ),
                  ),
                ),

                Padding(
                  padding: const EdgeInsets.only(top: 5),
                  child: Container(
                    decoration: const BoxDecoration(
                        borderRadius: BorderRadius.all(Radius.circular(8))
                    ),
                    child: Column(
                      children: <Widget>[
                        Padding(padding: const EdgeInsets.only(top: 10), child: otherTile([
                          myTile("商品总额", cartPageController.cartDetailResponse.value.totalAmount.toString(), () {}),
                          myTile("运费", "¥ 0.00", () {}),
                          myTile("满减", "- ¥ 0.00", () {}, resultColor: ColorManager.red),
                          myTile("优惠券", "无可用", () {}),
                          myTile("合计", cartPageController.cartDetailResponse.value.totalAmount.toString(), () {}, resultColor: ColorManager.red),
                        ])),

                      ],
                    ),
                  ),
                )
              ]
          ),
        );
      }

    });
  }

  otherTile(list) {
    return Container(
      decoration: const BoxDecoration(
          color: ColorManager.white,
          borderRadius: BorderRadius.only(
              bottomLeft: Radius.circular(8),
              bottomRight: Radius.circular(8)
          )
      ),

      child: Column(
        children: list,
      ),
    );
  }

  Widget myTile(String title, String result, Function() func, {Color resultColor = ColorManager.black}) {
    return ListTile(
        title: Text(title, style: const TextStyle(fontSize: 14)),
        trailing: Text(result, style: TextStyle(fontSize: 14, color: resultColor)),
        onTap: func
    );
  }

  cartDescTile(List<CartItemVO> cartItemVOList) {

    return ListView.builder(
        shrinkWrap:true,
        itemCount: cartItemVOList.length,
        itemBuilder: (BuildContext context, int index) {
          if(cartItemVOList[index].checked == 1) {
            return Container(
                decoration: const BoxDecoration(
                    color: ColorManager.white,
                    borderRadius: BorderRadius.all(Radius.circular(8))
                ),
                child: ListTile(
                  leading: cachedImage(cartItemVOList[index].skuPic, height: 70, width: 70),
                  trailing: Text("x" + cartItemVOList[index].quantity.toString()),
                  title: Text(cartItemVOList[index].skuName, style: const TextStyle(fontSize: 12, color: ColorManager.black)),
                  subtitle: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      Text(cartItemVOList[index].price.toString(), style: const TextStyle(fontSize: 14, color: ColorManager.black)),
                      Row(
                        children: [
                          littleTag("支持7天无理由退货", ColorManager.red),
                          littleTag("跨店铺满减", ColorManager.red)],
                      ),
                    ],
                  ),

                )

            );

          }
          return null;
        });
  }
}