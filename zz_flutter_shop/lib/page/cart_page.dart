import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/controller/cart_page_controller.dart';
import 'package:zz_flutter_shop/entity/response/cart_detail_response.dart';
import 'package:zz_flutter_shop/page/widget/cart/cart_app_bar.dart';
import 'package:zz_flutter_shop/page/widget/cart/cart_card.dart';
import 'package:zz_flutter_shop/page/widget/cart/cart_footer.dart';
import 'package:zz_flutter_shop/utils/loading_util.dart';

///购物车页面
class CartPage extends StatefulWidget {

  const CartPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _CartPageState();
  }
}

class _CartPageState extends State<CartPage> with TickerProviderStateMixin{

  ///用户下拉刷新的控制器
  final RefreshController _refreshController = RefreshController(initialRefresh: false);

  ///获取到注入的购物车业务控制器
  final CartPageController _cartPageController = Get.find<CartPageController>();

  @override
  void initState() {
    super.initState();
    //初始化获取到当前用户的购物车详情
    _cartPageController.cartDetail();

    //监听子组件选中所有的事件
    // Application.eventBus.on<CartSelectAllEvent>().listen((event) {
    //
    //   _refreshController.refreshCompleted();
    // });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      //app头部栏
      appBar: cartAppBar(context),

      //使用obx获取到业务控制器里的接口结果数据
      body: Obx(() {
        //如果结果数据为空，则显示加载动画
        if(_cartPageController.cartDetailResponse.value == null
            || _cartPageController.cartDetailResponse.value.cartItemVOList == null) {
          return normalLoading();
        }else {
          //否则展示智能刷新组件
          return Scaffold(
            bottomSheet: CartFooter(() {

            }),
            body: SmartRefresher(
                enablePullDown: true, //开启下拉
                enablePullUp: false, //关闭上拉
                header: const ClassicHeader(),
                footer: const ClassicFooter(),
                controller: _refreshController,
                onRefresh: _onRefresh, //下拉时调用
                child: _cartListView(_cartPageController.cartDetailResponse.value.cartItemVOList) //返回列表组件
            ),
          );


        }
      })
    );
  }

  ///下拉刷新时调用，重新调用接口，关闭下拉加载动画
  _onRefresh() {
    _cartPageController.cartDetail();
    _refreshController.refreshCompleted();
  }


  ///返回购物车商品列表，ListView
  _cartListView(List<CartItemVO> cartItemVOList) {
    return ListView.builder(
        itemCount: cartItemVOList.length,
        itemBuilder: (BuildContext context, int index) {

          //返回购物车item
          return CartCard(item: cartItemVOList[index],

              //购物车item的计数器改变事件
              counterOnChanged: (int quantity, int type) {
                //调用接口更新购物车item的数量
                _cartPageController.cartUpdateQuantity(cartItemVOList[index].skuId, quantity);

                //根据type加减类型修改全局的总金额参数
                if(type == -1) {
                  _cartPageController.totalAmount.value = _cartPageController.totalAmount.value - cartItemVOList[index].price * cartItemVOList[index].quantity;
                }else if(type == 1) {
                  _cartPageController.totalAmount.value = _cartPageController.totalAmount.value + cartItemVOList[index].price * cartItemVOList[index].quantity;
                }
              },

              //选择框的点击事件
              checkBoxOnChanged: (int checked) {
                //调用接口改变item的选中状态
                _cartPageController.cartCheck(cartItemVOList[index].skuId, checked);

                //根据选中状态更新全局的总金额参数
                if(checked == 1) {
                  _cartPageController.totalAmount.value = _cartPageController.totalAmount.value + cartItemVOList[index].price * cartItemVOList[index].quantity;
                }else if(checked == 0) {
                  _cartPageController.totalAmount.value = _cartPageController.totalAmount.value - cartItemVOList[index].price * cartItemVOList[index].quantity;
                }

              }
          );
        }
    );
  }

  @override
  void dispose() {
    super.dispose();
    _refreshController.dispose();
  }


}