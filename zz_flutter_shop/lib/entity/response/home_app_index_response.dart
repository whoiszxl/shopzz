class HomeAppIndexResponse {
  List<BannerEntity> banners;
  List<NavigationEntity> navigations;

  HomeAppIndexResponse({this.banners, this.navigations});

  HomeAppIndexResponse.fromJson(Map<String, dynamic> json) {
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
  String pic;
  int status;
  int clickCount;
  String url;
  String note;
  int sort;

  BannerEntity(
      {this.id,
        this.name,
        this.type,
        this.pic,
        this.status,
        this.clickCount,
        this.url,
        this.note,
        this.sort});

  BannerEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    type = json['type'];
    pic = json['pic'];
    status = json['status'];
    clickCount = json['clickCount'];
    url = json['url'];
    note = json['note'];
    sort = json['sort'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['type'] = type;
    data['pic'] = pic;
    data['status'] = status;
    data['clickCount'] = clickCount;
    data['url'] = url;
    data['note'] = note;
    data['sort'] = sort;
    return data;
  }
}

class NavigationEntity {
  int id;
  String name;
  int type;
  String pic;
  int status;
  int clickCount;
  String url;
  String note;
  int sort;

  NavigationEntity(
      {this.id,
        this.name,
        this.type,
        this.pic,
        this.status,
        this.clickCount,
        this.url,
        this.note,
        this.sort});

  NavigationEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    type = json['type'];
    pic = json['pic'];
    status = json['status'];
    clickCount = json['clickCount'];
    url = json['url'];
    note = json['note'];
    sort = json['sort'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['type'] = type;
    data['pic'] = pic;
    data['status'] = status;
    data['clickCount'] = clickCount;
    data['url'] = url;
    data['note'] = note;
    data['sort'] = sort;
    return data;
  }
}
