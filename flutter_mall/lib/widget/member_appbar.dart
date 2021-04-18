import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mall/navigator/zero_navigator.dart';
import 'package:flutter_mall/page/my_search_delegate.dart';
import 'package:flutter_mall/util/toast.dart';

///主页appbar
memberAppBar(BuildContext context) {

  return Padding(
      padding: EdgeInsets.only(left: 10, right: 25),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          Padding(
            padding: EdgeInsets.only(left: 8),
            child: Icon(Icons.settings_outlined,color: Colors.grey),
          ),
          Padding(
            padding: EdgeInsets.only(left: 8),
            child: Icon(Icons.record_voice_over_outlined,color: Colors.grey),
          ),
          Padding(
            padding: EdgeInsets.only(left: 8),
            child: Icon(Icons.mail_outline,color: Colors.grey),
          ),
        ],
      ),
    );


}