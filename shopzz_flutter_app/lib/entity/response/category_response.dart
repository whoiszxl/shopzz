class CategoryResponse {
  List<CategoryEntity> categorys;

  CategoryResponse({this.categorys});

  CategoryResponse.fromJson(Map<String, dynamic> json) {
    if (json['categorys'] != null) {
      categorys = <CategoryEntity>[];
      json['categorys'].forEach((v) {
        categorys.add(CategoryEntity.fromJson(v));
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

class CategoryEntity {
  int id;
  String name;
  int parentId;
  int level;
  int status;
  int sort;
  String icon;
  List<CategoryEntity> children;

  CategoryEntity(
      {this.id,
        this.name,
        this.parentId,
        this.level,
        this.status,
        this.sort,
        this.icon,
        this.children});

  CategoryEntity.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    parentId = json['parentId'];
    level = json['level'];
    status = json['status'];
    sort = json['sort'];
    icon = json['icon'];
    if (json['children'] != null) {
      children = <CategoryEntity>[];
      json['children'].forEach((v) {
        children.add(CategoryEntity.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['parentId'] = parentId;
    data['level'] = level;
    data['status'] = status;
    data['sort'] = sort;
    data['icon'] = icon;
    if (children != null) {
      data['children'] = children.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Children {
  int id;
  String name;
  int parentId;
  int level;
  int status;
  int sort;
  String icon;
  List<Children> children;

  Children(
      {this.id,
        this.name,
        this.parentId,
        this.level,
        this.status,
        this.sort,
        this.icon,
        this.children});

  Children.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    parentId = json['parentId'];
    level = json['level'];
    status = json['status'];
    sort = json['sort'];
    icon = json['icon'];
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
    data['name'] = name;
    data['parentId'] = parentId;
    data['level'] = level;
    data['status'] = status;
    data['sort'] = sort;
    data['icon'] = icon;
    if (children != null) {
      data['children'] = children.map((v) => v.toJson()).toList();
    }
    return data;
  }
}