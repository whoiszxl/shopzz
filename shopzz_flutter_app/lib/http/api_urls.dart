///API接口统一管理类
class ApiUrls {

  static const String baseUrl = 'http://192.168.252.1';
  //static const String baseUrl = 'http://192.168.3.13';

  ///首页获取banner数据
  static const String homeBanner = "/promotion/api/banner/app/index";

  ///首页获取推荐商品列表
  static const String homeRecommendList = "/promotion/api/recommend/product/list";

  ///首页获取banner数据
  static const String columnDetailById = "/promotion/api/column/detail";

  ///获取分类树
  static const String categoryTree = "/product/api/category/list/tree";


  static const String spuDetail = "/product/api/spu/detail/";

  static const String memberLogin = "/member/api/member/login";
  static const String memberPasswordRegister = "/member/api/member/phone/register";
  static const String memberInfo = "/member/api/member/detail";
  static const String memberAddress = "/member/api/address";

  static const String couponAllUnlimited = "/promotion/api/coupon/all/unlimited";
  static const String receiveCoupon = "/promotion/api/coupon/receive/";


  ///购物车
  static const String cartAdd = "/order/api/cart/add";
  static const String cartCheck = "/order/api/cart/check";
  static const String cartClean = "/order/api/cart/clean";
  static const String cartDelete = "/order/api/cart/delete";
  static const String cartDetail = "/order/api/cart/detail";
  static const String cartUpdateQuantity = "/order/api/cart/update/quantity";


  ///秒杀
  static const String seckillList = "/promotion/api/seckill/list";
  static const String seckillItemList = "/promotion/api/seckill/item/list/";
  static const String seckillOrderSubmit = "/promotion/api/seckill/order/submit";
  static const String seckillOrderResult = "/promotion/api/seckill/order/result";

}