class ColumnDetailResponse {
  int id;
  String name;
  String descs;
  String enterImg;
  String bannerImg;
  int status;
  int clickCount;
  int sort;
  List<ColumnSpuEntity> spuList;

  ColumnDetailResponse(
      {this.id,
        this.name,
        this.descs,
        this.enterImg,
        this.bannerImg,
        this.status,
        this.clickCount,
        this.sort,
        this.spuList});

  ColumnDetailResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    descs = json['descs'];
    enterImg = json['enterImg'];
    bannerImg = json['bannerImg'];
    status = json['status'];
    clickCount = json['clickCount'];
    sort = json['sort'];
    if (json['spuList'] != null) {
      spuList = <ColumnSpuEntity>[];
      json['spuList'].forEach((v) {
        spuList.add(ColumnSpuEntity.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['descs'] = descs;
    data['enterImg'] = enterImg;
    data['bannerImg'] = bannerImg;
    data['status'] = status;
    data['clickCount'] = clickCount;
    data['sort'] = sort;
    if (spuList != null) {
      data['spuList'] = spuList.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class ColumnSpuEntity {
  int id;
  String name;
  String subName;
  num defaultPrice;
  String defaultPic;

  ColumnSpuEntity(
      {this.id, this.name, this.subName, this.defaultPrice, this.defaultPic});

  ColumnSpuEntity.fromJson(Map<String, dynamic> json) {
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
