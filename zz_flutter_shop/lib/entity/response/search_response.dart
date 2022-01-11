class SearchResponse {
  List<ProductInfo> records;
  int total;
  int size;
  int current;
  bool optimizeCountSql;
  bool searchCount;
  int pages;

  SearchResponse(
      {this.records,
        this.total,
        this.size,
        this.current,
        this.optimizeCountSql,
        this.searchCount,
        this.pages});

  SearchResponse.fromJson(Map<String, dynamic> json) {
    if (json['records'] != null) {
      records = <ProductInfo>[];
      json['records'].forEach((v) {
        records.add(ProductInfo.fromJson(v));
      });
    }
    total = json['total'];
    size = json['size'];
    current = json['current'];
    optimizeCountSql = json['optimizeCountSql'];
    searchCount = json['searchCount'];
    pages = json['pages'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (records != null) {
      data['records'] = records.map((v) => v.toJson()).toList();
    }
    data['total'] = total;
    data['size'] = size;
    data['current'] = current;
    data['optimizeCountSql'] = optimizeCountSql;
    data['searchCount'] = searchCount;
    data['pages'] = pages;
    return data;
  }
}

class ProductInfo {
  int id;
  String name;
  String subName;
  double defaultPrice;
  String defaultPic;
  int categoryId;
  int brandId;
  String brandName;
  double grossWeight;
  double length;
  double width;
  double height;
  String serviceGuarantees;
  int deleteStatus;
  int publishStatus;
  int verifyStatus;
  String packageList;
  int freightTemplateId;
  int version;
  int isDeleted;
  String createdBy;
  String updatedBy;
  String createdAt;
  String updatedAt;

  ProductInfo(
      {this.id,
        this.name,
        this.subName,
        this.defaultPrice,
        this.defaultPic,
        this.categoryId,
        this.brandId,
        this.brandName,
        this.grossWeight,
        this.length,
        this.width,
        this.height,
        this.serviceGuarantees,
        this.deleteStatus,
        this.publishStatus,
        this.verifyStatus,
        this.packageList,
        this.freightTemplateId,
        this.version,
        this.isDeleted,
        this.createdBy,
        this.updatedBy,
        this.createdAt,
        this.updatedAt});

  ProductInfo.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    subName = json['subName'];
    defaultPrice = json['defaultPrice'];
    defaultPic = json['defaultPic'];
    categoryId = json['categoryId'];
    brandId = json['brandId'];
    brandName = json['brandName'];
    grossWeight = json['grossWeight'];
    length = json['length'];
    width = json['width'];
    height = json['height'];
    serviceGuarantees = json['serviceGuarantees'];
    deleteStatus = json['deleteStatus'];
    publishStatus = json['publishStatus'];
    verifyStatus = json['verifyStatus'];
    packageList = json['packageList'];
    freightTemplateId = json['freightTemplateId'];
    version = json['version'];
    isDeleted = json['isDeleted'];
    createdBy = json['createdBy'];
    updatedBy = json['updatedBy'];
    createdAt = json['createdAt'];
    updatedAt = json['updatedAt'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = id;
    data['name'] = name;
    data['subName'] = subName;
    data['defaultPrice'] = defaultPrice;
    data['defaultPic'] = defaultPic;
    data['categoryId'] = categoryId;
    data['brandId'] = brandId;
    data['brandName'] = brandName;
    data['grossWeight'] = grossWeight;
    data['length'] = length;
    data['width'] = width;
    data['height'] = height;
    data['serviceGuarantees'] = serviceGuarantees;
    data['deleteStatus'] = deleteStatus;
    data['publishStatus'] = publishStatus;
    data['verifyStatus'] = verifyStatus;
    data['packageList'] = packageList;
    data['freightTemplateId'] = freightTemplateId;
    data['version'] = version;
    data['isDeleted'] = isDeleted;
    data['createdBy'] = createdBy;
    data['updatedBy'] = updatedBy;
    data['createdAt'] = createdAt;
    data['updatedAt'] = updatedAt;
    return data;
  }
}
