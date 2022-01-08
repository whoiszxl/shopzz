import 'package:flutter/material.dart';
import 'package:oktoast/oktoast.dart';
import 'package:zz_flutter_shop/entity/response/member_info_response.dart';
import 'package:zz_flutter_shop/page/widget/member/member_header_tag.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/utils/image_util.dart';

///会员中心头部页
class MemberHeader extends StatelessWidget {

  final Member member;
  final MemberInfo memberInfo;

  MemberHeader({Key key, this.member, this.memberInfo}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      decoration: const BoxDecoration(
        image: DecorationImage(
          image: AssetImage('images/member_bg.jpg'),
          fit: BoxFit.fitWidth
        )
      ),

      child: Stack(
        children: <Widget>[
          Column(
            children: <Widget>[

              //头部，包含头像，用户名，小标签
              header(),

              floor(),
            ],
          )
        ],
      ),
    );
  }

  Widget floor() {
    return Container(
      margin: const EdgeInsets.only(top: 15, bottom: 10),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: <Widget>[

          floorTab("商品收藏", "123", () {
            showToast("商品收藏");
          }),
          floorTab("店铺关注", "32", () {
            showToast("店铺关注");
          }),
          floorTab("喜欢的内容", "345", () {
            showToast("喜欢的内容");
          }),
          floorTab("浏览记录", "999", () {
            showToast("浏览记录");
          }),

        ],
      ),
    );
  }

  //头部，包含头像，用户名，小标签
  Widget header() {
    return Container(
      height: 80,
      margin: const EdgeInsets.only(top: 80),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.end,
        children: <Widget>[

          //头像
          Padding(
            padding: const EdgeInsets.only(left: 10, right: 10),
            child: profilePhoto(memberInfo.profilePhoto),
          ),

          //网名和标语
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[

              Row(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  username(member.username),
                  gradeLevel(memberInfo.gradeLevel)
                ],
              ),


              Row(
                children: <Widget>[
                  tag("积分值1023"),
                  tag("成长值7655"),
                  tag("家庭号 >")
                ],
              )

            ],
          )
        ],
      ),
    );
  }


  Widget floorTab(String tabName, String tabValue, Function function) {
    return InkWell(
      onTap: function,
      child: Container(
        color: Colors.transparent,
        child: Column(
          children: <Widget>[

            Text(tabValue, textAlign: TextAlign.center, style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: Colors.white)),

            Text(tabName, textAlign: TextAlign.center, style: const TextStyle(fontSize: 12, color: Colors.white))
          ],
        ),
      ),
    );
  }

  Widget profilePhoto(String url) {
    return Container(
      width: 60,
      height: 60,
      decoration: BoxDecoration(
        border: Border.all(
          width: 2,
          color: Colors.white,
        ),
        borderRadius: const BorderRadius.all(Radius.circular(30)),
      ),
      child: CircleAvatar(
        child: ClipOval(
          child: cachedImage(url, width: 60, height: 60, fit: BoxFit.cover)
        ),
        backgroundColor: Colors.white,
      ),
    );
  }


  Widget username(String username) {
    return Text(username,
      style: const TextStyle(color: Colors.white, fontWeight: FontWeight.bold, fontSize: 16)
    );
  }

  Widget gradeLevel(String level) {
    return Container(
        color: ColorManager.red,
        padding: const EdgeInsets.only(left: 3, right: 3),
        margin: const EdgeInsets.only(left: 6),
        alignment: Alignment.centerLeft,
        height: 12,
        child: Text(memberInfo.gradeLevel, style: const TextStyle(color: Colors.white, fontSize: 8))
    );
  }

  Widget tag(String tagValue) {
    return Tag(
      color: Colors.black12,
      borderColor: Colors.black12,
      borderWidth: 0,
      margin: const EdgeInsets.only(top: 2, right: 2),
      height: 12,
      text: Text(tagValue, style: const TextStyle(color: Colors.white, fontSize: 8)),
    );
  }
}