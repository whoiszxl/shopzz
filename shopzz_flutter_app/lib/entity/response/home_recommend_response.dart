class HomeRecommendResponse {

  List<HomeRecommendEntity> homeRecommendList;

  HomeRecommendResponse({this.homeRecommendList});

  HomeRecommendResponse.fromJson(Map<String, dynamic> json) {
    if (json['homeRecommendList'] != null) {
      homeRecommendList = <HomeRecommendEntity>[];
      json['homeRecommendList'].forEach((v) {
        homeRecommendList.add(HomeRecommendEntity.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (homeRecommendList != null) {
      data['homeRecommendList'] =
          homeRecommendList.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class HomeRecommendEntity {
  int id;
  String name;
  String subName;
  int defaultPrice;
  String defaultPic;

  HomeRecommendEntity(
      {this.id, this.name, this.subName, this.defaultPrice, this.defaultPic});

  HomeRecommendEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    subName = json['subName'];
    defaultPrice = json['defaultPrice'];
    defaultPic = json['defaultPic'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['subName'] = subName;
    data['defaultPrice'] = defaultPrice;
    data['defaultPic'] = defaultPic;
    return data;
  }
}
