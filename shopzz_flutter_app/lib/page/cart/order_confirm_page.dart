
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:oktoast/oktoast.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:roundcheckbox/roundcheckbox.dart';
import 'package:shopzz_flutter_app/controller/cart_page_controller.dart';
import 'package:shopzz_flutter_app/controller/member_address_controller.dart';
import 'package:shopzz_flutter_app/entity/response/address_response.dart';
import 'package:shopzz_flutter_app/entity/response/cart_detail_response.dart';
import 'package:shopzz_flutter_app/page/cart/widgets/order_confirm_footer.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/router/router_manager.dart';
import 'package:shopzz_flutter_app/utils/loading_util.dart';
import 'package:shopzz_flutter_app/widgets/base_scaffold.dart';
import 'package:shopzz_flutter_app/widgets/my_app_bar.dart';

///订单确认页面
class OrderConfirmPage extends StatefulWidget {
  const OrderConfirmPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _OrderConfirmPageState();
  }
}

class _OrderConfirmPageState extends State<OrderConfirmPage>{

  MemberAddressController memberAddressController = Get.put(MemberAddressController());

  final CartPageController _cartPageController = Get.find<CartPageController>();

  final RefreshController _refreshController = RefreshController(initialRefresh: false);

  @override
  void initState() {
    super.initState();
    memberAddressController.getMemberAddressList();
    _cartPageController.cartDetail();
  }

  @override
  void dispose() {
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    return BaseScaffold(
      appBar: MyAppBar(
        title: const Text("结算"),
        backgroundColor: ColorManager.main,
        elevation: 0,
      ),

      body: Obx(() {
        if(memberAddressController.addressResponse.value == null || memberAddressController.addressResponse.value.mainAddress == null) {
          return normalLoading();
        }


        return Scaffold(
          bottomSheet: OrderConfirmFooter(() {
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
                memberAddressController.getMemberAddressList();
                _refreshController.refreshCompleted();
              }, //下拉时调用
              child: Column(
                children: <Widget>[
                  //地址确认
                  Padding(
                      padding: const EdgeInsets.only(top: 8, right: 5, left: 5),
                      child: defaultAddressTile(memberAddressController.addressResponse.value.mainAddress)
                  ),

                  //商品确认
                  Padding(
                      padding: const EdgeInsets.only(top: 8, right: 5, left: 5),
                      child: _columnOne(_cartPageController.cartDetailResponse.value)
                  ),

                  //确认信息
                  Padding(
                    padding: const EdgeInsets.only(top: 15, left: 10, right: 10),
                    child: Column(
                      children: [
                        confirmTile("商品金额", "¥" + _cartPageController.totalAmount.toString(), () {}),
                        confirmTile("运费", "¥6.00", () {}),
                        confirmTile("立减", "¥0.00", () {}),
                        confirmTile("优惠券", "-¥6.00", () {
                          //TODO 选择优惠券页面
                        })
                      ],
                    ),
                  ),

                  //支付方式
                  Padding(
                    padding: const EdgeInsets.only(top: 15, left: 10, right: 10),
                    child: Column(
                      children: [
                        payTile(Icons.wallet_travel_outlined, "零钱支付", isDefault: false),
                        const SizedBox(height: 3),
                        payTile(Icons.wallet_travel_outlined, "数字货币支付", isDefault: true),
                        const SizedBox(height: 3),
                        payTile(Icons.wallet_travel_outlined, "支付宝支付", isDefault: false),
                        const SizedBox(height: 3),
                        payTile(Icons.wallet_travel_outlined, "微信支付", isDefault: false)
                      ],
                    ),
                  ),

                ],
              )
          ),

        );

      }),
    );
  }



