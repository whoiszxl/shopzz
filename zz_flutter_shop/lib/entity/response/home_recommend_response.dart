class HomeRecommendResponse {
  List<RecommendEntity> hotRecommendList;
  List<RecommendEntity> niceRecommendList;

  HomeRecommendResponse({this.hotRecommendList, this.niceRecommendList});

  HomeRecommendResponse.fromJson(Map<String, dynamic> json) {
    if (json['hotRecommendList'] != null) {
      hotRecommendList = <RecommendEntity>[];
      json['hotRecommendList'].forEach((v) {
        hotRecommendList.add(RecommendEntity.fromJson(v));
      });
    }
    if (json['niceRecommendList'] != null) {
      niceRecommendList = <RecommendEntity>[];
      json['niceRecommendList'].forEach((v) {
        niceRecommendList.add(RecommendEntity.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.hotRecommendList != null) {
      data['hotRecommendList'] =
          this.hotRecommendList.map((v) => v.toJson()).toList();
    }
    if (this.niceRecommendList != null) {
      data['niceRecommendList'] =
          this.niceRecommendList.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class RecommendEntity {
  int id;
  int productId;
  String productName;
  String defaultPic;
  double defaultPrice;
  bool type;
  int status;
  int sort;

  RecommendEntity(
      {this.id,
        this.productId,
        this.productName,
        this.defaultPic,
        this.defaultPrice,
        this.type,
        this.status,
        this.sort});

  RecommendEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    productId = json['productId'];
    productName = json['productName'];
    defaultPic = json['defaultPic'];
    defaultPrice = json['defaultPrice'];
    type = json['type'];
    status = json['status'];
    sort = json['sort'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['productId'] = this.productId;
    data['productName'] = this.productName;
    data['defaultPic'] = this.defaultPic;
    data['defaultPrice'] = this.defaultPrice;
    data['type'] = this.type;
    data['status'] = this.status;
    data['sort'] = this.sort;
    return data;
  }
}