import 'package:flutter/material.dart';
import 'package:flutter_login/flutter_login.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/controller/member_page_controller.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';

///登录页面
class LoginPage extends StatefulWidget {
  const LoginPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _LoginPageState();
  }
}

class _LoginPageState extends State<LoginPage>{

  final MemberPageController memberPageController = Get.find();
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
    return FlutterLogin(
      title: '',
      logo: 'images/logo.png',
      onLogin: _execLogin,
      onSignup: _execRegister,
      userType: LoginUserType.phone,
      userValidator: usernameValidator,
      passwordValidator: passwordValidator,
      showDebugButtons: false,
      theme: LoginTheme(
        primaryColor: ColorManager.black,
        footerBackgroundColor: ColorManager.main,
      ),
      onSubmitAnimationCompleted: () {
        //Get.toNamed(Routers.member);
      },
      onRecoverPassword: _recoverPassword,
      messages: LoginMessages(
        userHint: "用户名",
        passwordHint: "密码",
        loginButton: "登录",
        signupButton: "注册",
        forgotPasswordButton: "忘记密码？",
        goBackButton: "返回",

      ),

    );
  }


  // ignore: prefer_function_declarations_over_variables
  static final FormFieldValidator<String> usernameValidator = (value) {
    if (value == null || value.isEmpty) {
      return '无效的用户名';
    }else {
      return null;
    }
  };

  // ignore: prefer_function_declarations_over_variables
  static final FormFieldValidator<String> passwordValidator = (value) {
    if (value == null || value.isEmpty) {
      return '请输入密码';
    }else if(value.length < 6){
      return '密码不能小于6位';
    }else {
      return null;
    }
  };


  Future<String> _execRegister(LoginData data) async {
    return Future.delayed(Duration.zero).then((_) async {
      if(data.name == null || data.name.isEmpty) {
        return "用户名不能为空";
      }
      if(data.password == null || data.password.isEmpty){
        return "密码不能为空";
      }

      //登录逻辑
      var registerFlag = await memberPageController.paswordRegister(data.name, data.password, data.password);
      if(!registerFlag){
        return "注册失败";
      }
      return null;
    });

  }

  Future<String> _execLogin(LoginData data) async {
    return Future.delayed(Duration.zero).then((_) async {
      if(data.name == null || data.name.isEmpty) {
        return "用户名不能为空";
      }
      if(data.password == null || data.password.isEmpty){
        return "密码不能为空";
      }

      //登录逻辑
      var loginFlag = await memberPageController.login(data.name, data.password);

      if(!loginFlag){
        return "用户名或密码错误";
      }
      return null;
    });

  }

  Future<String> _recoverPassword(String name) {
    return Future.value("dsaafas");
  }
}