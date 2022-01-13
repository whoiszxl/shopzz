class OrderPayDcResponse {
  int currencyId;
  String address;
  String qrCodeData;

  OrderPayDcResponse({this.currencyId, this.address, this.qrCodeData});

  OrderPayDcResponse.fromJson(Map<String, dynamic> json) {
    currencyId = json['currencyId'];
    address = json['address'];
    qrCodeData = json['qrCodeData'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['currencyId'] = currencyId;
    data['address'] = address;
    data['qrCodeData'] = qrCodeData;
    return data;
  }
}
