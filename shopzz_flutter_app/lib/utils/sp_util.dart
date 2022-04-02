import 'package:shared_preferences/shared_preferences.dart';

///shared_preferences工具类
class SPUtil{

  ///判断类型设置值到sp
  static set(String key,value) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    if (value is String) {
      prefs.setString(key, value);
    } else if (value is num) {
      prefs.setInt(key, value);
    } else if (value is double) {
      prefs.setDouble(key, value);
    } else if (value is bool) {
      prefs.setBool(key, value);
    } else if (value is List) {
      prefs.setStringList(key, value.cast<String>());
    }
  }


  static getInt(String key) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    int data = prefs.getInt(key);
    return data;
  }

  static getString(String key) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String data = prefs.getString(key);
    return data;
  }


  static getDouble(String key) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    double data = prefs.getDouble(key);
    return data;
  }

  static getBool(String key) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    bool data = prefs.getBool(key);
    return data;
  }

  static getStringList<T>(String key) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    List data = prefs.getStringList(key);
    return data;
  }


  static remove(String key) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.remove(key);
  }

  static removeAll() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.clear();
  }
}