  ///默认地址组件
  defaultAddressTile(AddressEntity address, {bool isDefault = true}) {
    return Container(
        height: 80,
        decoration: BoxDecoration(
            color: ColorManager.white,
            borderRadius: const BorderRadius.all(Radius.circular(8)),
            border: Border.all(color: ColorManager.main, width: 1)
        ),
        child: ListTile(
            title: Row(children: [
              isDefault ? littleTag("默认", Colors.redAccent) : littleTag("地址", Colors.blueAccent),
              Padding(
                  padding: const EdgeInsets.only(left: 3),
                  child: Text(address.province + address.city + address.district, style: const TextStyle(fontSize: 13, color: ColorManager.black))) ],),
            subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(address.detailAddress, style: const TextStyle(fontSize: 20, color: ColorManager.black)),
                Text(address.receiverName + "  " + address.receiverPhone, style: const TextStyle(fontSize: 13)),
              ],
            ),
            trailing: const Icon(Icons.navigate_next),
            onTap: () {
              Get.toNamed(Routers.address);
            }
        )
    );

  }

  Widget littleTag(String text, Color colors, {double size = 9}) {
    return Container(
        margin: const EdgeInsets.only(left: 2),
        padding: const EdgeInsets.only(left: 2, right: 2, top: 0, bottom: 0),
        decoration: BoxDecoration(border: Border.all(color: colors)),
        child: Text(text, style: TextStyle(
          color: colors,
          fontSize: size,
          fontWeight: FontWeight.w600,
        )));
  }



  //专栏ID为1的SPU列表
  _columnOne(CartDetailResponse cartDetailResponse) {
    return Column(
      children: <Widget>[
        Container(
          color: ColorManager.white,
          padding: const EdgeInsets.only(top: 10, bottom: 10),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: const <Widget>[
              Padding(padding: EdgeInsets.only(left: 5), child: Text("订单商品列表", style: TextStyle(color: ColorManager.black, fontSize: 14))),
            ],
          ),
        ),
        Container(
          color: ColorManager.white,
          child: SingleChildScrollView(
            scrollDirection: Axis.horizontal,
            child: Row(
              children: cartDetailResponse.cartItemVOList.map((CartItemEntity item) {
                return InkWell(
                  onTap: () {
                    showToast("点击了" + item.skuName);
                  },
                  child: _columnCard(item),
                );
              }).toList(),
            ),
          ),
        )
      ],
    );
  }



  ///专栏item卡片
  Widget _columnCard(CartItemEntity item) {

    return Card(
      child: SizedBox(
        width: 110,
        height: 155,
        child: Column(
          children: <Widget>[

            //SPU图片
            CachedNetworkImage(imageUrl: item.skuPic, fit: BoxFit.cover),

            //SPU名称
            Container(
              alignment: Alignment.centerLeft,
              padding: const EdgeInsets.only(right: 3, left: 5, top: 3),
              child: Text(
                item.skuName,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(color: ColorManager.black, fontSize: 10),
              ),
            ),

            //SPU价格
            Padding(
              padding: const EdgeInsets.only(right: 4, left: 4, top: 4),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  Text.rich(
                    TextSpan(
                        text: '¥',
                        children: [TextSpan(text: (item.price).toStringAsFixed(2).toString())],
                        style: const TextStyle(color: ColorManager.main, fontSize: 12)),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }


  ///确认文字信息
  confirmTile(String left, String right, onTap) {
    return InkWell(
      onTap: onTap,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          RichText(
            text: TextSpan(children: [
              WidgetSpan(
                child: Container(
                  padding: const EdgeInsets.only(left: 2, right: 2, top: 0, bottom: 1),
                  child: Text(left, style: const TextStyle(fontSize: 12, fontWeight: FontWeight.w500)),
                ),
              ),
              const WidgetSpan(child: SizedBox(width: 3)),
            ]),
          ),


          Row(
            children: [
              right.startsWith("-") ?
              Text(right, style: const TextStyle(fontSize: 13, color: ColorManager.red, fontWeight: FontWeight.bold))
              :
              Text(right, style: const TextStyle(fontSize: 13, color: ColorManager.fontGrey, fontWeight: FontWeight.bold)),

              const Icon(Icons.keyboard_arrow_right, color: ColorManager.grey)
            ],
          ),
        ],
      ),
    );
  }

  payTile(IconData icon, String title, {isDefault = false}) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [

        Row(
          children: [
            Icon(icon, size: 20, color: ColorManager.main),
            RichText(
              text: TextSpan(children: [
                WidgetSpan(
                  child: Container(
                    padding: const EdgeInsets.only(left: 2, right: 2, top: 0, bottom: 1),
                    child: Text(title, style: const TextStyle(fontSize: 12, fontWeight: FontWeight.w500)),
                  ),
                ),
                const WidgetSpan(child: SizedBox(width: 3)),
              ]),
            ),
          ],
        ),

        Row(
          children: [
            RoundCheckBox(
              isChecked: isDefault,
              size: 20,
              checkedWidget: const Icon(Icons.check, size: 15, color: ColorManager.white),
              checkedColor: ColorManager.main,
              onTap: (selected) {

              },
            )
          ],
        ),
      ],
    );
  }

}