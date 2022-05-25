import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:shopzz_flutter_app/controller/seckill_page_controller.dart';
import 'package:shopzz_flutter_app/entity/response/seckill_item_list_response.dart';
import 'package:shopzz_flutter_app/router/router_manager.dart';
import 'package:shopzz_flutter_app/utils/image_util.dart';

///秒杀商品列表卡片
class SeckillListCard extends StatelessWidget {

  final SeckillItemEntity seckillItemEntity;

  const SeckillListCard({Key key, this.seckillItemEntity}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return InkWell(
        onTap: () {
          //TODO 跳转
        },

        child: SizedBox(
          height: 145,
          child: Card(
            margin: const EdgeInsets.only(left: 4, right: 4, bottom: 4),
            child: ClipRRect(
              borderRadius: BorderRadius.circular(5),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [_itemImage(context), _infoText()],
              ),
            ),
          ),
        )
    );
  }


  _itemImage(BuildContext context) {
    return Stack(
      children: [
        cachedImage(seckillItemEntity.skuImg.toString(), fit: BoxFit.fill),
        Positioned(
            child: Container(
              padding: const EdgeInsets.only(left: 8, right: 8, bottom: 3, top: 5),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  //可以添加一些遮罩样式
                ],
              ),
            ))
      ],
    );
  }


  _infoText() {
    return Expanded(
        child: Container(
          padding: const EdgeInsets.only(top: 4, left: 4, right: 4, bottom: 7),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              //商品名称
              Text(
                seckillItemEntity.spuName + " " + seckillItemEntity.skuName,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(fontSize: 13, color: Colors.black87),
              ),

              //副名称
              Padding(
                padding: const EdgeInsets.only(top: 3),
                child: Text(
                  seckillItemEntity.skuDescs,
                  maxLines: 1,
                  overflow: TextOverflow.ellipsis,
                  style: const TextStyle(fontSize: 10, color: Colors.grey),
                ),
              ),

              //商品价格
              Padding(
                padding: const EdgeInsets.only(top: 5),
                child: Row(
                  children: [
                    const Text("¥",style: TextStyle(fontSize: 12, color: Colors.redAccent, fontWeight: FontWeight.w500)),
                    Text(
                      seckillItemEntity.seckillPrice.toString(),
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                      style: const TextStyle(fontSize: 15, color: Colors.redAccent, fontWeight: FontWeight.w700),
                    ),
                  ],
                ),
              ),



              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [

                  Column(
                    children: [
                      //小标签
                      Padding(
                        padding: const EdgeInsets.only(top: 5),
                        child: Row(
                          children: [
                            const Padding(padding: EdgeInsets.only(right: 2), child: Image(image: AssetImage('assets/images/ziying.png'), width: 25, height: 20)),

                            Padding(padding: const EdgeInsets.only(right: 2), child:littleTag("满赠", Colors.redAccent)),

                            Padding(padding: const EdgeInsets.only(right: 2), child:littleTag("大牌补贴", Colors.redAccent)),

                          ],
                        ),
                      ),

                      //评价数和好评率
                      const Text(
                        "10000+条评论 99%好评",
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                        style: TextStyle(fontSize: 10, color: Colors.grey),
                      ),
                    ],
                  ),


                  Padding(
                    padding: const EdgeInsets.only(right: 20),
                    child: ElevatedButton(
                      onPressed: () {

                        var future = Get.put(SeckillPageController())
                            .seckillOrderSubmit(seckillItemEntity.seckillId, seckillItemEntity.id);

                        future.then((value) {
                          if(value != "0") {
                            Map<String,String> map = HashMap();
                            map["seckillItemId"] = seckillItemEntity.id.toString();
                            map["taskId"] = value;
                            Get.toNamed(Routers.seckillResult, parameters: map);
                          }
                        });
                      },
                      child: const Text("抢购"),
                      style: ElevatedButton.styleFrom(
                        textStyle: const TextStyle(fontSize: 12, fontWeight: FontWeight.bold),
                        minimumSize: const Size(50, 25),
                        primary: Colors.red,
                        onSurface: Colors.blue,
                        shadowColor: Colors.grey,
                        elevation: 5,
                        side: BorderSide(color: Colors.redAccent.shade400, width: 2, style: BorderStyle.solid),
                        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(5)),
                        tapTargetSize: MaterialTapTargetSize.padded,
                      ),
                    ),
                  )

                ],
              )

            ],
          ),
        ));
  }


  Widget littleTag(String text, Color colors) {
    return Container(
        padding: const EdgeInsets.only(left: 2, right: 2, top: 0, bottom: 0),
        decoration: BoxDecoration(border: Border.all(color: colors)),
        child: Text(text, style: TextStyle(
          color: colors,
          fontSize: 9,
          fontWeight: FontWeight.w600,
        )));
  }
}