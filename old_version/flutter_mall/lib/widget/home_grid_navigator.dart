
import 'package:flustars/flustars.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/model/index_banner_model.dart';
import 'package:flutter_mall/util/toast.dart';

class HomeGridNavigator extends StatelessWidget {

  final List<Navigations> navigatorList;

  HomeGridNavigator({Key key, this.navigatorList}) : super(key: key);


  @override
  Widget build(BuildContext context) {
    //移除多余的导航框
    if(navigatorList.length > 10) {
      navigatorList.removeRange(10, navigatorList.length);
    }

    var tempIndex = -1;
    return Container(
      margin: EdgeInsets.only(top: 20),
      height: ScreenUtil.getInstance().getHeight(132),
      width: ScreenUtil.getInstance().screenWidth,
      padding: EdgeInsets.all(3),

      child: GridView.count(
        physics: NeverScrollableScrollPhysics(),
        crossAxisCount: 5,
        padding: EdgeInsets.all(1),
        children: navigatorList.map((item) {
          tempIndex++;
          return _item(context, item, tempIndex);
        }).toList(),
      ),
    );
  }


  ///构建网格导航中的每个方块栏
  Widget _item(BuildContext context, Navigations item, index) {
    return InkWell(
      onTap: () {
        //TODO 创建点击事件
        showToast("点击了" + item.name);
      },

      child: Column(
        children: <Widget>[
          //Icon(Icons.account_balance),
          Image.network(
              item.pic,
              width: ScreenUtil.getInstance().getWidth(30),
              height: ScreenUtil.getInstance().getHeight(30),),

          Text(item.name, style: TextStyle(color: Colors.grey))
        ],
      ),
    );
  }

}