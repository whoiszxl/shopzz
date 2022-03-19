import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/dao/member_dao.dart';
import 'package:flutter_mall/http/core/zero_error.dart';
import 'package:flutter_mall/navigator/zero_navigator.dart';
import 'package:flutter_mall/util/string_util.dart';
import 'package:flutter_mall/util/toast.dart';
import 'package:flutter_mall/util/validate_util.dart';
import 'package:flutter_mall/widget/appbar.dart';
import 'package:flutter_mall/widget/login_button.dart';
import 'package:flutter_mall/widget/login_input.dart';

///登录页面
class LoginPage extends StatefulWidget {

  @override
  _LoginPageState createState() => _LoginPageState();
}


class _LoginPageState extends State<LoginPage> {

  bool protect = false;
  bool loginEnable = false;
  String username;
  String password;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: appBar("登录", "注册", () {
        ZeroNavigator.getInstance().onJumpTo(RouteStatus.register);
      }),
      body: Container(
        child: ListView(
          children: [

            NormalInput(
                '用户名',
                '请输入用户名或手机号',
                onChanged: (text) {
                  username = text;
                  checkInput();
                },
            ),
            NormalInput(
              '密码',
              '请输入密码',
              obscureText: true,
              onChanged: (text) {
                password = text;
                checkInput();
              },

              focusChanged: (focus) {
                this.setState(() {
                  protect = focus;
                });
              },
            ),

            Padding(
              padding: EdgeInsets.only(left: 20, right: 20, top: 20),
              child: LoginButton(
                '登录',
                enable: loginEnable,
                onPressed: send,
              ),
            )

          ],
        ),
      ),
    );
  }

  //校验输入参数
  void checkInput() {
    setState(() {
      loginEnable = (isNotEmpty(username) && isNotEmpty(password)) ;
    });
  }

  //发送请求
  void send() async {
    try{
      var result = await MemberDao.login(username, password);
      if(verifyResult(result)) {
        showToast("登录成功");
        ZeroNavigator.getInstance().onJumpTo(RouteStatus.home);
      }else {
        showErrorToast(result['message']);
      }
    }on NeedAuth catch(e) {
      showErrorToast(e.message);
    }on ZeroNetError catch(e) {
      showErrorToast(e.message);
    }
  }

}