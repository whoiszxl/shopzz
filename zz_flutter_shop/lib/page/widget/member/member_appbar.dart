import 'package:flutter/material.dart';

///主页appbar
memberAppBar(BuildContext context) {

  return Padding(
    padding: const EdgeInsets.only(left: 10, right: 25),
    child: Row(
      mainAxisAlignment: MainAxisAlignment.end,
      children: const [
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