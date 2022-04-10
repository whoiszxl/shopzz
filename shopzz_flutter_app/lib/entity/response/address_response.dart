class AddressResponse {
  AddressEntity mainAddress;
  List<AddressEntity> addressList;

  AddressResponse({this.mainAddress, this.addressList});

  AddressResponse.fromJson(Map<String, dynamic> json) {
    mainAddress = json['mainAddress'] != null
        ? AddressEntity.fromJson(json['mainAddress'])
        : null;
    if (json['addressList'] != null) {
      addressList = <AddressEntity>[];
      json['addressList'].forEach((v) {
        addressList.add(AddressEntity.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (mainAddress != null) {
      data['mainAddress'] = mainAddress.toJson();
    }
    if (addressList != null) {
      data['addressList'] = addressList.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class AddressEntity {
  int id;
  int memberId;
  String receiverName;
  String receiverPhone;
  String province;
  String city;
  String district;
  String detailAddress;
  int isDefault;

  AddressEntity(
      {this.id,
        this.memberId,
        this.receiverName,
        this.receiverPhone,
        this.province,
        this.city,
        this.district,
        this.detailAddress,
        this.isDefault});

  AddressEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    memberId = json['memberId'];
    receiverName = json['receiverName'];
    receiverPhone = json['receiverPhone'];
    province = json['province'];
    city = json['city'];
    district = json['district'];
    detailAddress = json['detailAddress'];
    isDefault = json['isDefault'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['memberId'] = memberId;
    data['receiverName'] = receiverName;
    data['receiverPhone'] = receiverPhone;
    data['province'] = province;
    data['city'] = city;
    data['district'] = district;
    data['detailAddress'] = detailAddress;
    data['isDefault'] = isDefault;
    return data;
  }
}
