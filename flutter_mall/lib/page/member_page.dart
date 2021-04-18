import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_easyrefresh/easy_refresh.dart';
import 'package:flutter_mall/cache/sp_cache.dart';
import 'package:flutter_mall/config/common_config.dart';
import 'package:flutter_mall/dao/home_dao.dart';
import 'package:flutter_mall/dao/member_dao.dart';
import 'package:flutter_mall/model/index_banner_model.dart';
import 'package:flutter_mall/model/member_detail_model.dart';
import 'package:flutter_mall/page/login_page.dart';
import 'package:flutter_mall/page/member/member_header.dart';
import 'package:flutter_mall/page/member/member_order.dart';
import 'package:flutter_mall/page/member/member_wallet.dart';
import 'package:flutter_mall/util/color.dart';
import 'package:flutter_mall/util/log_util.dart';
import 'package:flutter_mall/util/string_util.dart';
import 'package:flutter_mall/widget/home_grid_navigator.dart';
import 'package:flutter_mall/widget/loading.dart';
import 'package:lottie/lottie.dart';


class MemberPage extends StatefulWidget {
  @override
  _MemberPageState createState() => _MemberPageState();
}

class _MemberPageState extends State<MemberPage> with AutomaticKeepAliveClientMixin,TickerProviderStateMixin {

  MemberVO memberVO;
  MemberInfoVO memberInfoVO;
  List<Navigations> navs;

  bool loading  = false;

  @override
  bool get wantKeepAlive => true;

  var memberPadding = EdgeInsets.only(top: 8, left: 5, right: 5);

  GlobalKey<MaterialFooterWidgetState> _footerKey = new GlobalKey<MaterialFooterWidgetState>();


  @override
  void initState() {
    String localToken = SpCache.getInstance().get(CommonConfig.LOCAL_TOKEN);
    if(!isEmpty(localToken)) {
      loadData();
    }
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    String localToken = SpCache.getInstance().get(CommonConfig.LOCAL_TOKEN);
    if (isEmpty(localToken)) {
      return LoginPage();
    }

    if(loading) {
      return zeroLoading();
    }

    return Scaffold(
      backgroundColor: Color.fromRGBO(244, 245, 245, 1.0),
      body: EasyRefresh(

        footer: ClassicalFooter(
            key:_footerKey,
            bgColor:Colors.white,
            textColor: primary,
            noMoreText: '没有了',
            loadingText: '加载中',
            loadReadyText:'上拉加载....'
        ),

        child: ListView(
          shrinkWrap: true,
          children: <Widget>[

            //会员信息头部
            MemberHeader(memberVO: memberVO, memberInfoVO: memberInfoVO),

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

            //会员小组件导航栏
            Padding(
              padding: memberPadding,
              child: Container(
                decoration: cardRadius(),
                child: HomeGridNavigator(navigatorList: navs),
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

                        Padding(padding: EdgeInsets.only(left: 8, top: 8, bottom: 4),
                            child: Text("我的服务", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 13))),

                        Padding(padding: EdgeInsets.only(right: 8, top: 8, bottom: 4),
                        child: Row(
                          children: [
                            Text("查看更多", style: TextStyle(fontSize: 12, color: Colors.grey)),
                            Icon(Icons.navigate_next, size: 12, color: Colors.grey),
                          ],
                        ))
                      ],
                    ),
                    //分割线
                    Divider(height: 2),
                    //网格栏
                    HomeGridNavigator(navigatorList: navs)
                  ],
                ),
              ),
            ),
          ],
        ),

      ),
    );


  }
  ///加载数据
  Future<void> loadData() async {
    if(loading) {
      Log.info("还在加载中");
      return;
    }
    loading = true;

    try{
      var memberDetailModel = await getMemberDetailInfo();
      var indexData = await getNavs();

      setState(() {
        memberVO = memberDetailModel.memberVO;
        memberInfoVO = memberDetailModel.memberInfoVO;
        navs = indexData.navigations;
        loading = false;
      });
    } on Exception catch (e) {
      loading = false;
      Log.info("加载出错" + e.toString());
    }
  }



  Future<IndexBannerModel> getNavs() async {
    IndexBannerModel indexBanner = await HomeDao.getIndexBanner();
    return indexBanner;

  }

  ///获取用户详细信息
  Future<MemberDetailModel> getMemberDetailInfo() async {
    MemberDetailModel result = await MemberDao.userInfo();
    return result;
  }


  cardRadius() {
    return BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.all(Radius.circular(10))
    );
  }
}