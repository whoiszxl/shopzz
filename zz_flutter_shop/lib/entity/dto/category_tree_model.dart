class CategoryTreeModel {
  List<Categorys> categorys;

  CategoryTreeModel({this.categorys});

  CategoryTreeModel.fromJson(Map<String, dynamic> json) {
    if (json['categorys'] != null) {
      categorys = <Categorys>[];
      json['categorys'].forEach((v) {
        categorys.add(Categorys.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (categorys != null) {
      data['categorys'] = categorys.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Categorys {
  int id;
  int parentId;
  String name;
  int level;
  int status;
  String icon;
  int sort;
  List<Children> children;

  Categorys(
      {this.id,
        this.parentId,
        this.name,
        this.level,
        this.status,
        this.icon,
        this.sort,
        this.children});

  Categorys.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    parentId = json['parentId'];
    name = json['name'];
    level = json['level'];
    status = json['status'];
    icon = json['icon'];
    sort = json['sort'];
    if (json['children'] != null) {
      children = <Children>[];
      json['children'].forEach((v) {
        children.add(Children.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['parentId'] = parentId;
    data['name'] = name;
    data['level'] = level;
    data['status'] = status;
    data['icon'] = icon;
    data['sort'] = sort;
    if (children != null) {
      data['children'] = children.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Children {
  int id;
  int parentId;
  String name;
  int level;
  int status;
  String icon;
  int sort;
  List<Children> children;

  Children(
      {this.id,
        this.parentId,
        this.name,
        this.level,
        this.status,
        this.icon,
        this.sort,
        this.children});

  Children.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    parentId = json['parentId'];
    name = json['name'];
    level = json['level'];
    status = json['status'];
    icon = json['icon'];
    sort = json['sort'];
    if (json['children'] != null) {
      children = <Children>[];
      json['children'].forEach((v) {
        children.add(Children.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['parentId'] = parentId;
    data['name'] = name;
    data['level'] = level;
    data['status'] = status;
    data['icon'] = icon;
    data['sort'] = sort;
    if (children != null) {
      data['children'] = children.map((v) => v.toJson()).toList();
    }
    return data;
  }
}