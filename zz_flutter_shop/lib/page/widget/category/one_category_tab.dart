import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/entity/dto/category_tree_model.dart';

class OneCategoryTab extends StatefulWidget {

  final Null Function(int) onChildClick;

  List<Categorys> categoryList = [];

  OneCategoryTab({Key key, @required this.categoryList, @required this.onChildClick}) : super(key: key);

  @override
  OneCategoryTabState createState() => OneCategoryTabState();
}

class OneCategoryTabState extends State<OneCategoryTab> {

  int activeIndex = 0;

  @override
  void initState() {
    super.initState();
  }


  @override
  Widget build(BuildContext context) {
    return Obx(() => SizedBox(
      width: 80,
      child: ListView.builder(
        physics: const BouncingScrollPhysics(),
        shrinkWrap: true,
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