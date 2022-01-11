class ProductDetailResponse {
  ProductVO productVO;
  List<Skus> skus;
  List<SkuStocks> skuStocks;
  List<Images> images;
  List<SaleAttr> saleAttr;
  List<SaleAttrGroup> saleAttrGroup;
  List<GroupAttrs> groupAttrs;

  ProductDetailResponse(
      {this.productVO,
        this.skus,
        this.skuStocks,
        this.images,
        this.saleAttr,
        this.saleAttrGroup,
        this.groupAttrs});

  ProductDetailResponse.fromJson(Map<String, dynamic> json) {
    productVO = json['productVO'] != null
        ? ProductVO.fromJson(json['productVO'])
        : null;
    if (json['skus'] != null) {
      skus = <Skus>[];
      json['skus'].forEach((v) {
        skus.add(Skus.fromJson(v));
      });
    }
    if (json['skuStocks'] != null) {
      skuStocks = <SkuStocks>[];
      json['skuStocks'].forEach((v) {
        skuStocks.add(SkuStocks.fromJson(v));
      });
    }
    if (json['images'] != null) {
      images = <Images>[];
      json['images'].forEach((v) {
        images.add(Images.fromJson(v));
      });
    }
    if (json['saleAttr'] != null) {
      saleAttr = <SaleAttr>[];
      json['saleAttr'].forEach((v) {
        saleAttr.add(SaleAttr.fromJson(v));
      });
    }
    if (json['saleAttrGroup'] != null) {
      saleAttrGroup = <SaleAttrGroup>[];
      json['saleAttrGroup'].forEach((v) {
        saleAttrGroup.add(SaleAttrGroup.fromJson(v));
      });
    }
    if (json['groupAttrs'] != null) {
      groupAttrs = <GroupAttrs>[];
      json['groupAttrs'].forEach((v) {
        groupAttrs.add(GroupAttrs.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (productVO != null) {
      data['productVO'] = productVO.toJson();
    }
    if (skus != null) {
      data['skus'] = skus.map((v) => v.toJson()).toList();
    }
    if (skuStocks != null) {
      data['skuStocks'] = skuStocks.map((v) => v.toJson()).toList();
    }
    if (images != null) {
      data['images'] = images.map((v) => v.toJson()).toList();
    }
    if (saleAttr != null) {
      data['saleAttr'] = saleAttr.map((v) => v.toJson()).toList();
    }
    if (saleAttrGroup != null) {
      data['saleAttrGroup'] =
          saleAttrGroup.map((v) => v.toJson()).toList();
    }
    if (groupAttrs != null) {
      data['groupAttrs'] = groupAttrs.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class ProductVO {
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

  ProductVO(
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
        this.freightTemplateId});

  ProductVO.fromJson(Map<String, dynamic> json) {
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
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
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
  double purchasePrice;
  double promotionPrice;
  double salePrice;
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
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['productId'] = productId;
    data['categoryId'] = categoryId;
    data['skuName'] = skuName;
    data['brandId'] = brandId;
    data['imgUrl'] = imgUrl;
    data['purchasePrice'] = purchasePrice;
    data['promotionPrice'] = promotionPrice;
    data['salePrice'] = salePrice;
    data['saleData'] = saleData;
    return data;
  }
}

class SkuStocks {
  int skuId;
  int quantity;

  SkuStocks({this.skuId, this.quantity});

  SkuStocks.fromJson(Map<String, dynamic> json) {
    skuId = json['skuId'];
    quantity = json['quantity'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['skuId'] = skuId;
    data['quantity'] = quantity;
    return data;
  }
}

class Images {
  int id;
  int productId;
  String imgName;
  String imgUrl;
  int sort;
  int defaultImg;

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
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['productId'] = productId;
    data['imgName'] = imgName;
    data['imgUrl'] = imgUrl;
    data['sort'] = sort;
    data['defaultImg'] = defaultImg;
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
    final Map<String, dynamic> data = <String, dynamic>{};
    data['attributeId'] = attributeId;
    data['attributeName'] = attributeName;
    data['attributeValue'] = attributeValue;
    data['skuIds'] = skuIds;
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
      attrValues = <AttrValues>[];
      json['attrValues'].forEach((v) {
        attrValues.add(AttrValues.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['attrId'] = attrId;
    data['attrName'] = attrName;
    if (attrValues != null) {
      data['attrValues'] = attrValues.map((v) => v.toJson()).toList();
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
    final Map<String, dynamic> data = <String, dynamic>{};
    data['attributeId'] = attributeId;
    data['attributeName'] = attributeName;
    data['attributeValue'] = attributeValue;
    data['skuIds'] = skuIds;
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
    final Map<String, dynamic> data = <String, dynamic>{};
    data['groupName'] = groupName;
    data['attributeId'] = attributeId;
    data['attributeName'] = attributeName;
    data['attributeValue'] = attributeValue;
    return data;
  }
}
