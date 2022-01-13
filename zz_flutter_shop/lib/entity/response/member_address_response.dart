class MemberAddressResponse {
  Address mainAddress;
  List<Address> addressList;

  MemberAddressResponse({this.mainAddress, this.addressList});

  MemberAddressResponse.fromJson(Map<String, dynamic> json) {
    mainAddress = json['mainAddress'] != null
        ? Address.fromJson(json['mainAddress'])
        : null;
    if (json['addressList'] != null) {
      addressList = <Address>[];
      json['addressList'].forEach((v) {
        addressList.add(Address.fromJson(v));
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

class Address {
  int id;
  int memberId;
  String reciverName;
  String reciverPhone;
  String province;
  String city;
  String district;
  String detailAddress;
  int isDefault;

  Address(
      {this.id,
        this.memberId,
        this.reciverName,
        this.reciverPhone,
        this.province,
        this.city,
        this.district,
        this.detailAddress,
        this.isDefault});

  Address.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    memberId = json['memberId'];
    reciverName = json['reciverName'];
    reciverPhone = json['reciverPhone'];
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
    data['reciverName'] = reciverName;
    data['reciverPhone'] = reciverPhone;
    data['province'] = province;
    data['city'] = city;
    data['district'] = district;
    data['detailAddress'] = detailAddress;
    data['isDefault'] = isDefault;
    return data;
  }
}
