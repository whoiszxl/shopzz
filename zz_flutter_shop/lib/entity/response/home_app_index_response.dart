class HomeAppIndexResponse {
  List<BannerEntity> banners;
  List<NavigationEntity> navigations;

  HomeAppIndexResponse({this.banners, this.navigations});

  HomeAppIndexResponse.fromJson(Map<String, dynamic> json) {
    if (json['banners'] != null) {
      banners = new List<BannerEntity>();
      json['banners'].forEach((v) {
        banners.add(new BannerEntity.fromJson(v));
      });
    }
    if (json['navigations'] != null) {
      navigations = new List<NavigationEntity>();
      json['navigations'].forEach((v) {
        navigations.add(new NavigationEntity.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.banners != null) {
      data['banners'] = this.banners.map((v) => v.toJson()).toList();
    }
    if (this.navigations != null) {
      data['navigations'] = this.navigations.map((v) => v.toJson()).toList();
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
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['name'] = this.name;
    data['type'] = this.type;
    data['pic'] = this.pic;
    data['status'] = this.status;
    data['clickCount'] = this.clickCount;
    data['url'] = this.url;
    data['note'] = this.note;
    data['sort'] = this.sort;
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
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['name'] = this.name;
    data['type'] = this.type;
    data['pic'] = this.pic;
    data['status'] = this.status;
    data['clickCount'] = this.clickCount;
    data['url'] = this.url;
    data['note'] = this.note;
    data['sort'] = this.sort;
    return data;
  }
}
