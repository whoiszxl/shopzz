///主页轮播图模型，包含了banner和导航小组件
class IndexBannerModel {
  List<Banners> banners;
  List<Navigations> navigations;

  IndexBannerModel({this.banners, this.navigations});

  IndexBannerModel.fromJson(Map<String, dynamic> json) {
    if (json['banners'] != null) {
      banners = <Banners>[];
      json['banners'].forEach((v) {
        banners.add(new Banners.fromJson(v));
      });
    }
    if (json['navigations'] != null) {
      navigations = <Navigations>[];
      json['navigations'].forEach((v) {
        navigations.add(new Navigations.fromJson(v));
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

class Banners {
  int clickCount;
  int id;
  String name;
  String note;
  String pic;
  int sort;
  int status;
  int type;
  String url;

  Banners(
      {this.clickCount,
        this.id,
        this.name,
        this.note,
        this.pic,
        this.sort,
        this.status,
        this.type,
        this.url});

  Banners.fromJson(Map<String, dynamic> json) {
    clickCount = json['clickCount'];
    id = json['id'];
    name = json['name'];
    note = json['note'];
    pic = json['pic'];
    sort = json['sort'];
    status = json['status'];
    type = json['type'];
    url = json['url'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['clickCount'] = this.clickCount;
    data['id'] = this.id;
    data['name'] = this.name;
    data['note'] = this.note;
    data['pic'] = this.pic;
    data['sort'] = this.sort;
    data['status'] = this.status;
    data['type'] = this.type;
    data['url'] = this.url;
    return data;
  }
}

class Navigations {
  int clickCount;
  int id;
  String name;
  String note;
  String pic;
  int sort;
  int status;
  int type;
  String url;

  Navigations(
      {this.clickCount,
        this.id,
        this.name,
        this.note,
        this.pic,
        this.sort,
        this.status,
        this.type,
        this.url});

  Navigations.fromJson(Map<String, dynamic> json) {
    clickCount = json['clickCount'];
    id = json['id'];
    name = json['name'];
    note = json['note'];
    pic = json['pic'];
    sort = json['sort'];
    status = json['status'];
    type = json['type'];
    url = json['url'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['clickCount'] = this.clickCount;
    data['id'] = this.id;
    data['name'] = this.name;
    data['note'] = this.note;
    data['pic'] = this.pic;
    data['sort'] = this.sort;
    data['status'] = this.status;
    data['type'] = this.type;
    data['url'] = this.url;
    return data;
  }
}
