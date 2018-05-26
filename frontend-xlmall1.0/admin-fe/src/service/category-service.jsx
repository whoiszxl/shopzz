import MUtil from 'util/mm.jsx';
const _mm = new MUtil();

class Category{
    //获取分类列表
    getCategoryList(parentCategoryId){
        return _mm.request({
            type: 'get',
            url: '/manage/category/get_category',
            data: {
                categoryId : parentCategoryId || 0
            }
        })
    }
    //修改品类名称
    updateCateName(cateInfo){
        return _mm.request({
            type: 'post',
            url: '/manage/category/set_category_name',
            data: cateInfo
        })
    }
    //增加分类
    addCategory(cateInfo){
        return _mm.request({
            type: 'post',
            url: '/manage/category/add_category',
            data: cateInfo
        })
    }
}
export default Category;