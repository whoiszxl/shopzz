import 'package:flutter/material.dart';
import 'package:flutter_mall/core/zero_state.dart';
import 'package:flutter_mall/util/color.dart';
import 'package:flutter_mall/util/log_util.dart';

///通用State，支持分页与刷新 M为数据模型 L为列表数据模型 T为widget
abstract class ZeroBasePageRefreshState<M, L, T extends StatefulWidget> extends ZeroState<T> with AutomaticKeepAliveClientMixin {

  List<L> dataList = [];
  int pageIndex = 1;
  int pageSize = 10;
  bool loading = false;
  ScrollController scrollController = ScrollController();

  //被实现，返回具体组件
  get contentChild;

  @override
  void initState() {
    super.initState();
    loadData();
    scrollController.addListener(() {
      var dis = scrollController.position.maxScrollExtent - scrollController.position.pixels;

      if (dis < 300 && !loading && scrollController.position.maxScrollExtent != 0) {
        loadData(loadMore: true);
      }
    });
  }

  @override
  void dispose() {
    super.dispose();
    scrollController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    super.build(context);
    return RefreshIndicator(
      onRefresh: loadData,
      color: primary,
      child: MediaQuery.removePadding(context: context, child: contentChild, removeTop: true),
    );
  }

  ///获取对应页码的数据
  Future<M> getData(int pageIndex, int size);

  ///从MO中解析出list数据
  List<L> parseList(M result);

  ///加载数据
  Future<void> loadData({loadMore = false}) async {
    if(loading) {
      Log.info("还在加载中");
      return;
    }
    loading = true;

    if(!loadMore) {
      pageIndex = 0;
    }
    var currentIndex = pageIndex + (loadMore ? 1 : 0);

    try{
      var result = await getData(currentIndex, pageSize);
      setState(() {
        if(loadMore) {
          dataList = [...dataList, ...parseList(result)];
          if (parseList(result).length != 0) {
            pageIndex++;
          }
        }else {
          dataList = parseList(result);
        }
      });

      Future.delayed(Duration(milliseconds: 1000), () {
        loading = false;
      });
    } on Exception catch (e) {
      loading = false;
      Log.info("加载出错" + e.toString());
    }
  }

  @override
  bool get wantKeepAlive => true;
}