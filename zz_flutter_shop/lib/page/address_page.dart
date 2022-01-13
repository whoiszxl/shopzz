import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:zz_flutter_shop/controller/member_address_controller.dart';
import 'package:zz_flutter_shop/entity/response/member_address_response.dart';
import 'package:zz_flutter_shop/page/widget/address/default_address.dart';
import 'package:zz_flutter_shop/page/widget/cart/cart_app_bar.dart';
import 'package:zz_flutter_shop/page/widget/settings/normal_appbar.dart';

///地址页面
class AddressPage extends StatefulWidget {
  const AddressPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _AddressPageState();
  }
}

class _AddressPageState extends State<AddressPage>{

  MemberAddressController memberAddressController = Get.find<MemberAddressController>();

  final RefreshController _refreshController = RefreshController(initialRefresh: false);

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    return Obx(() {
      return Scaffold(
        //app头部栏
        appBar: AppBar(
          flexibleSpace: const Image(
            image: AssetImage('images/cart_bg.jpg'),
            fit: BoxFit.fitWidth,
          ),
          backgroundColor: Colors.transparent,

          elevation: 0, //阴影的高度,默认在导航栏下有4高度阴影
          title: const Center(child: Text("选择地址")),
        ),
        body: Padding(
          padding: const EdgeInsets.only(top: 10),
          child: SmartRefresher(
              enablePullDown: true, //开启下拉
              enablePullUp: false, //关闭上拉
              header: const ClassicHeader(),
              footer: const ClassicFooter(),
              controller: _refreshController,
              onRefresh: _onRefresh, //下拉时调用
              child: _addressListView(memberAddressController.memberAddressResponse.value.mainAddress, memberAddressController.memberAddressResponse.value.addressList) //返回列表组件
          ),
        )



      );
    });
  }

  void _onRefresh() {
    memberAddressController.getMemberAddressList();
    _refreshController.refreshCompleted();
  }

  _addressListView(Address mainAddress, List<Address> addressList) {
    addressList.insert(0, mainAddress);
    return ListView.builder(
        itemCount: addressList?.length,
        itemBuilder: (BuildContext context, int index) {
          if(index == 0) {
            return defaultAddressTile(addressList[index]);
          }
          return defaultAddressTile(addressList[index], isDefault: false);
        }
    );
  }
}