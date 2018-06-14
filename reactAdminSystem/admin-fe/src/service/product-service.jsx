/*
* @Author: whoiszxl
* @Date:   2018-01-31 13:19:15
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-11 23:30:57
*/
import MUtil        from 'util/mm.jsx'

const _mm   = new MUtil();

class Product{
    // 获取商品列表
    getProductList(listParam){
        let url     = '',
            data    = {};
        if(listParam.listType === 'list'){
            url                         = _mm.apiUrl + '/manage/product/list';
            data.pageNum                = listParam.pageNum;
        }else if(listParam.listType === 'search'){
            url = _mm.apiUrl + '/manage/product/search';
            data.pageNum                = listParam.pageNum;
            data[listParam.searchType]  = listParam.keyword;
        }
        return _mm.request({
            type    : 'post',
            url     : url,
            data    : data
        });
    }
    // 获取商品详情
    getProduct(productId){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/product/detail',
            data    : {
                productId : productId || 0
            }
        });
    }
    // 变更商品销售状态
    setProductStatus(productInfo){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/product/set_sale_status',
            data    : productInfo
        });
    }
    // 检查保存商品的表单数据
    checkProduct(product){
        let result = {
            status: true,
            msg: '验证通过'
        };
        // 判断用户名为空
        if(typeof product.name !== 'string' || product.name.length ===0){
            return {
                status: false,
                msg: '商品名称不能为空！'
            }
        }
        // 判断描述不能为空
        if(typeof product.subtitle !== 'string' || product.subtitle.length ===0){
            return {
                status: false,
                msg: '商品描述不能为空！'
            }
        }
        // 验证品类ID
        if(typeof product.categoryId !== 'number' || !(product.categoryId > 0)){
            return {
                status: false,
                msg: '请选择商品品类！'
            }
        }
        // 判断商品价格为数字，且大于0
        if(typeof product.price !== 'number' || !(product.price >= 0)){
            return {
                status: false,
                msg: '请输入正确的商品价格！'
            }
        }
        // 判断库存为数字，且大于或等于0
        if(typeof product.stock !== 'number' || !(product.stock >= 0)){
            return {
                status: false,
                msg: '请输入正确的库存数量！'
            }
        }
        
        return result;
    }


    // 检查分类表单数据
    checkCategory(category){
        let result = {
            status: true,
            msg: '验证通过'
        };
        // 判断用户名为空
        if(typeof category.name !== 'string' || category.name.length ===0){
            return {
                status: false,
                msg: '分类名称不能为空！'
            }
        }
        
        return result;
    }
    
    // 保存商品
    saveProduct(product){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/product/save',
            data    : product
        });
    }
    /*
    *  品类相关
    */
    // 根据父品类id获取品类列表
    getCategoryList(parentCategoryId){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/category/get_category',
            data    : {
                categoryId : parentCategoryId || 0
            }
        });
    }

    // 保存品类
    saveManageCategory(category){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/category/save',
            data    : category
        });
    }

    // 获取品类详情
    getCategory(categoryId){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/category/detail',
            data    : {
                categoryId : categoryId || 0
            }
        });
    }

    // 新增品类
    saveCategory(category){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/category/add_category',
            data    : category
        });
    }
    // 修改品类名称
    updateCategoryName(category){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/category/set_category_name',
            data    : category
        });
    }
}

export default Product;