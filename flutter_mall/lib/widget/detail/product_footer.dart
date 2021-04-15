

import 'package:flutter/material.dart';
import 'package:flutter_mall/util/color.dart';
import 'package:flutter_mall/util/toast.dart';

class ProductFooter extends StatelessWidget {

  Function addCartFunction;
  ProductFooter(this.addCartFunction);


  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: 60,
      decoration: BoxDecoration(
        color: Colors.white,
        border: Border(
            top: BorderSide(
              color: primary,
              width: 0.2,
            )),
      ),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Container(
            width: 30,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Icon(Icons.support_agent_outlined, color: Colors.grey, size: 20),
                Text( "客服", style: TextStyle(color: Colors.grey, fontSize: 8)),
              ],
            ),
          ),
          Container(
            width: 30,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Icon(Icons.shopping_cart_outlined, color: Colors.grey, size: 20),
                Text("购物车", style: TextStyle(color: Colors.grey, fontSize: 8),
                ),
              ],
            ),
          ),
          Container(
            width: 30,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Icon(Icons.star_outline, color: Colors.grey, size: 20,),
                Text("收藏", style: TextStyle(color: Colors.grey, fontSize: 8)),
              ],
            ),
          ),
          Expanded(
            flex: 1,
            child: Container(
              margin: EdgeInsets.fromLTRB(10, 0, 5, 0),
              child: FlatButton(
                color: primary,
                highlightColor: Colors.black12,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(20.0)),
                child: Text("立即购买",style: TextStyle(color: Colors.white),
                ),
                onPressed: () {},
              ),
            ),
          ),

          Expanded(
            flex: 1,
            child: Container(
              margin: EdgeInsets.fromLTRB(10, 0, 10, 0),
              child: FlatButton(
                color: Colors.orangeAccent,
                highlightColor: Colors.black12,
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20.0)),
                child: Text("加入购物车", style: TextStyle(color: Colors.white),
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