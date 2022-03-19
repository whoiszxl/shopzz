import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:qr_flutter/qr_flutter.dart';
import 'package:zz_flutter_shop/controller/order_controller.dart';
import 'package:zz_flutter_shop/page/widget/product/little_tag.dart';
import 'package:zz_flutter_shop/page/widget/product/my_navigation_bar.dart';
import 'package:zz_flutter_shop/page/widget/settings/normal_appbar.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/utils/clipboard_util.dart';
import 'package:zz_flutter_shop/utils/loading_util.dart';

///发现页面
class PayDcPage extends StatefulWidget {

  const PayDcPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _PayDcPageState();
  }
}

class _PayDcPageState extends State<PayDcPage>{

  final OrderController _orderController = Get.find<OrderController>();

  String dcName = "";

  @override
  void initState() {
    super.initState();
    Map<String, String> getParams = Get.parameters;
    dcName = getParams['dcName'];

    //获取DC支付的相关信息（二维码信息，地址等）
    _orderController.orderPayDc(_orderController.orderId.value, dcName);
  }

  @override
  void dispose() {
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    return Obx(() {
      if(_orderController.orderPayDcResponse.value == null) {
        return normalLoading();
      }else {
        return Scaffold(
          bottomSheet: Container(
            width: MediaQuery.of(context).size.width,
            padding: const EdgeInsets.only(left: 5),
            height: 60,
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                _bottomButton("保存图片", backColor: ColorManager.grey, textColor: ColorManager.black),
                _bottomButton("复制地址", backColor: ColorManager.orange, event: () {
                  ClipboardUtil.copy(_orderController.orderPayDcResponse.value.toAddress);
                }),
              ],
            ),
          ),

          body: Column(
            children: <Widget>[
              //appbar
              MyNavigationBar(
                height: 50,
                child: normalAppBar(context, ""),
                color: ColorManager.white,
                statusStyle: StatusStyle.LIGHT_CONTENT,
              ),

              //支付提示语
              Padding(
                padding: const EdgeInsets.only(top: 20, left: 10),
                child: Text("支付ETH:" + _orderController.orderPayDcResponse.value.totalAmount.toString(), style: const TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 26
                ))
              ),

              //支付二维码
              Padding(
                padding: const EdgeInsets.only(top: 20, left: 10),
                child: SizedBox(
                    width: MediaQuery.of(context).size.width - 50,
                    height: 220,
                    child: DecoratedBox(
                        decoration: const BoxDecoration(color: ColorManager.white70),
                        child: Center(
                          child: QrImage(
                            data: _orderController.orderPayDcResponse.value.qrcodeData,
                            size: 200,
                            backgroundColor: ColorManager.white,
                          ),
                        )
                    )
                )
              ),

              //支付地址
              Padding(
                  padding: const EdgeInsets.only(top: 20, left: 10),
                  child: RichText(
                    text: TextSpan(
                      children: [
                        TextSpan(text: dcName + "支付地址：\n", style: const TextStyle(color: ColorManager.grey)),
                        TextSpan(text: _orderController.orderPayDcResponse.value.toAddress, style: const TextStyle(fontWeight: FontWeight.w600, color: Colors.black)),
                        const WidgetSpan( child: SizedBox( width: 5, )),
                        WidgetSpan(
                          child: InkWell(
                            child: littleTag("点击复制", ColorManager.orange),
                            onTap: () {
                              ClipboardUtil.copy(_orderController.orderPayDcResponse.value.toAddress);
                            },
                          ),
                        ),
                      ],
                    ),
                  )
              ),

              //支付提示语
              Padding(
                  padding: const EdgeInsets.only(top: 50, left: 0),
                  child: RichText(
                    text: TextSpan(
                      children: [
                        const TextSpan(text: "注意：\n", style: TextStyle(color: ColorManager.grey)),
                        TextSpan(text: "· 禁止向" + dcName + "地址支付除" + dcName + "以外的资产。\n", style: const TextStyle(color: ColorManager.grey)),
                        const TextSpan(text: "· 支付需要等待12个网络确认才能到账，届时才能成功支付", style: TextStyle(color: ColorManager.grey)),
                      ],
                    ),
                  )
              ),
            ],
          ),
        );
      }
    });
  }


  Widget _bottomButton(String buttonText, {Color backColor = ColorManager.red, Color textColor = ColorManager.white, Function event}) {
    return Expanded(
      flex: 4,
      child: Container(
        margin: const EdgeInsets.fromLTRB(10, 0, 10, 0),
        child: FlatButton(
          color: backColor,
          highlightColor: Colors.black12,
          shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10)),
          child: Text(
            buttonText,
            style: TextStyle(color: textColor),
          ),
          onPressed: event,
        ),
      ),
    );
  }
}