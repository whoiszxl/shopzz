
import 'package:flutter/material.dart';
import 'package:flutter_mall/navigator/zero_navigator.dart';
import 'package:flutter_mall/widget/tag.dart';

class MySearchDelegate extends SearchDelegate<String> {


  @override
  // TODO: implement searchFieldLabel
  String get searchFieldLabel => "";

  final recommendList = [
    "小米",
    "苹果",
    "Visakhapatham",
    "Coimbatore",
    "Delhi",
    "Bengalore",
    "Pune",
    "Nagpur",
    "Lucknow",
    "Vadodara",
    "Indore",
    "Jalalpur",
    "Bhopal",
    "Kolkata",
    "Kanpur",
    "New Delhi",
    "Faridabad",
    "Rajkot",
    "Ghaziabad",
    "Chennai",
    "Meerut",
    "Agra",
    "Jaipur",
    "Varanasi",
    "Allahabad",
    "Hyderabad",
    "Noida",
    "Howrah",
    "Thane"
  ];

  final recentList = ["小米", "苹果", "Java", "Flutter"];



  @override
  List<Widget> buildActions(BuildContext context) {
    // action for apps bar
    return [
      IconButton(
        icon: Icon(Icons.clear),
        // ketika klik clean maka hapus query
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
  Widget buildResults(BuildContext context) {
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
              return Tag(text: Text(p), borderColor: Colors.transparent, color: Colors.grey[300]);
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
        leading: Icon(Icons.location_city),
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

}