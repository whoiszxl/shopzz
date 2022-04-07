class SpuDetailResponse {
  SpuEntity spuVO;
  List<SkuEntity> skus;
  List<SkuStockEntity> skuStocks;
  List<SpuImageEntity> images;
  SpuDetailEntity spuDetailVO;
  List<SpuAttributeGroupEntity> spuAttributeGroupVOList;

  SpuDetailResponse(
      {this.spuVO,
        this.skus,
        this.skuStocks,
        this.images,
        this.spuDetailVO,
        this.spuAttributeGroupVOList});

  SpuDetailResponse.fromJson(Map<String, dynamic> json) {
    spuVO = json['spuVO'] != null ? SpuEntity.fromJson(json['spuVO']) : null;
    if (json['skus'] != null) {
      skus = <SkuEntity>[];
      json['skus'].forEach((v) {
        skus.add(SkuEntity.fromJson(v));
      });
    }
    if (json['skuStocks'] != null) {
      skuStocks = <SkuStockEntity>[];
      json['skuStocks'].forEach((v) {
        skuStocks.add(SkuStockEntity.fromJson(v));
      });
    }
    if (json['images'] != null) {
      images = <SpuImageEntity>[];
      json['images'].forEach((v) {
        images.add(SpuImageEntity.fromJson(v));
      });
    }
    spuDetailVO = json['spuDetailVO'] != null
        ? SpuDetailEntity.fromJson(json['spuDetailVO'])
        : null;
    if (json['spuAttributeGroupVOList'] != null) {
      spuAttributeGroupVOList = <SpuAttributeGroupEntity>[];
      json['spuAttributeGroupVOList'].forEach((v) {
        spuAttributeGroupVOList.add(SpuAttributeGroupEntity.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (spuVO != null) {
      data['spuVO'] = spuVO.toJson();
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
    if (spuDetailVO != null) {
      data['spuDetailVO'] = spuDetailVO.toJson();
    }
    if (spuAttributeGroupVOList != null) {
      data['spuAttributeGroupVOList'] =
          spuAttributeGroupVOList.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class SpuEntity {
  int id;
  String name;
  String subName;
  int defaultPrice;
  String defaultPic;
  int categoryId;
  int parentCategoryId;
  int brandId;
  String brandName;
  String packageList;
  int defaultSkuId;

  SpuEntity(
      {this.id,
        this.name,
        this.subName,
        this.defaultPrice,
        this.defaultPic,
        this.categoryId,
        this.parentCategoryId,
        this.brandId,
        this.brandName,
        this.packageList,
        this.defaultSkuId});

  SpuEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    subName = json['subName'];
    defaultPrice = json['defaultPrice'];
    defaultPic = json['defaultPic'];
    categoryId = json['categoryId'];
    parentCategoryId = json['parentCategoryId'];
    brandId = json['brandId'];
    brandName = json['brandName'];
    packageList = json['packageList'];
    defaultSkuId = json['defaultSkuId'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['subName'] = subName;
    data['defaultPrice'] = defaultPrice;
    data['defaultPic'] = defaultPic;
    data['categoryId'] = categoryId;
    data['parentCategoryId'] = parentCategoryId;
    data['brandId'] = brandId;
    data['brandName'] = brandName;
    data['packageList'] = packageList;
    data['defaultSkuId'] = defaultSkuId;
    return data;
  }
}

class SkuEntity {
  int id;
  int spuId;
  int categoryId;
  int parentCategoryId;
  String skuName;
  String skuImg;
  int salePrice;
  int promotionPrice;
  String saleAttr;
  String skuCode;

  SkuEntity(
      {this.id,
        this.spuId,
        this.categoryId,
        this.parentCategoryId,
        this.skuName,
        this.skuImg,
        this.salePrice,
        this.promotionPrice,
        this.saleAttr,
        this.skuCode});

  SkuEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    spuId = json['spuId'];
    categoryId = json['categoryId'];
    parentCategoryId = json['parentCategoryId'];
    skuName = json['skuName'];
    skuImg = json['skuImg'];
    salePrice = json['salePrice'];
    promotionPrice = json['promotionPrice'];
    saleAttr = json['saleAttr'];
    skuCode = json['skuCode'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['spuId'] = spuId;
    data['categoryId'] = categoryId;
    data['parentCategoryId'] = parentCategoryId;
    data['skuName'] = skuName;
    data['skuImg'] = skuImg;
    data['salePrice'] = salePrice;
    data['promotionPrice'] = promotionPrice;
    data['saleAttr'] = saleAttr;
    data['skuCode'] = skuCode;
    return data;
  }
}

class SkuStockEntity {
  int id;
  int skuId;
  int saleStockQuantity;
  int stockStatus;

  SkuStockEntity({this.id, this.skuId, this.saleStockQuantity, this.stockStatus});

  SkuStockEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    skuId = json['skuId'];
    saleStockQuantity = json['saleStockQuantity'];
    stockStatus = json['stockStatus'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['skuId'] = skuId;
    data['saleStockQuantity'] = saleStockQuantity;
    data['stockStatus'] = stockStatus;
    return data;
  }
}

class SpuImageEntity {
  int id;
  int spuId;
  String imgUrl;
  int sort;
  int isDefault;

  SpuImageEntity({this.id, this.spuId, this.imgUrl, this.sort, this.isDefault});

  SpuImageEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    spuId = json['spuId'];
    imgUrl = json['imgUrl'];
    sort = json['sort'];
    isDefault = json['isDefault'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['spuId'] = spuId;
    data['imgUrl'] = imgUrl;
    data['sort'] = sort;
    data['isDefault'] = isDefault;
    return data;
  }
}

class SpuDetailEntity {
  String detailHtml;
  String detailMobile;

  SpuDetailEntity({this.detailHtml, this.detailMobile});

  SpuDetailEntity.fromJson(Map<String, dynamic> json) {
    detailHtml = json['detailHtml'];
    detailMobile = json['detailMobile'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['detailHtml'] = detailHtml;
    data['detailMobile'] = detailMobile;
    return data;
  }
}

class SpuAttributeGroupEntity {
  int keyId;
  String keyName;
  List<SpuAttrList> spuAttrList;

  SpuAttributeGroupEntity({this.keyId, this.keyName, this.spuAttrList});

  SpuAttributeGroupEntity.fromJson(Map<String, dynamic> json) {
    keyId = json['keyId'];
    keyName = json['keyName'];
    if (json['spuAttrList'] != null) {
      spuAttrList = <SpuAttrList>[];
      json['spuAttrList'].forEach((v) {
        spuAttrList.add(SpuAttrList.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['keyId'] = keyId;
    data['keyName'] = keyName;
    if (spuAttrList != null) {
      data['spuAttrList'] = spuAttrList.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class SpuAttrList {
  int keyId;
  String key;
  int valueId;
  String value;

  SpuAttrList({this.keyId, this.key, this.valueId, this.value});

  SpuAttrList.fromJson(Map<String, dynamic> json) {
    keyId = json['keyId'];
    key = json['key'];
    valueId = json['valueId'];
    value = json['value'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['keyId'] = keyId;
    data['key'] = key;
    data['valueId'] = valueId;
    data['value'] = value;
    return data;
  }
}
