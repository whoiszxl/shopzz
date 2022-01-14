class OrderPayDcResponse {
  int id;
  int orderId;
  String orderSn;
  int memberId;
  int currencyId;
  String currencyName;
  String txHash;
  double totalAmount;
  String fromAddress;
  String toAddress;
  String qrcodeData;
  String upchainAt;
  String upchainSuccessAt;
  int upchainStatus;
  int currentConfirm;
  int height;
  int version;
  int isDeleted;
  String createdBy;
  String updatedBy;
  String createdAt;
  String updatedAt;

  OrderPayDcResponse(
      {this.id,
        this.orderId,
        this.orderSn,
        this.memberId,
        this.currencyId,
        this.currencyName,
        this.txHash,
        this.totalAmount,
        this.fromAddress,
        this.toAddress,
        this.qrcodeData,
        this.upchainAt,
        this.upchainSuccessAt,
        this.upchainStatus,
        this.currentConfirm,
        this.height,
        this.version,
        this.isDeleted,
        this.createdBy,
        this.updatedBy,
        this.createdAt,
        this.updatedAt});

  OrderPayDcResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    orderId = json['orderId'];
    orderSn = json['orderSn'];
    memberId = json['memberId'];
    currencyId = json['currencyId'];
    currencyName = json['currencyName'];
    txHash = json['txHash'];
    totalAmount = json['totalAmount'];
    fromAddress = json['fromAddress'];
    toAddress = json['toAddress'];
    qrcodeData = json['qrcodeData'];
    upchainAt = json['upchainAt'];
    upchainSuccessAt = json['upchainSuccessAt'];
    upchainStatus = json['upchainStatus'];
    currentConfirm = json['currentConfirm'];
    height = json['height'];
    version = json['version'];
    isDeleted = json['isDeleted'];
    createdBy = json['createdBy'];
    updatedBy = json['updatedBy'];
    createdAt = json['createdAt'];
    updatedAt = json['updatedAt'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['orderId'] = orderId;
    data['orderSn'] = orderSn;
    data['memberId'] = memberId;
    data['currencyId'] = currencyId;
    data['currencyName'] = currencyName;
    data['txHash'] = txHash;
    data['totalAmount'] = totalAmount;
    data['fromAddress'] = fromAddress;
    data['toAddress'] = toAddress;
    data['qrcodeData'] = qrcodeData;
    data['upchainAt'] = upchainAt;
    data['upchainSuccessAt'] = upchainSuccessAt;
    data['upchainStatus'] = upchainStatus;
    data['currentConfirm'] = currentConfirm;
    data['height'] = height;
    data['version'] = version;
    data['isDeleted'] = isDeleted;
    data['createdBy'] = createdBy;
    data['updatedBy'] = updatedBy;
    data['createdAt'] = createdAt;
    data['updatedAt'] = updatedAt;
    return data;
  }
}
