class CouponResponse {
  List<CouponEntity> couponList;

  CouponResponse({this.couponList});

  CouponResponse.fromJson(Map<String, dynamic> json) {
    if (json['couponList'] != null) {
      couponList = <CouponEntity>[];
      json['couponList'].forEach((v) {
        couponList.add(CouponEntity.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (couponList != null) {
      data['couponList'] = couponList.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class CouponEntity {
  int id;
  String name;
  String subName;
  String startTime;
  String endTime;
  num useThreshold;
  num discountAmount;
  num discountRate;
  int type;
  int fullLimited;

  CouponEntity(
      {this.id,
        this.name,
        this.subName,
        this.startTime,
        this.endTime,
        this.useThreshold,
        this.discountAmount,
        this.discountRate,
        this.type,
        this.fullLimited});

  CouponEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    subName = json['subName'];
    startTime = json['startTime'];
    endTime = json['endTime'];
    useThreshold = json['useThreshold'];
    discountAmount = json['discountAmount'];
    discountRate = json['discountRate'];
    type = json['type'];
    fullLimited = json['fullLimited'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['subName'] = subName;
    data['startTime'] = startTime;
    data['endTime'] = endTime;
    data['useThreshold'] = useThreshold;
    data['discountAmount'] = discountAmount;
    data['discountRate'] = discountRate;
    data['type'] = type;
    data['fullLimited'] = fullLimited;
    return data;
  }
}
