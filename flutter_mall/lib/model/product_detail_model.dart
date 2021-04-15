class ProductDetailModel {
  ProductVO productVO;
  List<Skus> skus;
  List<Images> images;
  List<SaleAttr> saleAttr;
  List<SaleAttrGroup> saleAttrGroup;
  List<GroupAttrs> groupAttrs;

  ProductDetailModel(
      {this.productVO,
        this.skus,
        this.images,
        this.saleAttr,
        this.saleAttrGroup,
        this.groupAttrs});

  ProductDetailModel.fromJson(Map<String, dynamic> json) {
    productVO = json['productVO'] != null
        ? new ProductVO.fromJson(json['productVO'])
        : null;
    if (json['skus'] != null) {
      skus = new List<Skus>();
      json['skus'].forEach((v) {
        skus.add(new Skus.fromJson(v));
      });
    }
    if (json['images'] != null) {
      images = new List<Images>();
      json['images'].forEach((v) {
        images.add(new Images.fromJson(v));
      });
    }
    if (json['saleAttr'] != null) {
      saleAttr = new List<SaleAttr>();
      json['saleAttr'].forEach((v) {
        saleAttr.add(new SaleAttr.fromJson(v));
      });
    }
    if (json['saleAttrGroup'] != null) {
      saleAttrGroup = new List<SaleAttrGroup>();
      json['saleAttrGroup'].forEach((v) {
        saleAttrGroup.add(new SaleAttrGroup.fromJson(v));
      });
    }
    if (json['groupAttrs'] != null) {
      groupAttrs = new List<GroupAttrs>();
      json['groupAttrs'].forEach((v) {
        groupAttrs.add(new GroupAttrs.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.productVO != null) {
      data['productVO'] = this.productVO.toJson();
    }
    if (this.skus != null) {
      data['skus'] = this.skus.map((v) => v.toJson()).toList();
    }
    if (this.images != null) {
      data['images'] = this.images.map((v) => v.toJson()).toList();
    }
    if (this.saleAttr != null) {
      data['saleAttr'] = this.saleAttr.map((v) => v.toJson()).toList();
    }
    if (this.saleAttrGroup != null) {
      data['saleAttrGroup'] =
          this.saleAttrGroup.map((v) => v.toJson()).toList();
    }
    if (this.groupAttrs != null) {
      data['groupAttrs'] = this.groupAttrs.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class ProductVO {
  int id;
  String name;
  String subName;
  String defaultPic;
  String defaultPrice;
  int categoryId;
  int brandId;
  String brandName;
  double grossWeight;
  int length;
  int width;
  int height;
  String serviceGuarantees;
  String packageList;
  int freightTemplateId;

  ProductVO(
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

  ProductVO.fromJson(Map<String, dynamic> json) {
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

class Skus {
  int id;
  int productId;
  int categoryId;
  String skuName;
  int brandId;
  String imgUrl;
  int purchasePrice;
  int promotionPrice;
  int salePrice;
  String saleData;

  Skus(
      {this.id,
        this.productId,
        this.categoryId,
        this.skuName,
        this.brandId,
        this.imgUrl,
        this.purchasePrice,
        this.promotionPrice,
        this.salePrice,
        this.saleData});

  Skus.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    productId = json['productId'];
    categoryId = json['categoryId'];
    skuName = json['skuName'];
    brandId = json['brandId'];
    imgUrl = json['imgUrl'];
    purchasePrice = json['purchasePrice'];
    promotionPrice = json['promotionPrice'];
    salePrice = json['salePrice'];
    saleData = json['saleData'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['productId'] = this.productId;
    data['categoryId'] = this.categoryId;
    data['skuName'] = this.skuName;
    data['brandId'] = this.brandId;
    data['imgUrl'] = this.imgUrl;
    data['purchasePrice'] = this.purchasePrice;
    data['promotionPrice'] = this.promotionPrice;
    data['salePrice'] = this.salePrice;
    data['saleData'] = this.saleData;
    return data;
  }
}

class Images {
  int id;
  int productId;
  Null imgName;
  String imgUrl;
  Null sort;
  Null defaultImg;

  Images(
      {this.id,
        this.productId,
        this.imgName,
        this.imgUrl,
        this.sort,
        this.defaultImg});

  Images.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    productId = json['productId'];
    imgName = json['imgName'];
    imgUrl = json['imgUrl'];
    sort = json['sort'];
    defaultImg = json['defaultImg'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['productId'] = this.productId;
    data['imgName'] = this.imgName;
    data['imgUrl'] = this.imgUrl;
    data['sort'] = this.sort;
    data['defaultImg'] = this.defaultImg;
    return data;
  }
}

class SaleAttr {
  int attributeId;
  String attributeName;
  String attributeValue;
  String skuIds;

  SaleAttr(
      {this.attributeId, this.attributeName, this.attributeValue, this.skuIds});

  SaleAttr.fromJson(Map<String, dynamic> json) {
    attributeId = json['attributeId'];
    attributeName = json['attributeName'];
    attributeValue = json['attributeValue'];
    skuIds = json['skuIds'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['attributeId'] = this.attributeId;
    data['attributeName'] = this.attributeName;
    data['attributeValue'] = this.attributeValue;
    data['skuIds'] = this.skuIds;
    return data;
  }
}

class SaleAttrGroup {
  int attrId;
  String attrName;
  List<AttrValues> attrValues;

  SaleAttrGroup({this.attrId, this.attrName, this.attrValues});

  SaleAttrGroup.fromJson(Map<String, dynamic> json) {
    attrId = json['attrId'];
    attrName = json['attrName'];
    if (json['attrValues'] != null) {
      attrValues = new List<AttrValues>();
      json['attrValues'].forEach((v) {
        attrValues.add(new AttrValues.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['attrId'] = this.attrId;
    data['attrName'] = this.attrName;
    if (this.attrValues != null) {
      data['attrValues'] = this.attrValues.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class AttrValues {
  int attributeId;
  String attributeName;
  String attributeValue;
  String skuIds;

  AttrValues(
      {this.attributeId, this.attributeName, this.attributeValue, this.skuIds});

  AttrValues.fromJson(Map<String, dynamic> json) {
    attributeId = json['attributeId'];
    attributeName = json['attributeName'];
    attributeValue = json['attributeValue'];
    skuIds = json['skuIds'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['attributeId'] = this.attributeId;
    data['attributeName'] = this.attributeName;
    data['attributeValue'] = this.attributeValue;
    data['skuIds'] = this.skuIds;
    return data;
  }
}

class GroupAttrs {
  String groupName;
  int attributeId;
  String attributeName;
  String attributeValue;

  GroupAttrs(
      {this.groupName,
        this.attributeId,
        this.attributeName,
        this.attributeValue});

  GroupAttrs.fromJson(Map<String, dynamic> json) {
    groupName = json['groupName'];
    attributeId = json['attributeId'];
    attributeName = json['attributeName'];
    attributeValue = json['attributeValue'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['groupName'] = this.groupName;
    data['attributeId'] = this.attributeId;
    data['attributeName'] = this.attributeName;
    data['attributeValue'] = this.attributeValue;
    return data;
  }
}
