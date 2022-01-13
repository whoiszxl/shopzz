import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';

class ProductFooter extends StatelessWidget {
  Function addCartFunction;

  ProductFooter(this.addCartFunction);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: 60,
      decoration: const BoxDecoration(
        color: ColorManager.white,
        border: Border(
            top: BorderSide(
          color: ColorManager.main,
          width: 0.2,
        )),
      ),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          SizedBox(
            width: 30,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: const <Widget>[
                Icon(Icons.support_agent_outlined,
                    color: ColorManager.grey, size: 20),
                Text("客服", style: TextStyle(color: ColorManager.grey, fontSize: 8)),
              ],
            ),
          ),
          SizedBox(
            width: 30,
            child: InkWell(
              onTap: () {
                Get.toNamed(Routers.cart);
              },
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: const <Widget>[
                  Icon(Icons.shopping_cart_outlined,
                      color: ColorManager.grey, size: 20),
                  Text(
                    "购物车",
                    style: TextStyle(color: ColorManager.grey, fontSize: 8),
                  ),
                ],
              ),
            ),
          ),
          SizedBox(
            width: 30,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: const <Widget>[
                Icon(
                  Icons.star_outline,
                  color: ColorManager.grey,
                  size: 20,
                ),
                Text("收藏", style: TextStyle(color: ColorManager.grey, fontSize: 8)),
              ],
            ),
          ),
          Expanded(
            flex: 1,
            child: Container(
              margin: const EdgeInsets.fromLTRB(10, 0, 5, 0),
              child: FlatButton(
                color: ColorManager.main,
                highlightColor: Colors.black12,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10)),
                child: const Text(
                  "立即购买",
                  style: TextStyle(color: ColorManager.white),
                ),
                onPressed: () {},
              ),
            ),
          ),
          Expanded(
            flex: 1,
            child: Container(
              margin: const EdgeInsets.fromLTRB(10, 0, 10, 0),
              child: FlatButton(
                color: Colors.orangeAccent,
                highlightColor: Colors.black12,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10)),
                child: const Text(
                  "加入购物车",
                  style: TextStyle(color: ColorManager.white),
                ),
                onPressed: () {
                  addCartFunction();
                },
              ),
            ),
          ),
        ],
      ),
    );
  }
}
