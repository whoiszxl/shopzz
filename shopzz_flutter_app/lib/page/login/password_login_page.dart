import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:shopzz_flutter_app/controller/member_page_controller.dart';
import 'package:shopzz_flutter_app/page/detail/widgets/bottom_button.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/router/router_manager.dart';
import 'package:shopzz_flutter_app/widgets/base_scaffold.dart';
import 'package:shopzz_flutter_app/widgets/my_app_bar.dart';

///密码登录页面
class PasswordLoginPage extends StatefulWidget {
  const PasswordLoginPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _PasswordLoginPageState();
  }
}

class _PasswordLoginPageState extends State<PasswordLoginPage>{

  bool pwdToogle = false;
  final GlobalKey _formKey = GlobalKey<FormState>();
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _pwdController = TextEditingController();

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

    return BaseScaffold(
      appBar: MyAppBar(
        leadingType: AppBarBackType.None,
        backgroundColor: ColorManager.transparent,
        elevation: 0,
      ),

      body: GestureDetector(
        behavior: HitTestBehavior.translucent,
        onTap: () => FocusScope.of(context).requestFocus(FocusNode()),

        child: Container(
          height: MediaQuery.of(context).size.height,
          padding: const EdgeInsets.symmetric(vertical: 0.0, horizontal: 30.0),

          child: Form(
            autovalidateMode: AutovalidateMode.disabled,
            key: _formKey,
            child: Column(
              children: <Widget>[

                //1. LOGO
                SizedBox(
                  height: MediaQuery.of(context).size.height * 0.25,
                  child: Column(mainAxisAlignment: MainAxisAlignment.center, children: const [
                    Text("SHOPZZ登录", style: TextStyle(fontSize: 28, fontWeight: FontWeight.bold, color: ColorManager.main)),
                  ]),
                ),

                //2. 手机号、用户名输入框
                _buildPhoneInput(),

                const SizedBox(height: 12),

                //3. 密码输入框
                _buildPasswordInput(),

                const SizedBox(height: 12),

                //4. 登录按钮
                BottomButton(text: '登录', handleOk: () => _loginAction(context)),

                const SizedBox(height: 18),

                //5. 找回密码和新用户注册按钮
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    InkWell(
                      onTap: () => {},
                      child: const Text('找回密码', style: TextStyle(fontSize: 14, color: ColorManager.fontGrey)),
                    ),
                    const SizedBox(width: 14),

                    const SizedBox(
                      width: 1,
                      height: 16,
                      child: DecoratedBox(decoration: BoxDecoration(color: ColorManager.grey)),
                    ),
                    const SizedBox(width: 14),

                    InkWell(
                      onTap: () => {},
                      child: const Text('新用户注册', style: TextStyle(fontSize: 14, color: ColorManager.black),
                      ),
                    ),
                  ],
                ),

              ],
            ),
          ),
        ),
      )
    );
  }

  void _loginAction(BuildContext context) async {
    if ((_formKey.currentState as FormState).validate()) {
      var name = _nameController.value.text;
      var password = _pwdController.value.text;

      if(name == null || name.isEmpty) {
        showToast("账号不能为空");
        return;
      }
      if(password == null || password.isEmpty){
        showToast("密码不能为空");
        return;
      }
      var loginFlag = await memberPageController.login(name, password);

      //showToast(_nameController.value.text + "::" +  _pwdController.value.text);

      if(!loginFlag){
        showToast("用户名或密码错误");
        return;
      }
    }
  }

  // 号码
  Widget _buildPhoneInput() {
    return TextFormField(
        controller: _nameController,
        keyboardType: TextInputType.phone,
        decoration: InputDecoration(
          suffixIcon: GestureDetector(
            onTap: () => _nameController.clear(),
            child: _nameController.text != '' ? const Icon(Icons.cancel, size: 18) : const SizedBox(),
          ),
          contentPadding: const EdgeInsets.symmetric(vertical: 5),
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(5), borderSide: BorderSide.none),
          filled: true,
          fillColor: ColorManager.fieldBg,
          hintText: "请输入手机号码",
          prefixIcon: const Icon(Icons.phone_android_rounded, color: ColorManager.fontGrey),
        ),
        cursorColor: ColorManager.main,
        // 校验用户名
        validator: (v) {
          String t = v ?? '';
          return t.trim().isNotEmpty ? null : "用户名不能为空";
        });
  }

  // 密码
  Widget _buildPasswordInput() {
    return TextFormField(
        controller: _pwdController,
        keyboardType: TextInputType.visiblePassword,
        decoration: InputDecoration(
          suffixIcon: Row(
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.end,
            children: <Widget>[
              _pwdController.text != ''
                  ? GestureDetector(onTap: () => _pwdController.clear(), child: const Icon(Icons.cancel, size: 18, color: ColorManager.grey))
                  : const SizedBox(),
              const SizedBox(width: 10),
              GestureDetector(
                onTap: () => {
                  setState(() {
                    pwdToogle = !pwdToogle;
                  })
                },
                child: const Icon(Icons.remove_red_eye, size: 18, color: ColorManager.grey),
              ),
              const SizedBox(width: 15),
            ],
          ),
          contentPadding: const EdgeInsets.symmetric(vertical: 5),
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(5), borderSide: BorderSide.none),
          filled: true,
          fillColor: ColorManager.fieldBg,
          hintText: "请输入密码",
          prefixIcon: const Icon(Icons.security_outlined, color: ColorManager.fontGrey),
        ),
        obscureText: !pwdToogle,
        cursorColor: ColorManager.main,
        //校验密码
        validator: (v) {
          String t = v ?? '';
          return t.trim().length > 5 ? null : "密码不能少于6位";
        });
  }
}