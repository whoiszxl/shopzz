import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/entity/dto/category_tree_model.dart';

class OneCategoryTab extends StatefulWidget {

  final Null Function(int) onChildClick;

  List<Categorys> categorys = [];

  OneCategoryTab({Key key, @required this.categorys, @required this.onChildClick}) : super(key: key);

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
    return Obx(() => Container(
      width: 80,
      child: ListView.builder(
        physics: BouncingScrollPhysics(),
        shrinkWrap: true,
        itemCount: widget.categorys.length,
        itemBuilder: (context, index) {
          return GestureDetector(
            onTap: () {
              this.setState(() {
                this.activeIndex = index;
                widget.onChildClick(index);
              });
            },

            child: Stack(
              children: <Widget>[

                Container(
                  height: 35,
                  color: this.activeIndex == index
                      ? Color(0xFFFEFFFF)
                      : Color(0xFFF1F2F3),
                  child: Center(
                    child: Text(
                      widget.categorys[index].name,
                      style: TextStyle(
                        fontSize: 12,
                        fontWeight: this.activeIndex == index ? FontWeight.bold : FontWeight.normal,
                      ),
                    ),
                  ),
                ),

                Positioned(
                  top: 0,
                  left: 0,
                  child: Offstage(
                    offstage: this.activeIndex != index,
                    child: Container(
                      width: 3,
                      height: 35,
                      color: Color(0xFFDA3E27),
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