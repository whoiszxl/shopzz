import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/navigator/zero_navigator.dart';
import 'package:flutter_mall/page/my_search_delegate.dart';
import 'package:flutter_mall/util/toast.dart';

///搜索结果页appbar
searchAppBar(BuildContext context, String query) {

  return Padding(
      padding: EdgeInsets.only(left: 10, right: 10),
      child: Row(
        children: [
          InkWell(
            onTap: () {
              Navigator.pop(context);
            },
            child: ClipRRect(
              borderRadius: BorderRadius.circular(13),
              child: Icon(Icons.keyboard_return, color: Colors.grey,)
            ),
          ),
          Expanded(
              child: Padding(
                padding: EdgeInsets.only(left: 15, right: 15),
                child: InkWell(
                  onTap: () {
                    showSearch(context: context, delegate: MySearchDelegate());
                  },
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(13),
                    child: Container(
                      padding: EdgeInsets.only(left: 10),
                      height: 32,
                      alignment: Alignment.centerLeft,
                      child: Row(
                        children: [
                          Icon(Icons.search, color: Colors.grey),

                          Padding(padding: EdgeInsets.only(left: 5), child: Text(query, style: TextStyle(fontSize: 12, color: Colors.black87)))
                        ],
                      ),
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