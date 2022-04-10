import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:shopzz_flutter_app/controller/member_page_controller.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/router/router_manager.dart';
import 'package:shopzz_flutter_app/widgets/base_scaffold.dart';
import 'package:shopzz_flutter_app/widgets/my_app_bar.dart';

///设置页面
class SettingsPage extends StatefulWidget {
  const SettingsPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _SettingsPageState();
  }
}

final MemberPageController _memberPageController = Get.find<MemberPageController>();

class _SettingsPageState extends State<SettingsPage>{

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
        title: const Text("个人设置"),
        leadingType: AppBarBackType.Back,
        backgroundColor: ColorManager.fieldBg,
        elevation: 0,
      ),
      body: Stack(
          children: [


            Padding(
              padding: const EdgeInsets.only(top: 1),
              child: SingleChildScrollView(
                child: Column(
                  children: <Widget>[

                    memberInfoTile(() {
                      Get.toNamed(Routers.address);
                    }),

                    Padding(padding: const EdgeInsets.only(top: 10), child: otherTile([
                      myTile("账户与安全", () {}),_border(),
                      myTile("清除缓存", () {}),_border(),
                      myTile("主题更换", () {}),_border(),
                      myTile("语言选择", () {}),_border(),
                      myTile("关于我们", () {}),_border(),
                      myTile("隐私设置", () {}),
                    ])),

                    Padding(padding: const EdgeInsets.only(top: 10), child: otherTile([
                      myTile("退出登录", () {}),
                    ]),)
                  ],
                ),
              ),
            )
          ]
      ),
    );
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

  ///展示用户信息和收货地址选项
  memberInfoTile(onTap) {
    return Container(
      decoration: const BoxDecoration(
          color: ColorManager.white,
          borderRadius: BorderRadius.only(
              bottomLeft: Radius.circular(8),
              bottomRight: Radius.circular(8)
          )
      ),

      child: Column(
        children: <Widget>[
          ListTile(
            leading: CircleAvatar(
              radius: 28,
              backgroundColor: ColorManager.white,
              backgroundImage: NetworkImage(_memberPageController.memberInfoResponse.value.avatar),

            ),

            title: Text(_memberPageController.memberInfoResponse.value.username),
            subtitle: Text('账号ID: ' + _memberPageController.memberInfoResponse.value.memberId.toString()),
            trailing: const Icon(Icons.navigate_next, color: ColorManager.grey),
          ),
          _border(),
          InkWell(
            onTap: onTap,
            child: const ListTile(
              title: Text("我的收货地址"),
              trailing: Icon(Icons.navigate_next),
            ),
          ),
        ],
      ),
    );
  }

  Widget _border() {
    return const Divider(height: 0.6, color: ColorManager.grey);
  }

  Widget myTile(String title, Function() func) {
    return ListTile(
        title: Text(title),
        trailing: const Icon(Icons.navigate_next),
        onTap: func
    );
  }
}