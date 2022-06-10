
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:shopzz_flutter_app/controller/cart_page_controller.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';

class OrderConfirmFooter extends StatelessWidget {
  Function addCartFunction;

  OrderConfirmFooter(this.addCartFunction, {Key key}) : super(key: key);

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

          Obx(() => Expanded(flex: 6, child: Text("合计：¥" + _cartPageController.totalAmount.value.toString() + "元"))),

          Expanded(
            flex: 4,
            child: Container(
              margin: const EdgeInsets.fromLTRB(10, 0, 10, 0),
              child: FlatButton(
                color: ColorManager.red,
                highlightColor: Colors.black12,
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
                child: const Text("下单", style: TextStyle(color: ColorManager.white)),
                onPressed: () {
                  addCartFunction();
                },
              ),
            ),
          ),
        ],
      ),
    );
  }

}