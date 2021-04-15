import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/navigator/zero_navigator.dart';
import 'package:flutter_mall/page/my_search_delegate.dart';
import 'package:flutter_mall/util/toast.dart';

///主页appbar
homeAppBar(BuildContext context) {

  return Padding(
      padding: EdgeInsets.only(left: 10, right: 10),
      child: Row(
        children: [
          InkWell(
            onTap: () {
              ZeroNavigator.getInstance().onJumpTo(RouteStatus.login);
            },
            child: ClipRRect(
              borderRadius: BorderRadius.circular(13),
              child: Image(
                height: 26,
                width: 26,
                image: AssetImage('images/avatar.png'),
              ),
            ),
          ),
          Expanded(
              child: Padding(
                padding: EdgeInsets.only(left: 15, right: 15),
                child: InkWell(
                  onTap: () {
                    showToast("点击搜索了");
                    showSearch(context: context, delegate: MySearchDelegate());
                  },
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(13),
                    child: Container(
                      padding: EdgeInsets.only(left: 10),
                      height: 32,
                      alignment: Alignment.centerLeft,
                      child: Icon(Icons.search, color: Colors.grey),
                      decoration: BoxDecoration(color: Colors.grey[100]),
                    ),
                  ),
                ),
              )),
          Icon(
            Icons.explore_outlined,
            color: Colors.grey,
          ),
          Padding(
            padding: EdgeInsets.only(left: 12),
            child: Icon(
              Icons.mail_outline,
              color: Colors.grey,
            ),
          ),
        ],
      ),
    );


}