///主页热门推荐和精品推荐模型，包含了分页相关参数
class RecommendModel {
  List<Content> content;
  int totalPages;
  int number;
  int size;
  bool first;

  RecommendModel(
      {this.content, this.totalPages, this.number, this.size, this.first});

  RecommendModel.fromJson(Map<String, dynamic> json) {
    if (json['content'] != null) {
      content = <Content>[];
      json['content'].forEach((v) {
        content.add(new Content.fromJson(v));
      });
    }
    totalPages = json['totalPages'];
    number = json['number'];
    size = json['size'];
    first = json['first'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.content != null) {
      data['content'] = this.content.map((v) => v.toJson()).toList();
    }
    data['totalPages'] = this.totalPages;
    data['number'] = this.number;
    data['size'] = this.size;
    data['first'] = this.first;
    return data;
  }
}

class Content {
  int id;
  int productId;
  String productName;
  String defaultPic;
  double defaultPrice;
  int type;
  int status;
  int sort;

  Content(
      {this.id,
        this.productId,
        this.productName,
        this.defaultPic,
        this.defaultPrice,
        this.type,
        this.status,
        this.sort});

  Content.fromJson(Map<String, dynamic> json) {
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
