
import 'package:flutter/material.dart';
import 'package:flutter_mall/navigator/zero_navigator.dart';
import 'package:flutter_mall/util/color.dart';
import 'package:flutter_mall/widget/tag.dart';

class MySearchDelegate extends SearchDelegate<String> {


  @override
  String get searchFieldLabel => "";

  final recommendList = [
    "小米",
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
        icon: Icon(Icons.clear),
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
      ),
      onPressed: () {
        close(context, null);
      },
    );
  }


  @override
  Widget buildResults(BuildContext context) => Container();

  @override
  void showResults(BuildContext context) {
    ZeroNavigator.getInstance().onJumpTo(RouteStatus.product_list, args: {'query': query});
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    final suggestionList = query.isEmpty ? recentList : recommendList.where((p) => p.startsWith(query)).toList();

    if(query.isEmpty) {
      return Container(
        margin: EdgeInsets.all(10),
        child: GridView.count(
          crossAxisCount: 5,
          childAspectRatio: 3,
          mainAxisSpacing: 5,
          crossAxisSpacing: 5,
          children: <Widget>[

            ...recentList.map((p) {
              return InkWell(
                onTap: () {
                  ZeroNavigator.getInstance().onJumpTo(RouteStatus.product_list, args: {'query': p});
                },
                child: Tag(text: Text(p), borderColor: Colors.transparent, color: Colors.grey[300]),
              );
            })
          ],
        ),
      );
    }
    return ListView.builder(
      itemBuilder: (context, index) => ListTile(
        onTap:(){
          showResults(context);
        },
        leading: Icon(Icons.adb),
        title: RichText(
          text: TextSpan(
              text: suggestionList[index].substring(0, query.length),
              style:
              TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
              children: [TextSpan(
                  text: suggestionList[index].substring(query.length),
                  style: TextStyle(color: Colors.grey)
              )]),
        ),
      ),
      itemCount: suggestionList.length,
    );
  }


  @override
  ThemeData appBarTheme(BuildContext context) {
    assert(context != null);
    final ThemeData theme = Theme.of(context);
    assert(theme != null);

    return theme.copyWith(
      //设置光标颜色
      textSelectionTheme: TextSelectionThemeData(cursorColor: primary),

      //主色亮度
      primaryColorBrightness: Brightness.dark,
      textTheme: theme.textTheme.copyWith(
        headline6: TextStyle(fontWeight: FontWeight.normal),
      ),
    );
  }
}