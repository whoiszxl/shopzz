
import 'dart:async';

import 'package:get/get.dart';
import 'package:zz_flutter_shop/entity/response/member_address_response.dart';
import 'package:zz_flutter_shop/service/address_api_service.dart';

class MemberAddressController extends GetxController {

  final memberAddressResponse = MemberAddressResponse().obs;

  ///获取用户地址列表
  Future<bool> getMemberAddressList() async {
    var result = await Get.find<AddressApiService>().addressList();
    memberAddressResponse.value = result;
    return true;
  }
}