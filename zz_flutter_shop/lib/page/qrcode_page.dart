import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:qr_flutter/qr_flutter.dart';
import 'package:zz_flutter_shop/controller/member_page_controller.dart';
import 'package:zz_flutter_shop/page/widget/member/qrcode_appbar.dart';
import 'package:zz_flutter_shop/page/widget/product/my_navigation_bar.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';

///我的二维码页面
class QrcodePage extends StatefulWidget {
  const QrcodePage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _QrcodePageState();
  }
}

class _QrcodePageState extends State<QrcodePage>{

  final MemberPageController _memberPageController = Get.find<MemberPageController>();

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(

        children: [
          MyNavigationBar(
            height: 30,
            color: ColorManager.white,
            statusStyle: StatusStyle.DARK_CONTENT,
            child: qrcodeAppBar(context),
          ),

          Obx(() => Padding(
              padding: const EdgeInsets.only(top: 100),
              child: Column(
                children: <Widget>[
                  Padding(
                      padding: const EdgeInsets.only(bottom: 20),
                      child: Text(_memberPageController.memberInfoResponse.value.member.username, style: const TextStyle(fontSize: 28, fontWeight: FontWeight.bold))
                  ),

                  Center(
                    child: SizedBox(
                      width: MediaQuery.of(context).size.width - 50,
                      height: 400,
                      child: DecoratedBox(
                          decoration: const BoxDecoration(color: ColorManager.main),
                          child: Center(
                            child: QrImage(
                              data: _memberPageController.memberInfoResponse.value.member.id.toString(),
                              size: 280,
                              backgroundColor: ColorManager.white,
                            ),
                          )
                      )
                  ),),

                  const Padding(
                      padding: EdgeInsets.only(top: 20),
                      child: Text("用手机SHOPZZ扫一扫二维码，加我为好友", style: TextStyle(fontSize: 14, color: ColorManager.grey, fontWeight: FontWeight.bold))
                  ),

                ],
              )
          ))

        ]
      ),
    );
  }
}