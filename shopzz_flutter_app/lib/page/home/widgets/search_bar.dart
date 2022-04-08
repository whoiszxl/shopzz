import 'package:flutter/material.dart';
import 'package:oktoast/oktoast.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/utils/qrcode_util.dart';

/// 首页搜索栏
class SearchBar extends StatelessWidget {

  const SearchBar({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // 装饰盒（`DecoratedBox`）组件，在子组件绘画之前或之后绘制装饰的组件。
    return DecoratedBox(
      // 装饰（`decoration`）属性，具体怎么画装饰。
      decoration: const BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topRight,
            colors: <Color>[
              ColorManager.main, Color.fromARGB(255,238,78,20)
            ],
          )
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: <Widget>[

          //二维码扫描图标
          _icon(Icons.crop_free, () {
            QrcodeUtil.getQrcodeState().then((value) => showToast(value));
          }),

          //搜索框
          Expanded(
              child: _searchField("搜一搜吧！", () {
                showToast("点击了");
              })
          ),

          //消息小图标
          _icon(Icons.chat_outlined, () {
            showToast("点击了");
          }),

          const SizedBox(height: 50.0),
        ],
      ),
    );
  }

  ///搜索框
  _searchField(text, onTap) {
    return InkWell(
      onTap: onTap,
      child: Container(
        height: 28.0,
        decoration: const BoxDecoration(
          color: ColorManager.white,
          borderRadius: BorderRadius.all(Radius.circular(5.0)),
        ),
        child: Padding(
          padding: const EdgeInsets.only(left: 10),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: <Widget>[
              const Icon(Icons.search, size: 18.0, color: ColorManager.fontGrey),
              Text(text, style: const TextStyle(color: ColorManager.fontGrey, fontSize: 13.0)),
            ],
          ),
        ),
      ),
    );
  }

  ///小图标
  _icon(logo, onTap) {
    return InkWell(
      onTap: onTap,
      child: Padding(
        padding: const EdgeInsets.only(right: 15, left: 15),
        child: Icon(logo, size: 25, color: ColorManager.white),
      ),
    );
  }
}