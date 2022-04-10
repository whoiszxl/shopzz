import 'package:get/get.dart';
import 'package:shopzz_flutter_app/entity/response/address_response.dart';
import 'package:shopzz_flutter_app/service/address_api_service.dart';

class MemberAddressController extends GetxController {

  final addressResponse = AddressResponse().obs;

  ///获取用户地址列表
  Future<bool> getMemberAddressList() async {
    var result = await Get.find<AddressApiService>().addressList();
    addressResponse.value = result;
    return true;
  }
}