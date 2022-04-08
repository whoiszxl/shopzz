import 'package:flutter/material.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';

/// appbar 返回按钮类型
enum AppBarBackType { Back, Close, None }

const double kNavigationBarHeight = 44.0;

// 自定义 AppBar
class MyAppBar extends AppBar implements PreferredSizeWidget {
  MyAppBar(
      {Key key,
        Widget title,
        AppBarBackType leadingType,
        WillPopCallback onWillPop,
        Widget leading,
        Brightness brightness,
        Color backgroundColor,
        List<Widget> actions,
        bool centerTitle = true,
        double elevation})
      : super(
    key: key,
    title: title,
    centerTitle: centerTitle,
    brightness: brightness ?? Brightness.light,
    backgroundColor: backgroundColor ?? const Color(0xfffefefe),
    leading: leading ??
        (leadingType == AppBarBackType.None
            ? Container()
            : AppBarBack(
          leadingType ?? AppBarBackType.Back,
          onWillPop: onWillPop,
        )),
    actions: actions,
    elevation: elevation ?? 0.5,
  );
  @override
  get preferredSize => const Size.fromHeight(44);
}

// 自定义返回按钮
class AppBarBack extends StatelessWidget {
  final AppBarBackType _backType;
  final Color color;
  final WillPopCallback onWillPop;

  AppBarBack(this._backType, {this.onWillPop, this.color});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () async {
        final willBack = onWillPop == null ? true : await onWillPop();
        if (!willBack) return;
        Navigator.pop(context);
      },
      child: _backType == AppBarBackType.Close
          ? Icon(Icons.close, color: color ?? const Color(0xFF222222), size: 24.0)
          : Container(
        padding: const EdgeInsets.only(right: 15),
        child: Icon(Icons.close, color: color),
      ),
    );
  }
}

class MyTitle extends StatelessWidget {
  final String _title;
  final Color color;

  MyTitle(this._title, {this.color});

  @override
  Widget build(BuildContext context) {
    return Text(_title,
        style: TextStyle(color: color ?? ColorManager.fontGrey, fontSize: 16, fontWeight: FontWeight.w500));
  }
}