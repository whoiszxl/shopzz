import 'package:flutter/material.dart';
import 'package:zz_flutter_shop/page/my_search_delegate.dart';
///搜索结果页appbar
searchAppBar(BuildContext context, String query) {

  return Padding(
    padding: const EdgeInsets.only(left: 10, right: 10),
    child: Row(
      children: [
        InkWell(
          onTap: () {
            Navigator.pop(context);
          },
          child: ClipRRect(
              borderRadius: BorderRadius.circular(13),
              child: const Icon(Icons.keyboard_return, color: Colors.grey,)
          ),
        ),
        Expanded(
            child: Padding(
              padding: const EdgeInsets.only(left: 15, right: 15),
              child: InkWell(
                onTap: () {
                  showSearch(context: context, delegate: MySearchDelegate());
                },
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(13),
                  child: Container(
                    padding: const EdgeInsets.only(left: 10),
                    height: 32,
                    alignment: Alignment.centerLeft,
                    child: Row(
                      children: [
                        const Icon(Icons.search, color: Colors.grey),

                        Padding(padding: const EdgeInsets.only(left: 5), child: Text(query, style: const TextStyle(fontSize: 12, color: Colors.black87)))
                      ],
                    ),
                    decoration: BoxDecoration(color: Colors.grey[100]),
                  ),
                ),
              ),
            )),
        const Icon(
          Icons.explore_outlined,
          color: Colors.grey,
        ),
        const Padding(
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