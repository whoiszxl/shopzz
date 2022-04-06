import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/response/category_response.dart';

class OneCategoryTab extends StatefulWidget {

  final Null Function(int) onChildClick;

  List<CategoryEntity> categoryList = [];

  OneCategoryTab({Key key, @required this.categoryList, @required this.onChildClick}) : super(key: key);

  @override
  OneCategoryTabState createState() => OneCategoryTabState();
}

class OneCategoryTabState extends State<OneCategoryTab> {

  int activeIndex = 0;

  ScrollController scrollController;

  @override
  void initState() {
    scrollController = ScrollController();
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
    scrollController.dispose();
  }


  @override
  Widget build(BuildContext context) {
    return Obx(() => SizedBox(
      width: 80,
      child: ListView.builder(
        physics: const BouncingScrollPhysics(),
        shrinkWrap: true,
        controller: scrollController,
        itemCount: widget.categoryList.length,
        itemBuilder: (context, index) {
          return GestureDetector(
            onTap: () {
              setState(() {
                activeIndex = index;
                widget.onChildClick(index);
              });
            },

            child: Stack(
              children: <Widget>[

                Container(
                  height: 35,
                  color: activeIndex == index
                      ? const Color(0xFFFEFFFF)
                      : const Color(0xFFF1F2F3),
                  child: Center(
                    child: Text(
                      widget.categoryList[index].name,
                      style: TextStyle(
                        fontSize: 12,
                        fontWeight: activeIndex == index ? FontWeight.bold : FontWeight.normal,
                      ),
                    ),
                  ),
                ),

                Positioned(
                  top: 0,
                  left: 0,
                  child: Offstage(
                    offstage: activeIndex != index,
                    child: Container(
                      width: 3,
                      height: 35,
                      color: const Color(0xFFDA3E27),
                    ),
                  ),
                ),
              ],
            ),
          );
        },

      ),
    ));
  }

}