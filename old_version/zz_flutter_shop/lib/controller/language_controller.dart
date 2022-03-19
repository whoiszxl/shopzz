
import 'dart:ui';

import 'package:get/get.dart';

class LanguageController extends GetxController {

  /// 语言切换，传入语言编码和地区编码
  void langSwitch(String languageCode, String regionCode) {
    var locale = Locale(languageCode, regionCode);
    Get.updateLocale(locale);
  }

}