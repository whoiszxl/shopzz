import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:qr_flutter/qr_flutter.dart';
import 'package:shopzz_flutter_app/controller/member_page_controller.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/widgets/base_scaffold.dart';
import 'package:shopzz_flutter_app/widgets/my_app_bar.dart';

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
    return BaseScaffold(
      appBar: MyAppBar(
        leadingType: AppBarBackType.None,
        backgroundColor: ColorManager.fieldBg,
        elevation: 0,
      ),

      body: Stack(

          children: [
            Obx(() {
              return Container(
                color: ColorManager.fieldBg,
                  padding: const EdgeInsets.only(top: 50),
                  child: Column(
                    children: <Widget>[
                      Padding(
                          padding: const EdgeInsets.only(bottom: 20),
                          child: Text(_memberPageController.memberInfoResponse.value.username, style: const TextStyle(fontSize: 28, fontWeight: FontWeight.bold))
                      ),

                      Center(
                        child: SizedBox(
                            width: MediaQuery.of(context).size.width - 50,
                            height: 400,
                            child: DecoratedBox(
                                decoration: const BoxDecoration(color: ColorManager.main),
                                child: Center(
                                  child: QrImage(
                                    data: _memberPageController.memberInfoResponse.value.memberId.toString(),
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

                      Padding(

                        padding: EdgeInsets.only(top: 80),
                        child: Row(
                        children: <Widget>[

                          _qrcodeButton("分享", Icons.link_outlined),

                          const SizedBox(width: 70),

                          _qrcodeButton("保存", Icons.download_outlined),

                          const SizedBox(width: 70),

                          _qrcodeButton("扫扫", Icons.crop_free),
                        ],

                          mainAxisAlignment: MainAxisAlignment.center,
                      ),)
                    ],
                  )
              );

            })

          ]
      ),
    );

  }

  _qrcodeButton(String text,IconData icon) {
    return Column(
      children: <Widget>[
        Icon(icon, size: 38, color: ColorManager.grey),
        Text(text, style: const TextStyle(fontSize: 14, color: ColorManager.grey, fontWeight: FontWeight.bold)),
      ],
    );
  }
}