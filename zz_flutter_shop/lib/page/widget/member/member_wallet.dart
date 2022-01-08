import 'package:flutter/material.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';

///会员资产组件
class MemberWallet extends StatelessWidget {

  MemberWallet({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    return Container(
      height: 100,
      width: double.infinity,
      margin: const EdgeInsets.only(top: 10),
      decoration: const BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.all(Radius.circular(10)),
      ),

      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [

          walletTab("1877", "积分", "签到领积分", Colors.black, () {}),
          walletTab("14", "优惠券", "加购即送", Colors.black, () {}),
          walletTab("1900.00", "数字货币", "USDT购买", Colors.black, () {}),
          walletTab("166", "邀请人数", "邀请有礼", Colors.black, () {}),
          walletTab(Icons.account_balance_wallet_outlined, "我的钱包", "", ColorManager.orange, () {}),

        ],
      ),

    );

  }


  walletTab(var tabIcon, String tabName, String subTabName, Color color, Function function) {
    return Expanded(
      flex: 1,
      child: GestureDetector(
        onTap: function,
        child: Container(
          height: 65,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: <Widget>[

              //ICON or value
              tabIcon is IconData ? Icon(tabIcon, size: 30, color: color)
                  : Text(tabIcon, style: const TextStyle(color: Colors.black,
                  fontSize: 16,
                  fontWeight: FontWeight.bold)),

              SizedBox(
                height: 35,
                child: Column(
                  children: [
                    //主标题
                    Text(tabName, style: TextStyle(fontSize: 12, color: color)),

                    //副标题
                    Text(subTabName, style: const TextStyle(fontSize: 10, color: Colors.grey)),
                  ],
                ),
              ),

            ],
          ),
        ),
      ),
    );
  }
}