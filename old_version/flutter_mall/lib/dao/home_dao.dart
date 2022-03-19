
import 'package:flutter_mall/http/core/zero_net.dart';
import 'package:flutter_mall/http/request/home/banner_request.dart';
import 'package:flutter_mall/http/request/home/recommend_request.dart';
import 'package:flutter_mall/model/index_banner_model.dart';
import 'package:flutter_mall/model/recommend_model.dart';
import 'package:flutter_mall/util/log_util.dart';

///主页dao数据访问层
class HomeDao {

  ///获取首页banner与nav导航小组件
  static Future getIndexBanner() async {
    BannerRequest bannerRequest = new BannerRequest();

    var result = await ZeroNet.getInstance().request(bannerRequest);
    Log.debug("banner list" + result['data'].toString());
    return IndexBannerModel.fromJson(result['data']);
  }


  ///通过类型获取精品推荐或热门推荐
  static Future getRecommendByType(int type) async {
    RecommendRequest recommendRequest = new RecommendRequest();
    recommendRequest.addParam("recommendType", type);

    var result = await ZeroNet.getInstance().request(recommendRequest);
    Log.debug("recommend list" + result['data'].toString());
    return RecommendModel.fromJson(result['data']);
  }
}