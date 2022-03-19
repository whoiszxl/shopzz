
import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/page/widget/search_tag.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';

///flutter自带搜索组件
class MySearchDelegate extends SearchDelegate<String> {


  @override
  String get searchFieldLabel => "ps5";

  final recommendList = [
    "小红帽",
    "小车",
    "小米",
    "红红",
    "红猪",
    "苹果",
    "T恤",
    "安踏",
    "手机",
    "平板电脑",
    "猕猴桃"
  ];

  final recentList = ["小米", "苹果", "Java", "Flutter", "手机", "华为"];



  @override
  List<Widget> buildActions(BuildContext context) {
    return [
      IconButton(
        icon: const Icon(Icons.clear, color: ColorManager.grey,),
        onPressed: () {
          query = "";
        },
      )
    ];
  }

  @override
  Widget buildLeading(BuildContext context) {
    return IconButton(
      icon: AnimatedIcon(
        icon: AnimatedIcons.menu_arrow,
        progress: transitionAnimation,
        color: ColorManager.grey,
      ),
      onPressed: () {
        close(context, null);
      },
    );
  }


  @override
  Widget buildResults(BuildContext context) {
    // show some result based on the app
    return Center(
      child: Container(
        // menampilkan data yang akan di cari
        width: 100.0,
        height: 100.0,
        child: Card(
          color: Colors.red,
          shape: const StadiumBorder(),
          child: Center(
            child: Text(query),
          ),
        ),
      ),
    );
  }

  @override
  void showResults(BuildContext context) {
    close(context, null);
    Map<String,String> map = HashMap();
    map['keywords'] = query;
    Get.toNamed(Routers.productList, parameters: map);
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    final suggestionList = query.isEmpty ? recentList : recommendList.where((p) => p.contains(query)).toList();
    if(query.isEmpty) {
      return Container(
        margin: const EdgeInsets.all(10),
        child: GridView.count(
          crossAxisCount: 5,
          childAspectRatio: 3,
          mainAxisSpacing: 5,
          crossAxisSpacing: 5,
          children: <Widget>[

            ...recentList.map((p) {
              return InkWell(
                onTap: () {
                  Map<String,String> map = HashMap();
                  map['keywords'] = query;
                  Get.toNamed(Routers.productList, parameters: map);
                },
                child: SearchTag(text: Text(p), borderColor: Colors.transparent, color: ColorManager.grey),
              );
            })
          ],
        ),
      );
    }

    return ListView.builder(
      itemBuilder: (context, index) => ListTile(
        onTap:(){
          query = suggestionList[index];
          showResults(context);
        },
        title: RichText(
          text: TextSpan(
              text: suggestionList[index].substring(0, suggestionList[index].indexOf(query)),
              style: const TextStyle(color: ColorManager.grey, fontWeight: FontWeight.normal),
              children: [

                TextSpan(
                  text: suggestionList[index].substring(suggestionList[index].indexOf(query), suggestionList[index].indexOf(query) + query.length),
                  style: const TextStyle(color: ColorManager.red, fontWeight: FontWeight.bold)),

                TextSpan(
                    text: suggestionList[index].substring(suggestionList[index].indexOf(query) + query.length),
                    style: const TextStyle(color: ColorManager.grey, fontWeight: FontWeight.normal))
              ]),
        ),
      ),
      itemCount: suggestionList.length,
    );
  }

  @override
  ThemeData appBarTheme(BuildContext context) {
    return ThemeData(
      inputDecorationTheme: InputDecorationTheme(
        focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(5),
            borderSide: const BorderSide(color: ColorManager.red),
        ),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(5)
        ),
        enabledBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(5),
            borderSide: const BorderSide(color: ColorManager.red)
        )
      ),

      appBarTheme: const AppBarTheme(
        //toolbarHeight: 70,
        color: ColorManager.white,
        toolbarTextStyle: TextStyle(fontWeight: FontWeight.normal),
        shadowColor: ColorManager.grey,
        foregroundColor: ColorManager.orange,
      ),

    );
  }
}