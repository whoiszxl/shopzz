import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/controller/member_page_controller.dart';
import 'package:zz_flutter_shop/entity/response/home_app_index_response.dart';
import 'package:zz_flutter_shop/entity/response/member_info_response.dart';
import 'package:zz_flutter_shop/page/widget/home_banner.dart';
import 'package:zz_flutter_shop/page/widget/member/member_appbar.dart';
import 'package:zz_flutter_shop/page/widget/member/member_grid_navigator.dart';
import 'package:zz_flutter_shop/page/widget/member/member_header.dart';
import 'package:zz_flutter_shop/page/widget/member/member_order.dart';
import 'package:zz_flutter_shop/page/widget/member/member_wallet.dart';
import 'package:zz_flutter_shop/page/widget/product/my_navigation_bar.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';

///会员页面
class MemberPage extends StatefulWidget {
  const MemberPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _MemberPageState();
  }
}

class _MemberPageState extends State<MemberPage> with AutomaticKeepAliveClientMixin,TickerProviderStateMixin {


  var memberPadding = const EdgeInsets.only(top: 8, left: 5, right: 5);

  final MemberPageController memberPageController = Get.find();

  final RefreshController _refreshController = RefreshController(initialRefresh: false);

  @override
  void initState() {
    super.initState();
    memberPageController.memberInfo();
    memberPageController.getNav();
    memberPageController.getSmallBanners();
    _refreshController.refreshCompleted();
  }

  @override
  void dispose() {
    super.dispose();
    _refreshController.dispose();
  }
  @override
  Widget build(BuildContext context) {
    super.build(context);
    return Obx(() {
      if(memberPageController.memberInfoResponse.value == null
          || memberPageController.memberInfoResponse.value.memberInfo == null
          || memberPageController.memberInfoResponse.value.member == null) {
        return Center(
          child: TextButton(
            child: const Text("点击登录"),
            onPressed: () {
              Get.toNamed(Routers.login);
            },
          ),
        );
      }else {
        return SmartRefresher(
          enablePullDown: true,
          enablePullUp: false,
          onRefresh: _onRefresh,
          header: const ClassicHeader(
            refreshingText: "刷新中",
            releaseText: "刷新中",
            completeText: "刷新完成",
          ),
          footer: const ClassicFooter(
            loadingText: "加载中",
            failedText: "加载失败",
            idleText: "加载中",
            canLoadingText: "加载中",
            noDataText: "没有更多商品了",
          ),
          controller: _refreshController,
          child: _son(memberPageController.memberInfoResponse.value, memberPageController.navigationList),
        );;
      }
    });
  }


  _son(MemberInfoResponse memberInfoResponse, List<NavigationEntity> navigationList) {
    return ListView(
      shrinkWrap: true,
      children: <Widget>[

        //沉浸式Appbar
        Stack(
          children: [
            //会员信息头部
            MemberHeader(member: memberInfoResponse.member, memberInfo: memberInfoResponse.memberInfo),
            MyNavigationBar(
              height: 30,
              color: Colors.transparent,
              statusStyle: StatusStyle.DARK_CONTENT,
              child: memberAppBar(context),
            ),
          ],
        ),

        //会员订单栏
        Padding(
          padding: memberPadding,
          child: Container(
            decoration: cardRadius(),
            child: MemberOrder(),
          ),
        ),

        //会员钱包资产栏
        Padding(
          padding: memberPadding,
          child: Container(
            decoration: cardRadius(),
            child: MemberWallet(),
          ),
        ),

        Padding(
          padding: memberPadding,
          child: Container(
            decoration: cardRadius(),
            child: Column(
              children: <Widget>[
                //标题行
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[

                    const Padding(padding: EdgeInsets.only(left: 8, top: 8, bottom: 4),
                        child: Text("我的服务", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 13))),

                    Padding(padding: const EdgeInsets.only(right: 8, top: 8, bottom: 4),
                        child: Row(
                          children: const [
                            Text("查看更多", style: TextStyle(fontSize: 12, color: ColorManager.grey)),
                            Icon(Icons.navigate_next, size: 12, color: ColorManager.grey),
                          ],
                        ))
                  ],
                ),
                //分割线
                const Divider(height: 2),
                //网格栏
                MemberGridNavigator(navigatorList: navigationList)
              ],
            ),
          ),
        ),


        //通栏广告banner
        Padding(
          padding: memberPadding,
          child: Container(
            decoration: cardRadius(),
            child: HomeBanner(
              memberPageController.smallBanners,
              bannerHeight: 50,
            )
          ),
        ),

      ],
    );
  }

  void _onRefresh() async{
    memberPageController.memberInfo();
    _refreshController.refreshCompleted();
  }

  cardRadius() {
    return const BoxDecoration(
        color: ColorManager.white,
        borderRadius: BorderRadius.all(Radius.circular(10))
    );
  }

  @override
  bool get wantKeepAlive => true;
}