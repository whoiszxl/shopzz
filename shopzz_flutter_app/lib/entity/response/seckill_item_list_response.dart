class SeckillItemListResponse {
  List<SeckillItemEntity> seckillItemList;

  SeckillItemListResponse({this.seckillItemList});

  SeckillItemListResponse.fromJson(Map<String, dynamic> json) {
    if (json['seckillItemVOList'] != null) {
      seckillItemList = <SeckillItemEntity>[];
      json['seckillItemVOList'].forEach((v) {
        seckillItemList.add(SeckillItemEntity.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (seckillItemList != null) {
      data['seckillItemVOList'] =
          seckillItemList.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class SeckillItemEntity {
  num id;
  num seckillId;
  String skuName;
  String skuDescs;
  String skuImg;
  String spuName;
  String startTime;
  String endTime;
  int initStockQuantity;
  int availableStockQuantity;
  int warmUpStatus;
  num skuPrice;
  num seckillPrice;
  int status;

  SeckillItemEntity(
      {this.id,
        this.seckillId,
        this.skuName,
        this.skuDescs,
        this.spuName,
        this.skuImg,
        this.startTime,
        this.endTime,
        this.initStockQuantity,
        this.availableStockQuantity,
        this.warmUpStatus,
        this.skuPrice,
        this.seckillPrice,
        this.status});

  SeckillItemEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    seckillId = json['seckillId'];
    skuName = json['skuName'];
    skuDescs = json['skuDescs'];
    skuImg = json['skuImg'];
    spuName = json['spuName'];
    startTime = json['startTime'];
    endTime = json['endTime'];
    initStockQuantity = json['initStockQuantity'];
    availableStockQuantity = json['availableStockQuantity'];
    warmUpStatus = json['warmUpStatus'];
    skuPrice = json['skuPrice'];
    seckillPrice = json['seckillPrice'];
    status = json['status'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['seckillId'] = seckillId;
    data['skuName'] = skuName;
    data['skuDescs'] = skuDescs;
    data['skuImg'] = skuImg;
    data['spuName'] = spuName;
    data['startTime'] = startTime;
    data['endTime'] = endTime;
    data['initStockQuantity'] = initStockQuantity;
    data['availableStockQuantity'] = availableStockQuantity;
    data['warmUpStatus'] = warmUpStatus;
    data['skuPrice'] = skuPrice;
    data['seckillPrice'] = seckillPrice;
    data['status'] = status;
    return data;
  }
}
