import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:shopzz_flutter_app/controller/cart_page_controller.dart';
import 'package:shopzz_flutter_app/entity/response/cart_detail_response.dart';
import 'package:shopzz_flutter_app/page/cart/widgets/cart_tag.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/utils/image_util.dart';
import 'package:shopzz_flutter_app/widgets/count_down.dart';
import 'package:shopzz_flutter_app/widgets/counter.dart';
import 'package:shopzz_flutter_app/widgets/round_checkbox.dart';

class CartCard extends StatefulWidget {

  /// 构造函数传入的购物车item信息
  final CartItemEntity item;

  /// 回调方法，在计数器改变的时候调用修改购物车商品数量的接口
  Function(int, int) counterOnChanged;

  /// 回调方法，选中和未选中时调用接口
  Function(int) checkBoxOnChanged;

  CartCard({Key key, this.item, this.counterOnChanged, this.checkBoxOnChanged}) : super(key: key);

  @override
  _CartCardState createState() => _CartCardState();
}

class _CartCardState extends State<CartCard> {

  final CartPageController _cartPageController = Get.find<CartPageController>();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      padding: const EdgeInsets.all(15),
      margin: const EdgeInsets.only(top: 10),
      decoration: const BoxDecoration(
        color: ColorManager.white,
        borderRadius: BorderRadius.all(Radius.circular(8)),
      ),

