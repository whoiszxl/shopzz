class CartDetailResponse {

  List<CartItemEntity> cartItemVOList;
  int skuCount;
  num totalAmount;

  CartDetailResponse({this.cartItemVOList, this.skuCount, this.totalAmount});

  CartDetailResponse.fromJson(Map<String, dynamic> json) {
    if (json['cartItemVOList'] != null) {
      cartItemVOList = <CartItemEntity>[];
      json['cartItemVOList'].forEach((v) {
        cartItemVOList.add(CartItemEntity.fromJson(v));
      });
    }
    skuCount = json['skuCount'];
    totalAmount = json['totalAmount'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (cartItemVOList != null) {
      data['cartItemVOList'] =
          cartItemVOList.map((v) => v.toJson()).toList();
    }
    data['skuCount'] = skuCount;
    data['totalAmount'] = totalAmount;
    return data;
  }
}

class CartItemEntity {
  int id;
  int memberId;
  int productId;
  int skuId;
  String skuName;
  String skuPic;
  int quantity;
  num price;
  String saleAttr;
  int checked;
  int status;

  CartItemEntity(
      {this.id,
        this.memberId,
        this.productId,
        this.skuId,
        this.skuName,
        this.skuPic,
        this.quantity,
        this.price,
        this.saleAttr,
        this.checked,
        this.status});

  CartItemEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    memberId = json['memberId'];
    productId = json['productId'];
    skuId = json['skuId'];
    skuName = json['skuName'];
    skuPic = json['skuPic'];
    quantity = json['quantity'];
    price = json['price'];
    saleAttr = json['saleAttr'];
    checked = json['checked'];
    status = json['status'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['memberId'] = memberId;
    data['productId'] = productId;
    data['skuId'] = skuId;
    data['skuName'] = skuName;
    data['skuPic'] = skuPic;
    data['quantity'] = quantity;
    data['price'] = price;
    data['saleAttr'] = saleAttr;
    data['checked'] = checked;
    data['status'] = status;
    return data;
  }
}
