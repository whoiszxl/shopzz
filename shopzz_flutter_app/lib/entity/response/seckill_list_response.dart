class SeckillListResponse {

  List<SeckillEntity> seckillList;

  SeckillListResponse({this.seckillList});

  SeckillListResponse.fromJson(Map<String, dynamic> json) {
    if (json['seckillList'] != null) {
      seckillList = <SeckillEntity>[];
      json['seckillList'].forEach((v) {
        seckillList.add(SeckillEntity.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (seckillList != null) {
      data['seckillList'] = seckillList.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class SeckillEntity {
  int id;
  String name;
  String descs;
  String startTime;
  String endTime;
  String img;
  int status;

  SeckillEntity(
      {this.id,
        this.name,
        this.descs,
        this.startTime,
        this.endTime,
        this.img,
        this.status});

  SeckillEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    descs = json['descs'];
    startTime = json['startTime'];
    endTime = json['endTime'];
    img = json['img'];
    status = json['status'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['descs'] = descs;
    data['startTime'] = startTime;
    data['endTime'] = endTime;
    data['img'] = img;
    data['status'] = status;
    return data;
  }
}