      child: Column(
        children: [
          //header部分，店铺名称和运费信息
          cartHeader(),

          //item主体，展示促销信息和SKU基础信息
          Padding(
            padding: const EdgeInsets.only(top: 6),
            child: cartItem(),
          )
        ],
      ),

    );
  }

  //item主体，展示促销信息和SKU基础信息
  cartItem() {
    return Column(
      children: [
        //促销信息展示，目前展示秒杀信息和其他活动信息
        cartSubHeader(widget.item),
        cartBody(widget.item)
      ],
    );
  }

  ///购物车头部，店铺名称和运费信息
  cartHeader() {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[

        //自营logo
        Container(width: 30, padding: const EdgeInsets.only(right: 4), child: const Image(image: AssetImage('assets/images/ziying.png'), width: 25, height: 20)),

        //店铺名
        const Expanded(
          child: Text("shopzz自营旗舰店", style: TextStyle(
            fontWeight: FontWeight.bold,
          )),
        ),

        Row(
          children: <Widget>[
            const Icon(Icons.info_outline, color: ColorManager.orange, size: 16),
            Obx(() {
              if(_cartPageController.isEdit.value) {
                return const Text("已免运费", style: TextStyle(fontSize: 12));
              }else {
                return InkWell(
                  child: const Text("删除", style: TextStyle(fontSize: 12)),
                  onTap: () {
                    _cartPageController.cartDelete([widget.item.skuId]);
                    _cartPageController.cartDetail();
                  },
                );
              }
            })
          ],
        )
      ],
    );
  }

  cartBody(CartItemEntity item) {
    return Container(
      padding: const EdgeInsets.only(bottom: 20),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Container(
            alignment: Alignment.centerLeft,
            width: 30,
            height: 100,
            //item选择框
            child: RoundCheckbox(
              value: item.checked == 1,
              backgroundColor: ColorManager.white,
              activeColor: ColorManager.red,
              checkColor: ColorManager.white,
              activeBorderColor: ColorManager.red,
              onChanged: (status) {
                setState(() {
                  item.checked = status ? 1 : 0;
                  widget.checkBoxOnChanged(status ? 1 : 0);
                });
              },
            ),
          ),

          //item sku商品图
          Container(
              alignment: Alignment.topCenter,
              margin: const EdgeInsets.only(left: 0, right: 10),
              width: 100,
              height: 100,
              decoration: BoxDecoration(
                color: ColorManager.white,
                borderRadius: BorderRadius.circular(5),
              ),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(5),
                child: cachedImage(item.skuPic, width: 100, height: 100),
              )
          ),
          Expanded(
            flex: 1,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Row(
                  children: <Widget>[
                    SizedBox(
                      width: MediaQuery.of(context).size.width - 80 - 30 - 60,
                      child: Text(
                        item.skuName,
                        maxLines: 2,
                        softWrap: true,
                        overflow: TextOverflow.ellipsis,
                        style: const TextStyle(
                            fontSize: 11,
                            fontWeight: FontWeight.bold,
                            height: 1.1),
                      ),
                    ),
                  ],
                ),
                UnconstrainedBox(
                  child: CartTag(
                    height: 20,
                    margin: const EdgeInsets.only(top: 10, bottom: 10),
                    text: Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: const <Widget>[
                        LimitedBox(
                          maxWidth: 120,
                          child: Text(
                            "暗夜白 128G",
                            maxLines: 1,
                            softWrap: false,
                            overflow: TextOverflow.ellipsis,
                            style: TextStyle(fontSize: 12, color: Colors.black),
                          ),
                        ),
                        Text("精品套装", style: TextStyle(fontSize: 12, color: Colors.black)),
                        Icon(Icons.keyboard_arrow_down, size: 12),
                      ],
                    ),
                    color: const Color(0xFFF6F6F6),
                    borderWidth: 0,
                  ),
                ),

                Container(
                  margin: const EdgeInsets.only(top: 10, bottom: 10),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[

                      RichText(
                        text: TextSpan(text:item.price.toString()?.substring(0, item.price.toString().indexOf(".")),
                            style: const TextStyle(color: ColorManager.red, fontWeight: FontWeight.bold, fontSize: 16),
                            children: <TextSpan>[
                              TextSpan(
                                text: ".${item?.price?.toString()?.split('.')[1]}",
                                style: const TextStyle(color: Colors.transparent, fontSize: 12),
                              ),
                            ]),
                      ),
                      Counter(defaultValue: item.quantity, min: 1, onChanged: widget.counterOnChanged)
                    ],
                  ),
                ),
              ],
            ),
          )
        ],
      ),
    );
  }


  cartSubHeader(CartItemEntity item) {
    return Container(
      margin: const EdgeInsets.only(left: 30),
      child: Column(children: <Widget>[
        //秒杀信息，todo 通过offstage控制是否展示
        Offstage(
          offstage: false,
          child: flashSaleInfo(86400),
        ),

        //其他活动信息，todo 通过offstage控制是否展示
        Offstage(
            offstage: false,
            child: activityInfo(item)
        ),
      ]),
    );
  }

  activityInfo(CartItemEntity item) {
    return Container(
      margin: const EdgeInsets.only(bottom: 10),
      child: Row(
        children: <Widget>[
          //其他活动的标签，如：跨店满减，加价购等
          CartTag(
            height: 12,
            padding: const EdgeInsets.only(left: 1, right: 1),
            margin: const EdgeInsets.only(right: 5),
            text: const Text("跨店满减", style: TextStyle(color: ColorManager.white, fontSize: 8, height: 1.1)),
            color: Colors.red,
            borderColor: Colors.red,
            radius: 3,
          ),

          //活动描述
          const Expanded(flex: 1, child: Text("满2件总价8折", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 12)),
          ),

          //凑单按钮
          Row(
            children: const <Widget>[
              Text("去凑单", style: TextStyle(fontSize: 12)),
              Icon(Icons.keyboard_arrow_right, size: 15)
            ],
          ),
        ],
      ),
    );
  }

  //秒杀信息，传入秒数倒计时
  flashSaleInfo(int secKillEndCountdown) {
    return Container(
      margin: const EdgeInsets.only(bottom: 5),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          const Text("秒杀", style: TextStyle(color: Colors.black, fontSize: 12, fontWeight: FontWeight.bold)),
          Row(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[

              //标语
              Container(
                margin: const EdgeInsets.only(left: 5, right: 5),
                child: const Text("距离活动结束时间结束还剩", style: TextStyle(fontSize: 12, color: Colors.black)),
              ),

              //时分秒倒计时组件
              Countdown(
                timeLeft: secKillEndCountdown ?? 0,
              ),
            ],
          ),
        ],
      ),
    );
  }

}