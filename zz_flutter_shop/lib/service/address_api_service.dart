import 'package:get/get.dart';
import 'package:zz_flutter_shop/entity/dto/category_tree_model.dart';
import 'package:zz_flutter_shop/entity/response/home_app_index_response.dart';
import 'package:zz_flutter_shop/entity/response/member_address_response.dart';
import 'package:zz_flutter_shop/http/http_manager.dart';
import 'package:zz_flutter_shop/service/api_urls.dart';

///地址接口服务
class AddressApiService extends GetxService {

  ///获取地址列表
  Future<MemberAddressResponse> addressList() async {
    var result = await HttpManager.getInstance().get(url: ApiUrls.memberAddress);
    return MemberAddressResponse.fromJson(result);
  }

  ///新增地址
  Future<bool> addressAdd() async {
    var result = await HttpManager.getInstance().post(url: ApiUrls.memberAddress);
    return true;
  }

  ///修改地址
  Future<bool> addressUpdate(Address address) async {
    var result = await HttpManager.getInstance().put(url: ApiUrls.memberAddress, data: address);
    return true;
  }

  ///删除地址
  Future<bool> addressDelete(int addressId) async {
    var result = await HttpManager.getInstance().delete(url: ApiUrls.memberAddress + "/" + addressId.toString());
    return true;
  }
}