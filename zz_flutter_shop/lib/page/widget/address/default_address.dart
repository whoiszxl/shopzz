import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:zz_flutter_shop/entity/response/member_address_response.dart';
import 'package:zz_flutter_shop/page/widget/product/little_tag.dart';
import 'package:zz_flutter_shop/res/colors_manager.dart';
import 'package:zz_flutter_shop/router/router_manager.dart';

///默认地址组件
defaultAddressTile(Address address, {bool isDefault = true}) {
  return Container(
      decoration: const BoxDecoration(
          color: ColorManager.white,
          borderRadius: BorderRadius.all(Radius.circular(8))
      ),
      child: ListTile(
          title: Row(children: [
            isDefault ? littleTag("默认", Colors.redAccent) : littleTag("地址", Colors.blueAccent),
            Padding(
                padding: const EdgeInsets.only(left: 3),
                child: Text(address.province + address.city + address.district, style: const TextStyle(fontSize: 13, color: ColorManager.grey))) ],),
          subtitle: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(address.detailAddress, style: const TextStyle(fontSize: 20, color: ColorManager.black)),
              Text(address.reciverName + "  " + address.reciverPhone, style: const TextStyle(fontSize: 13)),
            ],
          ),
          trailing: const Icon(Icons.navigate_next),
          onTap: () {
            Get.toNamed(Routers.address);
          }
      )
  );

}