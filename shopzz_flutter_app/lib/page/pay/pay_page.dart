import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
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
              Get.toNamed(Routers.pay);
            }),

            body: SmartRefresher(
              enablePullDown: true, //开启下拉
              enablePullUp: false, //关闭上拉
              header: const ClassicHeader(),
              footer: const ClassicFooter(),
              controller: _refreshController,
              onRefresh: () {

              }, //下拉时调用

              child: Column(
                children: <Widget>[
                  Text("BTC支付"),
                  Text("ETH支付"),
                  Text("SHOPZZ支付"),
                ],
              ),
            ),
          ),
      );
    });
  }
}