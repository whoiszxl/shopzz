import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/controller/cart_page_controller.dart';
import 'package:zz_flutter_shop/events/cart_select_all_event.dart';
import 'package:zz_flutter_shop/page/widget/common/round_checkbox.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/application.dart';

class OrderConfirmFooter extends StatelessWidget {

  Function submitOrderFunction;

  OrderConfirmFooter(this.submitOrderFunction, {Key key}) : super(key: key);

  final CartPageController _cartPageController = Get.find<CartPageController>();

  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      padding: const EdgeInsets.only(left: 5),
      height: 60,
      decoration: const BoxDecoration(
        color: ColorManager.white,
        border: Border(
            top: BorderSide(
          color: ColorManager.main,
          width: 0.2,
        )),
      ),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[

          Obx(() => Expanded(flex: 5, child: Text("合计：¥" + _cartPageController.totalAmount.value.toString() + "元"))),
          Expanded(
            flex: 4,
            child: Container(
              margin: const EdgeInsets.fromLTRB(10, 0, 10, 0),
              child: FlatButton(
                color: ColorManager.red,
                highlightColor: Colors.black12,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10)),
                child: Text(
                  "去结算(" + _cartPageController.totalQuantity.value.toString() + ")",
                  style: const TextStyle(color: ColorManager.white),
                ),
                onPressed: () {
                  submitOrderFunction();
                },
              ),
            ),
          ),
        ],
      ),
    );
  }
}
