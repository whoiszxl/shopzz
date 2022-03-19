class CategoryTreeModel {
  List<Categorys> categorys;

  CategoryTreeModel({this.categorys});

  CategoryTreeModel.fromJson(Map<String, dynamic> json) {
    if (json['categorys'] != null) {
      categorys = <Categorys>[];
      json['categorys'].forEach((v) {
        categorys.add(new Categorys.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.categorys != null) {
      data['categorys'] = this.categorys.map((v) => v.toJson()).toList();
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
        children.add(new Children.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['parentId'] = this.parentId;
    data['name'] = this.name;
    data['level'] = this.level;
    data['status'] = this.status;
    data['icon'] = this.icon;
    data['sort'] = this.sort;
    if (this.children != null) {
      data['children'] = this.children.map((v) => v.toJson()).toList();
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
      children = new List<Children>();
      json['children'].forEach((v) {
        children.add(new Children.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['parentId'] = this.parentId;
    data['name'] = this.name;
    data['level'] = this.level;
    data['status'] = this.status;
    data['icon'] = this.icon;
    data['sort'] = this.sort;
    if (this.children != null) {
      data['children'] = this.children.map((v) => v.toJson()).toList();
    }
    return data;
  }
}
