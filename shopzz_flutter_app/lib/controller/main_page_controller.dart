
import 'package:get/get.dart';

class MainPageController extends GetxController {

  //底部bar页面切换下标
  var bottomPageIndex = RxInt(0);

  //滑动开关
  var slideSwitch = RxBool(true);

  //选中指定的bar，并配置第一二个可以滑动
  void selectIndexBottomBarMainPage(int index){
    if(index == 0 || index == 1){
      updateScrollPageScrollState(true);
    }else{
      updateScrollPageScrollState(false);
    }
    bottomPageIndex.value = index;
  }

  //ScrollPage的PageView是否可以滑动
  void updateScrollPageScrollState(bool scroll){
    slideSwitch.value = scroll;
  }

}