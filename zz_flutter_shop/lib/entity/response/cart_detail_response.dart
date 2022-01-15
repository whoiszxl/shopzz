class CartDetailResponse {

  List<CartItemVO> cartItemVOList;
  int skuCount;
  double totalAmount;

  CartDetailResponse({this.cartItemVOList, this.skuCount, this.totalAmount});

  CartDetailResponse.fromJson(Map<String, dynamic> json) {
    if (json['cartItemVOList'] != null) {
      cartItemVOList = <CartItemVO>[];
      json['cartItemVOList'].forEach((v) {
        cartItemVOList.add(CartItemVO.fromJson(v));
      });
    }
    skuCount = json['skuCount'];
    totalAmount = json['totalAmount'] != null ? double.parse(json['totalAmount'].toString()) : 0.0;
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

class CartItemVO {
  Null id;
  int productId;
  int skuId;
  String skuName;
  String skuPic;
  int quantity;
  double price;
  int checked;

  CartItemVO(
      {this.id,
        this.productId,
        this.skuId,
        this.skuName,
        this.skuPic,
        this.quantity,
        this.price,
        this.checked});

  CartItemVO.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    productId = json['productId'];
    skuId = json['skuId'];
    skuName = json['skuName'];
    skuPic = json['skuPic'];
    quantity = json['quantity'];
    price = json['price'];
    checked = json['checked'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['productId'] = productId;
    data['skuId'] = skuId;
    data['skuName'] = skuName;
    data['skuPic'] = skuPic;
    data['quantity'] = quantity;
    data['price'] = price;
    data['checked'] = checked;
    return data;
  }
}
