///商品搜索结果
class ProductSearchResultModel {

  //商品内容
  List<ProductInfo> content;

  //总页数
  int totalPages;

  //页码
  int number;

  //每页数
  int size;

  //是否是首页
  bool first;

  ProductSearchResultModel(
      {this.content, this.totalPages, this.number, this.size, this.first});

  ProductSearchResultModel.fromJson(Map<String, dynamic> json) {
    if (json['content'] != null) {
      content = <ProductInfo>[];
      json['content'].forEach((v) {
        content.add(new ProductInfo.fromJson(v));
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

class ProductInfo {
  int id;
  String name;
  String subName;
  String defaultPic;
  String defaultPrice;
  int categoryId;
  int brandId;
  String brandName;
  double grossWeight;
  double length;
  double width;
  double height;
  String serviceGuarantees;
  String packageList;
  int freightTemplateId;

  ProductInfo(
      {this.id,
        this.name,
        this.subName,
        this.defaultPic,
        this.defaultPrice,
        this.categoryId,
        this.brandId,
        this.brandName,
        this.grossWeight,
        this.length,
        this.width,
        this.height,
        this.serviceGuarantees,
        this.packageList,
        this.freightTemplateId});

  ProductInfo.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    subName = json['subName'];
    defaultPic = json['defaultPic'];
    defaultPrice = json['defaultPrice'];
    categoryId = json['categoryId'];
    brandId = json['brandId'];
    brandName = json['brandName'];
    grossWeight = json['grossWeight'];
    length = json['length'];
    width = json['width'];
    height = json['height'];
    serviceGuarantees = json['serviceGuarantees'];
    packageList = json['packageList'];
    freightTemplateId = json['freightTemplateId'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['name'] = this.name;
    data['subName'] = this.subName;
    data['defaultPic'] = this.defaultPic;
    data['defaultPrice'] = this.defaultPrice;
    data['categoryId'] = this.categoryId;
    data['brandId'] = this.brandId;
    data['brandName'] = this.brandName;
    data['grossWeight'] = this.grossWeight;
    data['length'] = this.length;
    data['width'] = this.width;
    data['height'] = this.height;
    data['serviceGuarantees'] = this.serviceGuarantees;
    data['packageList'] = this.packageList;
    data['freightTemplateId'] = this.freightTemplateId;
    return data;
  }
}
