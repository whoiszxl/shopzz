import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:roundcheckbox/roundcheckbox.dart';
import 'package:shopzz_flutter_app/controller/cart_page_controller.dart';
import 'package:shopzz_flutter_app/controller/member_address_controller.dart';
import 'package:shopzz_flutter_app/controller/order_confirm_page_controller.dart';
import 'package:shopzz_flutter_app/page/pay/widgets/pay_footer.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/router/router_manager.dart';
import 'package:shopzz_flutter_app/utils/loading_util.dart';
import 'package:shopzz_flutter_app/widgets/base_scaffold.dart';
import 'package:shopzz_flutter_app/widgets/my_app_bar.dart';

///支付页面
class PayPage extends StatefulWidget {
  const PayPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _PayPageState();
  }
}

class _PayPageState extends State<PayPage>{

  PayPageController payPageController = Get.put(PayPageController());
  MemberAddressController memberAddressController = Get.put(MemberAddressController());
  final CartPageController _cartPageController = Get.find<CartPageController>();
  final RefreshController _refreshController = RefreshController(initialRefresh: false);

  int payType = 1;

  @override
  void initState() {
    super.initState();

    //调用提交订单接口，并跳转到支付方式选择界面
    payPageController.orderSubmit(
        memberAddressController.addressResponse.value.mainAddress.id.toString(),
        "TODO COMMENT",
        null,
        _cartPageController.totalAmount.value.toString());
  }

  @override
  void dispose() {
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    return Obx(() {
      if(payPageController.loadingFlag.isTrue) {
        return normalLoading();
      }

      if(payPageController.loadingFlag.isFalse) {
        if(payPageController.submitFlag.isFalse) {
          return const Center(child: Text("支付失败"));
        }
      }

      return BaseScaffold(
        appBar: MyAppBar(
          title: const Text("SHOPZZ收银台"),
          backgroundColor: ColorManager.main,
          elevation: 0,
        ),

        body: Scaffold(
          bottomSheet: PayFooter(() {
            //跳转支付页面
            Get.toNamed(Routers.dcwalletPay);
          }),

          body: SmartRefresher(
            enablePullDown: true, //开启下拉
            enablePullUp: false, //关闭上拉
            header: const ClassicHeader(),
            footer: const ClassicFooter(),
            controller: _refreshController,
            onRefresh: () {
              _refreshController.refreshCompleted();
            },

            child: Column(
              children: <Widget>[

                Padding(
                  padding: const EdgeInsets.only(top: 15, left: 10, right: 10),
                  child: Column(
                    children: <Widget>[
                      RichText(
                        text: TextSpan(text: "¥",
                            style: const TextStyle(color: ColorManager.main, fontWeight: FontWeight.bold, fontSize: 18),
                            children: <TextSpan>[
                              TextSpan(
                                text: _cartPageController.totalAmount.value.toString(),
                                style: const TextStyle(color: ColorManager.main, fontWeight: FontWeight.bold, fontSize: 24),
                              ),
                            ]),
                      ),
                    ],
                  ),
                ),

                //支付方式
                Padding(
                  padding: const EdgeInsets.only(top: 15, left: 10, right: 10),
                  child: Column(
                    children: [
                      payTile(Icons.attach_money_outlined, "SHOPZZ币支付", 1, isDefault: false),
                      const SizedBox(height: 3),
                      payTile(Icons.attach_money_outlined, "ETH支付", 2, isDefault: true),
                      const SizedBox(height: 3),
                      payTile(Icons.attach_money_outlined, "BTC支付", 3, isDefault: false),
                    ],
                  ),
                ),

              ],
            )
          ),

        ),

      );
    });
  }

  ///支付方式item
  payTile(IconData icon, String title, int payType, {isDefault = false}) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [

        Row(
          children: [
            Icon(icon, size: 20, color: ColorManager.main),
            RichText(
              text: TextSpan(children: [
                WidgetSpan(
                  child: Container(
                    padding: const EdgeInsets.only(left: 2, right: 2, top: 0, bottom: 1),
                    child: Text(title, style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w500)),
                  ),
                ),
                const WidgetSpan(child: SizedBox(width: 3)),
              ]),
            ),
          ],
        ),

        Row(
          children: [
            littleTag("安全支付", ColorManager.main),
            const SizedBox(width: 3),
            RoundCheckBox(
              isChecked: this.payType == payType,
              size: 20,
              checkedWidget: const Icon(Icons.check, size: 18, color: ColorManager.white),
              checkedColor: ColorManager.main,
              onTap: (selected) {
                if(selected) {
                  setState(() {
                    this.payType = payType;
                  });
                }
              },
            )
          ],
        ),
      ],
    );
  }


  Widget littleTag(String text, Color colors) {
    return Container(
        padding: const EdgeInsets.only(left: 2, right: 2, top: 0, bottom: 0),
        decoration: BoxDecoration(border: Border.all(color: colors)),
        child: Text(text, style: TextStyle(
          color: colors,
          fontSize: 9,
          fontWeight: FontWeight.w600,
        )));
  }
}