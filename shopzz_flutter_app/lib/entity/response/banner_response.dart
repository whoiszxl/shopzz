class BannerResponse {
  List<BannerEntity> banners;
  List<NavigationEntity> navigations;

  BannerResponse({this.banners, this.navigations});

  BannerResponse.fromJson(Map<String, dynamic> json) {
    if (json['banners'] != null) {
      banners = <BannerEntity>[];
      json['banners'].forEach((v) {
        banners.add(BannerEntity.fromJson(v));
      });
    }
    if (json['navigations'] != null) {
      navigations = <NavigationEntity>[];
      json['navigations'].forEach((v) {
        navigations.add(NavigationEntity.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (banners != null) {
      data['banners'] = banners.map((v) => v.toJson()).toList();
    }
    if (navigations != null) {
      data['navigations'] = navigations.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class BannerEntity {
  int id;
  String name;
  int type;
  int bizId;
  String pic;
  String url;
  int sort;

  BannerEntity(
      {this.id,
        this.name,
        this.type,
        this.bizId,
        this.pic,
        this.url,
        this.sort});

  BannerEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    type = json['type'];
    bizId = json['bizId'];
    pic = json['pic'];
    url = json['url'];
    sort = json['sort'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['type'] = type;
    data['bizId'] = bizId;
    data['pic'] = pic;
    data['url'] = url;
    data['sort'] = sort;
    return data;
  }
}

class NavigationEntity {
  int id;
  String name;
  int type;
  int bizId;
  String pic;
  String url;
  int sort;

  NavigationEntity(
      {this.id,
        this.name,
        this.type,
        this.bizId,
        this.pic,
        this.url,
        this.sort});

  NavigationEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    type = json['type'];
    bizId = json['bizId'];
    pic = json['pic'];
    url = json['url'];
    sort = json['sort'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['type'] = type;
    data['bizId'] = bizId;
    data['pic'] = pic;
    data['url'] = url;
    data['sort'] = sort;
    return data;
  }
}
