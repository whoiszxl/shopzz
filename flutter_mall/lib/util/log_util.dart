import 'package:flustars/flustars.dart';

class Log {
  static void info(Object object, {String tag}) {
    LogUtil.e(object, tag: tag);
  }

  static void debug(Object object, {String tag}) {
    LogUtil.d(object, tag: tag);
  }
}