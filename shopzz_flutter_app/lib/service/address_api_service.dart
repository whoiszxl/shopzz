import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/response/address_response.dart';
import 'package:shopzz_flutter_app/http/api_urls.dart';
import 'package:shopzz_flutter_app/http/http_manager.dart';

///地址接口服务
class AddressApiService extends GetxService {

  ///获取地址列表
  Future<AddressResponse> addressList() async {
    var result = await HttpManager.getInstance().get(url: ApiUrls.memberAddress);
    return AddressResponse.fromJson(result);
  }

  ///新增地址
  Future<bool> addressAdd() async {
    var result = await HttpManager.getInstance().post(url: ApiUrls.memberAddress);
    return true;
  }

  ///修改地址
  Future<bool> addressUpdate(AddressEntity address) async {
    var result = await HttpManager.getInstance().put(url: ApiUrls.memberAddress, data: address);
    return true;
  }

  ///删除地址
  Future<bool> addressDelete(int addressId) async {
    var result = await HttpManager.getInstance().delete(url: ApiUrls.memberAddress + "/" + addressId.toString());
    return true;
  }
}