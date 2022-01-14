import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:zz_flutter_shop/controller/cart_page_controller.dart';
import 'package:zz_flutter_shop/page/widget/product/my_navigation_bar.dart';
import 'package:zz_flutter_shop/page/widget/settings/normal_appbar.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';

import 'widget/common/round_checkbox.dart';

///支付收银台页面
class PayCounterPage extends StatefulWidget {
  const PayCounterPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _PayCounterPageState();
  }
}

class _PayCounterPageState extends State<PayCounterPage> {

  final CartPageController _cartPageController = Get.find<CartPageController>();

  String currentPayType = "SHOPZZ";

  String totalAmount = "";

  @override
  void initState() {
    var parameters = Get.parameters;
    String totalAmount = parameters['totalAmount'];
    if(totalAmount != null && totalAmount.isNotEmpty) {
      this.totalAmount = totalAmount;
    }
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Obx(() {
      return Scaffold(
        bottomSheet: Container(
          width: MediaQuery.of(context).size.width,
          padding: const EdgeInsets.only(left: 5),
          height: 60,
          decoration: const BoxDecoration(
            color: ColorManager.white,
            border:
                Border(top: BorderSide(color: ColorManager.main, width: 0.2)),
          ),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
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

                      "支付" + _cartPageController.totalAmount.toString(),
                      style: const TextStyle(color: ColorManager.white),
                    ),
                    onPressed: () {
                      showToast(currentPayType);
                      setState(() {

                      });
                    },
                  ),
                ),
              ),
            ],
          ),
        ),
        body: Column(children: [
          //appbar
          MyNavigationBar(
            height: 50,
            child: normalAppBar(context, "shopzz收银台",
                titleColor: ColorManager.white),
            color: ColorManager.red,
            statusStyle: StatusStyle.LIGHT_CONTENT,
          ),

          Padding(
            padding: const EdgeInsets.only(top: 8),
            child: Center(
                child: Text(_cartPageController.totalAmount.toString(),
                    style: const TextStyle(
                        color: ColorManager.red,
                        fontSize: 30,
                        fontWeight: FontWeight.bold))),
          ),

          Padding(
              padding: const EdgeInsets.only(top: 8),
              child: Container(
                decoration: const BoxDecoration(
                    color: ColorManager.white,
                    borderRadius: BorderRadius.only(
                        bottomLeft: Radius.circular(8),
                        bottomRight: Radius.circular(8))),

                child: Column(
                  children: <Widget>[
                    const ListTile(
                      leading: CircleAvatar(
                        radius: 20,
                        backgroundColor: ColorManager.white,
                        backgroundImage: AssetImage("images/logo2.png"),
                      ),
                      title: Text("SHOPZZ"),
                      //trailing: _checkBox("SHOPZZ", isChecked: currentPayType == "SHOPZZ"),
                      trailing: Icon(Icons.navigate_next),
                    ),
                    _border(),
                    const ListTile(
                      leading: CircleAvatar(
                        radius: 20,
                        backgroundColor: ColorManager.white,
                        backgroundImage: NetworkImage(
                            "http://zero-mall.oss-cn-shenzhen.aliyuncs.com/logo/BTC.png"),
                      ),
                      title: Text("BTC"),
                      //trailing: _checkBox("BTC", isChecked: currentPayType == "BTC"),
                      trailing: Icon(Icons.navigate_next),
                    ),
                    _border(),
                    ListTile(
                      leading: const CircleAvatar(
                        radius: 20,
                        backgroundColor: ColorManager.white,
                        backgroundImage: NetworkImage(
                            "http://zero-mall.oss-cn-shenzhen.aliyuncs.com/logo/ETH.png"),
                      ),
                      title: const Text("ETH"),
                      //trailing: _checkBox("ETH", isChecked: currentPayType == "ETH"),
                      trailing: const Icon(Icons.navigate_next),
                      onTap: () {
                        Map<String,String> map = HashMap();
                        map['dcName'] = "ETH";
                        Get.toNamed(Routers.payDc, parameters: map);
                      },
                    ),
                    _border(),
                    const ListTile(
                      leading: CircleAvatar(
                        radius: 20,
                        backgroundColor: ColorManager.white,
                        backgroundImage: NetworkImage(
                            "http://zero-mall.oss-cn-shenzhen.aliyuncs.com/logo/ZHIFUBAO.png"),
                      ),
                      title: Text("支付宝"),
                      //trailing: _checkBox("ZHIFUBAO", isChecked: currentPayType == "ZHIFUBAO"),
                      trailing: Icon(Icons.navigate_next),
                    ),
                    _border(),
                    const ListTile(
                      leading: CircleAvatar(
                        radius: 20,
                        backgroundColor: ColorManager.white,
                        backgroundImage: NetworkImage(
                            "http://zero-mall.oss-cn-shenzhen.aliyuncs.com/logo/WEIXIN.png"),
                      ),
                      title: Text("微信支付"),
                      //trailing: _checkBox("WEIXIN", isChecked: currentPayType == "WEIXIN"),
                      trailing: Icon(Icons.navigate_next),
                    ),
                  ],
                ),
              )),
        ]),
      );
    });
  }

  Widget _checkBox(String payType, {isChecked = false}) {
    return RoundCheckbox(
        value: isChecked,
        backgroundColor: ColorManager.white,
        activeColor: ColorManager.red,
        checkColor: ColorManager.white,
        activeBorderColor: ColorManager.red,
        onChanged: (status) {
          setState(() {
            currentPayType = payType;
          });
        });
  }

  Widget _border() {
    return const Divider(height: 0.6, color: ColorManager.grey);
  }
}
