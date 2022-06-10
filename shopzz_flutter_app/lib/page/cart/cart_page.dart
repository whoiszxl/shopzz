import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:shopzz_flutter_app/controller/cart_page_controller.dart';
import 'package:shopzz_flutter_app/entity/response/cart_detail_response.dart';
import 'package:shopzz_flutter_app/page/cart/widgets/cart_card.dart';
import 'package:shopzz_flutter_app/page/cart/widgets/cart_footer.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/router/router_manager.dart';
import 'package:shopzz_flutter_app/widgets/base_scaffold.dart';
import 'package:shopzz_flutter_app/widgets/my_app_bar.dart';

///发现页面
class CartPage extends StatefulWidget {
  const CartPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _CartPageState();
  }
}

class _CartPageState extends State<CartPage> with AutomaticKeepAliveClientMixin,TickerProviderStateMixin {

  ///用户下拉刷新的控制器
  final RefreshController _refreshController = RefreshController(initialRefresh: false);

  final ScrollController _scrollController = ScrollController();

  ///获取到注入的购物车业务控制器
  final CartPageController _cartPageController = Get.find<CartPageController>();

  @override
  void initState() {
    super.initState();
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
          title: const Text("购物车"),
          leadingType: AppBarBackType.None,
          backgroundColor: ColorManager.main,
          elevation: 0,
        ),
        body: Obx(() {

          return Scaffold(
            bottomSheet: CartFooter(() {
              //跳转订单确认页操作
              Get.toNamed(Routers.orderConfirm);
            }),
            body: (_cartPageController.cartDetailResponse.value != null && _cartPageController.cartDetailResponse.value.cartItemVOList != null) ?
            SmartRefresher(
                enablePullDown: true, //开启下拉
                enablePullUp: false, //关闭上拉
                header: const ClassicHeader(),
                footer: const ClassicFooter(),
                controller: _refreshController,
                onRefresh: _onRefresh, //下拉时调用
                child: _cartListView(_cartPageController.cartDetailResponse.value.cartItemVOList) //返回列表组件
            ) : Center(
              child: InkWell(
                child: const Text("购物车无商品，快去加购吧，点击刷新", style: TextStyle(color: ColorManager.black, fontSize: 14, fontWeight: FontWeight.bold)),
                onTap: () {
                  _cartPageController.cartDetail();
                },
              ),
            ),

          );

        }),


    );
  }


  ///下拉刷新时调用，重新调用接口，关闭下拉加载动画
  _onRefresh() {
    _cartPageController.cartDetail();
    _refreshController.refreshCompleted();
  }


  ///返回购物车商品列表，ListView
  _cartListView(List<CartItemEntity> cartItemList) {
    return ListView.builder(
        itemCount: cartItemList?.length,
        controller: _scrollController,
        itemBuilder: (BuildContext context, int index) {
          print(index);

          //返回购物车item
          return CartCard(item: cartItemList[index],

              //购物车item的计数器改变事件
              counterOnChanged: (int quantity, int type) {
                //调用接口更新购物车item的数量
                _cartPageController.cartUpdateQuantity(cartItemList[index]?.skuId, quantity);

                //根据type加减类型修改全局的总金额参数
                if(type == -1) {
                  _cartPageController.totalAmount.value = _cartPageController.totalAmount.value - cartItemList[index].price;
                }else if(type == 1) {
                  _cartPageController.totalAmount.value = _cartPageController.totalAmount.value + cartItemList[index].price;
                }
              },

              //选择框的点击事件
              checkBoxOnChanged: (int checked) {
                //调用接口改变item的选中状态
                _cartPageController.cartCheck(cartItemList[index].skuId, checked);

                //根据选中状态更新全局的总金额参数
                if(checked == 1)                                                                                                                                                                                            {
                  _cartPageController.totalAmount.value = _cartPageController.totalAmount.value + cartItemList[index].price * cartItemList[index].quantity;
                }else if(checked == 0) {
                  _cartPageController.totalAmount.value = _cartPageController.totalAmount.value - cartItemList[index].price * cartItemList[index].quantity;
                }

              }
          );
        }
    );
  }

  @override
  bool get wantKeepAlive => true;
}