import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/controller/member_page_controller.dart';
import 'package:zz_flutter_shop/page/member_page.dart';
import 'package:zz_flutter_shop/page/widget/product/my_navigation_bar.dart';
import 'package:zz_flutter_shop/page/widget/settings/settings_appbar.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/utils/image_util.dart';

///设置页面
class SettingsPage extends StatefulWidget {
  const SettingsPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _SettingsPageState();
  }
}

class _SettingsPageState extends State<SettingsPage>{

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

          //appbar
          MyNavigationBar(
            height: 50,
            child: settingsAppBar(context),
            color: ColorManager.white,
            statusStyle: StatusStyle.LIGHT_CONTENT,
          ),


          Padding(
            padding: const EdgeInsets.only(top: 80),
            child: SingleChildScrollView(
              child: Column(
                children: <Widget>[
                  memberInfoTile(),
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
  memberInfoTile() {
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
              backgroundImage: NetworkImage(_memberPageController.memberInfoResponse.value.memberInfo.profilePhoto),

            ),

            title: Text(_memberPageController.memberInfoResponse.value.member.username),
            subtitle: Text('账号ID: ' + _memberPageController.memberInfoResponse.value.member.id.toString()),
            trailing: const Icon(Icons.navigate_next, color: ColorManager.grey),
          ),
          _border(),
          const ListTile(
            title: Text("我的收货地址"),
            trailing: Icon(Icons.navigate_next),
          )
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