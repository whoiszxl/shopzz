import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:shopzz_flutter_app/controller/coupon_page_controller.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/utils/loading_util.dart';
import 'package:shopzz_flutter_app/widgets/base_scaffold.dart';
import 'package:shopzz_flutter_app/widgets/my_app_bar.dart';

///发现页面
class CouponPage extends StatefulWidget {
  const CouponPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _CouponPageState();
  }
}

class _CouponPageState extends State<CouponPage>  with AutomaticKeepAliveClientMixin,TickerProviderStateMixin {

  final CouponPageController couponPageController = Get.put(CouponPageController());

  final RefreshController _refreshController = RefreshController(initialRefresh: false);


  @override
  void initState() {
    super.initState();
    couponPageController.getCouponAllUnlimited(_refreshController);
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
      if(couponPageController.couponList.isEmpty) {
        return normalLoading();
      }

      return BaseScaffold(
        appBar: MyAppBar(
          title: const Text("优惠券"),
          leadingType: AppBarBackType.Back,
          backgroundColor: ColorManager.fieldBg,
          elevation: 0,
        ),

        body: Container(
            color: ColorManager.fieldBg,
            child: ListView.builder(
                itemCount: couponPageController.couponList.length,
                itemBuilder: (BuildContext context, int index) {
                  return _couponItem(context, index, () {
                    couponPageController.receiveCoupon(couponPageController.couponList[index].id.toString()).then((value) => {
                      value ? showToast("领取成功"):showToast("领取失败")
                    });
                  });
                })),
      );
    });
  }


  _couponItem(BuildContext context, int index, onTap) {
    return InkWell(
      onTap: onTap,
      child: Container(
        margin: const EdgeInsets.all(8.0),

        //优惠券背景
        decoration: const BoxDecoration(
            color: ColorManager.transparent,
            image: DecorationImage(image: AssetImage('assets/images/coupon_bg.png'), fit: BoxFit.fill),
            borderRadius: BorderRadius.all(Radius.circular(5.0))
        ),

        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[

            //金额
            Container(
              width: 100.0,
              padding: const EdgeInsets.only(left: 3),
              margin: const EdgeInsets.only(left: 10.0, right: 10.0),
              child: Row(
                children: <Widget>[

                  const Text('¥', style: TextStyle(color: ColorManager.main, fontSize: 17)),

                  Text(couponPageController.couponList[index].discountAmount.toString(),
                    style: const TextStyle(color: ColorManager.main, fontSize: 26),
                  )
                ],
              ),
            ),

            //优惠券信息
            Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Container(
                    margin: const EdgeInsets.only(top: 10.0, bottom: 10.0),
                    child: Row(
                      children: <Widget>[
                        Text(
                          couponPageController.couponList[index].name,
                          style: const TextStyle(fontSize: 18.0),
                        ),
                        // Expanded(
                        //   child: Container(),
                        // )
                      ],
                    ),
                  ),

                  couponPageController.couponList[index].type == 1 ? const Text("满减券")
                      : (couponPageController.couponList[index].type == 2
                      ? const Text("满减折扣券")
                      : const Text("无门槛立减券")),


                  Container(
                    margin: const EdgeInsets.only(top: 10.0, bottom: 10.0),
                    child: Text(couponPageController.couponList[index].subName,
                        style: const TextStyle(color: Colors.grey)),
                  ),
                  Container(
                    margin: const EdgeInsets.only(top: 10.0, bottom: 10.0),
                    child: Text(
                      '有效期至:${couponPageController.couponList[index].endTime}',
                      style: const TextStyle(color: Colors.grey),
                    ),
                  )
                ],
              ),
            )
          ],
        ),
      ),
    );
  }


  @override
  bool get wantKeepAlive => true;
}