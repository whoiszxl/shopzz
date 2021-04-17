import 'package:flutter_mall/http/core/zero_net.dart';
import 'package:flutter_mall/http/request/category/category_list_request.dart';
import 'package:flutter_mall/model/category_tree_model.dart';
import 'package:flutter_mall/util/log_util.dart';


///分类数据访问层
class CategoryDao {

  ///获取分类树数据
  static Future getCategoryTree() async {
    CategoryTreeRequest categoryTreeRequest = new CategoryTreeRequest();
    var result = await ZeroNet.getInstance().request(categoryTreeRequest);
    Log.debug("banner list" + result['data'].toString());
    return CategoryTreeModel.fromJson(result['data']);
  }
}