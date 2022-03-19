import 'package:flutter/material.dart';
import 'package:oktoast/oktoast.dart';
import 'package:zz_flutter_shop/entity/response/home_app_index_response.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';

class MemberGridNavigator extends StatelessWidget {

  final List<NavigationEntity> navigatorList;

  final double gridHeight;
  final double gridWidth;

  const MemberGridNavigator({Key key, this.navigatorList, this.gridHeight = 160, this.gridWidth = 375}) : super(key: key);


  @override
  Widget build(BuildContext context) {
    //移除多余的导航框
    if(navigatorList.length > 10) {
      navigatorList.removeRange(10, navigatorList.length);
    }

    var tempIndex = -1;
    return Container(
      margin: const EdgeInsets.only(top: 20),
      height: gridHeight,
      width: gridWidth,
      padding: const EdgeInsets.all(3),

      child: GridView.count(
        physics: const NeverScrollableScrollPhysics(),
        crossAxisCount: 5,
        padding: const EdgeInsets.all(1),
        children: navigatorList.map((item) {
          tempIndex++;
          return _item(context, item, tempIndex);
        }).toList(),
      ),
    );
  }


  ///构建网格导航中的每个方块栏
  Widget _item(BuildContext context, NavigationEntity item, index) {
    return InkWell(
      onTap: () {
        //TODO 创建点击事件
        showToast("点击了" + item.name);
      },

      child: Column(
        children: <Widget>[
          Image.network(
            item.pic,
            width: 30,
            height: 30),

          Text(item.name, style: const TextStyle(color: ColorManager.black, fontSize: 12))
        ],
      ),
    );
  }

}