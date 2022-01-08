import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:zz_flutter_shop/page/my_search_delegate.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';

class HomeHeader extends StatefulWidget {

  const HomeHeader({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => HomeHeaderState();
}

class HomeHeaderState extends State<HomeHeader> {

  @override
  void initState() {
    super.initState();
    //配置单次回调，在frame绘制完成后
    WidgetsBinding.instance?.addPostFrameCallback((_bottomBarLayout) {
      //配置系统的bar样式
      SystemChrome.setSystemUIOverlayStyle(const SystemUiOverlayStyle(
        statusBarColor: Colors.transparent,
        statusBarIconBrightness: Brightness.light,
      ));
    });
  }

  @override
  Widget build(BuildContext context) {
    return SliverAppBar(
      floating: true,
      pinned: true,
      leading: const Text(''),
      foregroundColor: ColorManager.black,
      backgroundColor: ColorManager.black,
      shadowColor: ColorManager.black,
      automaticallyImplyLeading: false,
      centerTitle: true,
      actions: [

        _headerButton(Icons.crop_free, () {
          showToast("掃描二維碼");
        }),

        _headerButton(Icons.message_rounded, () {
          showToast("查看消息");
          Get.toNamed(Routers.login);
        }),

      ],
      iconTheme: const IconThemeData(
        color: Colors.white,
        size: 30,
        opacity: 1,
      ),
      expandedHeight: 100.0,
      flexibleSpace: _searchInput(),
    );
  }

  _headerButton(icon, event) {
    return SizedBox(
      width: 35,
      child: Center(
        child: IconButton(
          padding:const EdgeInsets.all(0.0),
          icon: Icon(
            icon,
            color: Colors.white,
            size: 20,
          ),
          onPressed: event,
        ),
      ),
    );
  }

  _searchInput() {
    return FlexibleSpaceBar(
      title: Container(
        height: 20,
        width: MediaQuery.of(context).size.width * 0.6,
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(3),
        ),
        child: InkWell(
            onTap: () {
              showSearch(context: context, delegate: MySearchDelegate());
            },
            child: const TextField(
              textAlign: TextAlign.start,
              enabled: false,
              decoration: InputDecoration(
                hintStyle: TextStyle(
                  fontSize: 8,
                ),
                border: InputBorder.none,
                prefixIcon: Icon(
                  Icons.search,
                  color: Colors.grey,
                  size: 15,
                ),
                suffixIcon: Icon(
                  Icons.camera_alt,
                  color: Colors.grey,
                  size: 15,
                ),
              ),
            )
        ),
      ),
      centerTitle: true,
      titlePadding: const EdgeInsets.only(bottom: 10, right: 0),
      collapseMode: CollapseMode.parallax,
      background: Image.network('http://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/bg1.jpg', fit: BoxFit.cover),
    );
  }
}