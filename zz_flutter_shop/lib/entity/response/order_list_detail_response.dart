class OrderListDetailResponse {
  List<Order> records;
  int total;
  int size;
  int current;
  bool optimizeCountSql;
  bool searchCount;
  int countId;
  int maxLimit;
  int pages;

  OrderListDetailResponse(
      {this.records,
        this.total,
        this.size,
        this.current,
        this.optimizeCountSql,
        this.searchCount,
        this.countId,
        this.maxLimit,
        this.pages});

  OrderListDetailResponse.fromJson(Map<String, dynamic> json) {
    if (json['records'] != null) {
      records = <Order>[];
      json['records'].forEach((v) {
        records.add(Order.fromJson(v));
      });
    }
    total = json['total'];
    size = json['size'];
    current = json['current'];
    optimizeCountSql = json['optimizeCountSql'];
    searchCount = json['searchCount'];
    countId = json['countId'];
    maxLimit = json['maxLimit'];
    pages = json['pages'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (records != null) {
      data['records'] = records.map((v) => v.toJson()).toList();
    }
    data['total'] = total;
    data['size'] = size;
    data['current'] = current;
    data['optimizeCountSql'] = optimizeCountSql;
    data['searchCount'] = searchCount;
    data['countId'] = countId;
    data['maxLimit'] = maxLimit;
    data['pages'] = pages;
    return data;
  }
}

class Order {
  List<OrderItemVOList> orderItemVOList;
  int id;
  String orderSn;
  int memberId;
  String username;
  int orderStatus;
  String receiverName;
  String receiverPhone;
  String receiverPostCode;
  String receiverProvince;
  String receiverCity;
  String receiverRegion;
  String receiverDetailAddress;
  int payType;
  double totalAmount;
  double freightAmount;
  double promotionAmount;
  double pointAmount;
  double couponAmount;
  double adminDiscountAmount;
  double payAmount;
  int invoiceType;
  String invoiceHeader;
  String invoiceContent;
  String invoiceReceiverPhone;
  String invoiceReceiverEmail;
  String invoiceReceiverAddress;
  String orderComment;
  String paymentTime;
  String deliveryTime;
  String receiveTime;
  String commentTime;

  Order(
      {this.orderItemVOList,
        this.id,
        this.orderSn,
        this.memberId,
        this.username,
        this.orderStatus,
        this.receiverName,
        this.receiverPhone,
        this.receiverPostCode,
        this.receiverProvince,
        this.receiverCity,
        this.receiverRegion,
        this.receiverDetailAddress,
        this.payType,
        this.totalAmount,
        this.freightAmount,
        this.promotionAmount,
        this.pointAmount,
        this.couponAmount,
        this.adminDiscountAmount,
        this.payAmount,
        this.invoiceType,
        this.invoiceHeader,
        this.invoiceContent,
        this.invoiceReceiverPhone,
        this.invoiceReceiverEmail,
        this.invoiceReceiverAddress,
        this.orderComment,
        this.paymentTime,
        this.deliveryTime,
        this.receiveTime,
        this.commentTime});

  Order.fromJson(Map<String, dynamic> json) {
    if (json['orderItemVOList'] != null) {
      orderItemVOList = <OrderItemVOList>[];
      json['orderItemVOList'].forEach((v) {
        orderItemVOList.add(OrderItemVOList.fromJson(v));
      });
    }
    id = json['id'];
    orderSn = json['orderSn'];
    memberId = json['memberId'];
    username = json['username'];
    orderStatus = json['orderStatus'];
    receiverName = json['receiverName'];
    receiverPhone = json['receiverPhone'];
    receiverPostCode = json['receiverPostCode'];
    receiverProvince = json['receiverProvince'];
    receiverCity = json['receiverCity'];
    receiverRegion = json['receiverRegion'];
    receiverDetailAddress = json['receiverDetailAddress'];
    payType = json['payType'];
    totalAmount = json['totalAmount'];
    freightAmount = json['freightAmount'];
    promotionAmount = json['promotionAmount'];
    pointAmount = json['pointAmount'];
    couponAmount = json['couponAmount'];
    adminDiscountAmount = json['adminDiscountAmount'];
    payAmount = json['payAmount'];
    invoiceType = json['invoiceType'];
    invoiceHeader = json['invoiceHeader'];
    invoiceContent = json['invoiceContent'];
    invoiceReceiverPhone = json['invoiceReceiverPhone'];
    invoiceReceiverEmail = json['invoiceReceiverEmail'];
    invoiceReceiverAddress = json['invoiceReceiverAddress'];
    orderComment = json['orderComment'];
    paymentTime = json['paymentTime'];
    deliveryTime = json['deliveryTime'];
    receiveTime = json['receiveTime'];
    commentTime = json['commentTime'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (orderItemVOList != null) {
      data['orderItemVOList'] =
          orderItemVOList.map((v) => v.toJson()).toList();
    }
    data['id'] = id;
    data['orderSn'] = orderSn;
    data['memberId'] = memberId;
    data['username'] = username;
    data['orderStatus'] = orderStatus;
    data['receiverName'] = receiverName;
    data['receiverPhone'] = receiverPhone;
    data['receiverPostCode'] = receiverPostCode;
    data['receiverProvince'] = receiverProvince;
    data['receiverCity'] = receiverCity;
    data['receiverRegion'] = receiverRegion;
    data['receiverDetailAddress'] = receiverDetailAddress;
    data['payType'] = payType;
    data['totalAmount'] = totalAmount;
    data['freightAmount'] = freightAmount;
    data['promotionAmount'] = promotionAmount;
    data['pointAmount'] = pointAmount;
    data['couponAmount'] = couponAmount;
    data['adminDiscountAmount'] = adminDiscountAmount;
    data['payAmount'] = payAmount;
    data['invoiceType'] = invoiceType;
    data['invoiceHeader'] = invoiceHeader;
    data['invoiceContent'] = invoiceContent;
    data['invoiceReceiverPhone'] = invoiceReceiverPhone;
    data['invoiceReceiverEmail'] = invoiceReceiverEmail;
    data['invoiceReceiverAddress'] = invoiceReceiverAddress;
    data['orderComment'] = orderComment;
    data['paymentTime'] = paymentTime;
    data['deliveryTime'] = deliveryTime;
    data['receiveTime'] = receiveTime;
    data['commentTime'] = commentTime;
    return data;
  }
}

class OrderItemVOList {
  int id;
  int orderId;
  String orderSn;
  int productId;
  int categoryId;
  int skuId;
  String skuName;
  String skuPic;
  double skuPrice;
  int quantity;
  String skuAttrs;
  int promotionActivityId;
  double promotionAmount;
  double couponAmount;
  double pointAmount;
  double realAmount;

  OrderItemVOList(
      {this.id,
        this.orderId,
        this.orderSn,
        this.productId,
        this.categoryId,
        this.skuId,
        this.skuName,
        this.skuPic,
        this.skuPrice,
        this.quantity,
        this.skuAttrs,
        this.promotionActivityId,
        this.promotionAmount,
        this.couponAmount,
        this.pointAmount,
        this.realAmount});

  OrderItemVOList.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    orderId = json['orderId'];
    orderSn = json['orderSn'];
    productId = json['productId'];
    categoryId = json['categoryId'];
    skuId = json['skuId'];
    skuName = json['skuName'];
    skuPic = json['skuPic'];
    skuPrice = json['skuPrice'];
    quantity = json['quantity'];
    skuAttrs = json['skuAttrs'];
    promotionActivityId = json['promotionActivityId'];
    promotionAmount = json['promotionAmount'];
    couponAmount = json['couponAmount'];
    pointAmount = json['pointAmount'];
    realAmount = json['realAmount'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['orderId'] = orderId;
    data['orderSn'] = orderSn;
    data['productId'] = productId;
    data['categoryId'] = categoryId;
    data['skuId'] = skuId;
    data['skuName'] = skuName;
    data['skuPic'] = skuPic;
    data['skuPrice'] = skuPrice;
    data['quantity'] = quantity;
    data['skuAttrs'] = skuAttrs;
    data['promotionActivityId'] = promotionActivityId;
    data['promotionAmount'] = promotionAmount;
    data['couponAmount'] = couponAmount;
    data['pointAmount'] = pointAmount;
    data['realAmount'] = realAmount;
    return data;
  }
}
