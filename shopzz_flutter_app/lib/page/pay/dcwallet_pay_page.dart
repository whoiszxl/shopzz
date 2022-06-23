import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:qr_flutter/qr_flutter.dart';
import 'package:shopzz_flutter_app/page/pay/widgets/pay_footer.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/router/router_manager.dart';
import 'package:shopzz_flutter_app/widgets/base_scaffold.dart';
import 'package:shopzz_flutter_app/widgets/my_app_bar.dart';

///数字货币支付页面
class DcwalletPayPage extends StatefulWidget {
  const DcwalletPayPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _DcwalletPayPageState();
  }
}

class _DcwalletPayPageState extends State<DcwalletPayPage>{

  final RefreshController _refreshController = RefreshController(initialRefresh: false);

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
        title: const Text("SHOPZZ数字货币支付"),
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
              Padding(
                padding: const EdgeInsets.only(left: 5, top: 5, right: 5),
                child: _waitPayTile(),
              ),

              Padding(
                padding: const EdgeInsets.only(left: 5, top: 5, right: 5),
                child: Row(
                  children: [
                    _button("返回首页", () {}),
                    const SizedBox(width: 10,),
                    _button("查看订单", () {}),
                  ],
                ),
              ),

              Padding(
                padding: const EdgeInsets.only(left: 10, top: 20, right: 10),
                child: Container(
                    color: ColorManager.fieldBg,
                    padding: const EdgeInsets.only(top: 10),
                    child: Column(
                      children: <Widget>[
                        Padding(
                            padding: const EdgeInsets.only(bottom: 20),
                            child: Row(
                              children: const <Widget>[
                                Icon(Icons.attach_money, color: ColorManager.main,),
                                Text("BTC 18.99", style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: ColorManager.black))
                              ],
                            )
                        ),

                        Center(
                            child: SizedBox(
                                width: 200,
                                height: 200,
                                child: DecoratedBox(
                                    decoration: const BoxDecoration(color: ColorManager.bg),
                                    child: Center(
                                      child: QrImage(
                                        data: "qrcode",
                                        size: 180,
                                        backgroundColor: ColorManager.white,
                                      ),
                                    )
                                )
                            )
                        ),

                        Padding(
                            padding: const EdgeInsets.only(top: 8),
                            child: Container(
                              width: 340,
                              height: 30,
                              color: Colors.grey[300],
                              alignment: Alignment.center,
                              child: const Text(
                                  "地址: 0x36d429596a4DA28CD93d8CF3dE5e2557f27dA2A5",
                                  style: TextStyle(fontSize: 12, color: ColorManager.black, fontWeight: FontWeight.bold)),
                            )
                        ),


                        Padding(
                          padding: const EdgeInsets.only(top: 10),
                          child: _button("保存二维码", () {}, color: ColorManager.main),
                        ),


                        const Padding(
                          padding: EdgeInsets.only(top: 10),
                          child: Divider(height: 2),
                        ),


                        Padding(
                          padding: const EdgeInsets.only(top: 10),
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.end,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: const <Widget>[
                              Text("如何通过Shopzz进行支付"),
                              Text("1. 保存二维码到手机"),
                              Text("2. 选择对应的钱包进行扫描"),
                              Text("3. 扫描并进行支付"),
                              Text("4. 完成付款"),
                            ],
                          ),
                        )

                      ],
                    )
                ),
              )


            ],
          ),
        ),
      ),
    );
  }


  _button(title, function, {color = ColorManager.red}) {
    return FlatButton(
      color: color,
      highlightColor: Colors.black12,
      shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10)),
      child: Text(title, style: const TextStyle(color: ColorManager.white)),
      onPressed: function,
    );
  }

  ///待付款组件
  _waitPayTile() {
    return Row(
      children: <Widget>[

        Expanded(
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: const <Widget>[
              Text("待付款", style: TextStyle(fontWeight: FontWeight.w600, fontSize: 22, color: ColorManager.fontGrey)),
              SizedBox(width: 100),
              Icon(Icons.access_time, size: 28, color: ColorManager.main,)
            ],
          ),
        )


      ],
    );

  }

  _qrcodeButton(String text,IconData icon, onTap) {
    return InkWell(
      onTap: onTap,
      child: Column(
        children: <Widget>[
          Icon(icon, size: 38, color: ColorManager.grey),
          Text(text, style: const TextStyle(fontSize: 14, color: ColorManager.grey, fontWeight: FontWeight.bold)),
        ],
      ),
    );
  }
}