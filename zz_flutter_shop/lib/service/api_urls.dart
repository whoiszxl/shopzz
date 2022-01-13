class ApiUrls {

  static const String baseUrl = 'http://192.168.4.153';

  ///首页获取banner和nav接口
  static const String homeRecommendAppIndex = "/product/recommend/app/index";

  ///首页获取热门和精选商品的接口
  static const String homeRecommendProductList = "/product/recommend/list";
  static const String bannerByType = "/product/recommend/banner/";

  static const String categoryTree = "/product/category/list/tree";


  static const String memberLogin = "/member/member/login";
  static const String memberPasswordRegister = "/member/member/password/register";
  static const String memberRegister = "/member/member/register";
  static const String memberInfo = "/member/member";

  static const String productSearch = "/product/product/search";
  static const String productDetail = "/product/product/detail/";


  ///购物车
  static const String cartAdd = "/order/cart/add";
  static const String cartCheck = "/order/cart/check";
  static const String cartClear = "/order/cart/clear";
  static const String cartDelete = "/order/cart/delete";
  static const String cartDetail = "/order/cart/detail";
  static const String cartUpdateQuantity = "/order/cart/update/quantity";


  ///地址管理 CRUD
  static const String memberAddress = "/member/member-address";


  ///提交订单
  static const String orderSubmit = "/order/order/submit";

  static const String orderPay = "/order/order/pay";

}