import 'package:flutter/material.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:get/get.dart';
import 'package:shopzz_flutter_app/controller/member_address_controller.dart';
import 'package:shopzz_flutter_app/entity/response/address_response.dart';
import 'package:shopzz_flutter_app/res/colors_manager.dart';
import 'package:shopzz_flutter_app/router/router_manager.dart';
import 'package:shopzz_flutter_app/utils/loading_util.dart';

///地址页面
class AddressPage extends StatefulWidget {
  const AddressPage({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _AddressPageState();
  }
}

class _AddressPageState extends State<AddressPage>{

  MemberAddressController memberAddressController = Get.put(MemberAddressController());

  final RefreshController _refreshController = RefreshController(initialRefresh: false);

  @override
  void initState() {
    memberAddressController.getMemberAddressList();
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    return Obx(() {
      if(memberAddressController.addressResponse.value == null || memberAddressController.addressResponse.value.mainAddress == null) {
        return normalLoading();
      }

      return Scaffold(
        //app头部栏
          appBar: AppBar(
            flexibleSpace: const Image(
              image: AssetImage('assets/images/member_bg.jpg'),
              fit: BoxFit.fitWidth,
            ),
            backgroundColor: Colors.transparent,
            actions: const [
                Padding(
                  padding: EdgeInsets.only(right: 10),
                  child: Icon(Icons.add),
                )
            ],
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
                child: _addressListView(memberAddressController.addressResponse.value.mainAddress, memberAddressController.addressResponse.value.addressList) //返回列表组件
            ),
          )



      );
    });
  }

  void _onRefresh() {
    memberAddressController.getMemberAddressList();
    _refreshController.refreshCompleted();
  }

  _addressListView(AddressEntity mainAddress, List<AddressEntity> addressList) {
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

  ///默认地址组件
  defaultAddressTile(AddressEntity address, {bool isDefault = true}) {
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
                Text(address.receiverName + "  " + address.receiverPhone, style: const TextStyle(fontSize: 13)),
              ],
            ),
            trailing: const Icon(Icons.navigate_next),
            onTap: () {
              Get.toNamed(Routers.address);
            }
        )
    );

  }

  Widget littleTag(String text, Color colors, {double size = 9}) {
    return Container(
        margin: const EdgeInsets.only(left: 2),
        padding: const EdgeInsets.only(left: 2, right: 2, top: 0, bottom: 0),
        decoration: BoxDecoration(border: Border.all(color: colors)),
        child: Text(text, style: TextStyle(
          color: colors,
          fontSize: size,
          fontWeight: FontWeight.w600,
        )));
  }
}