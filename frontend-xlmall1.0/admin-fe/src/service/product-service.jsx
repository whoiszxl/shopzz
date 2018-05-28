import MUtil from 'util/mm.jsx';
const _mm = new MUtil();

class Product{
   
    //获取商品列表
    getProductList(listParam){
        let url = '',
            data = {};
        if(listParam.listType           === 'list'){
            url                         = '/manage/product/list';
            data.pageNum                = listParam.pageNum;
        }else if(listParam.listType     === 'search'){
            url = '/manage/product/search';
            data.pageNum                = listParam.pageNum;
            data[listParam.searchType]  = listParam.keyword;
        }
        return _mm.request({
            type: 'get',
            url: url,
            data: data
        })
    }
    //商品上下架
    setSaleStatus(productInfo){
        return _mm.request({
            type: 'post',
            url: '/manage/product/set_sale_status',
            data: productInfo
        })
    }
    //商品搜索
    // searchProduct(searchInfo){
    //     return _mm.request({
    //         type: 'post',
    //         url: '/manage/product/search',
    //         data: searchInfo
    //     })
    // }
    //表单验证
    checkProduct(product){
        let productCheckRes = {
            status: true,
            msg: '验证通过'
        };
        if(typeof product.name !== 'string' || product.name.length === 0){
            return {
                status: false,
                msg: '商品名称不能为空！'
            }
        }
        if(typeof product.subtitle !== 'string' || product.subtitle.length === 0){
            return {
                status: false,
                msg: '商品描述不能为空！'
            }
        }
        if(typeof product.price !== 'number' || !(product.price >= 0)){
            return {
                status: false,
                msg: '请输入正确的商品价格！'
            }
        }
        if(typeof product.stock !== 'number' || !(product.stock >= 0)){
            return {
                status: false,
                msg: '请输入正确的库存！'
            }
        }
        //品类Id
        if(typeof product.categoryId !== 'number' || !(product.categoryId > 0)){
            return {
                status: false,
                msg: '请选中商品品类！'
            }
        }
        return productCheckRes;
    }
    //提交产品
    saveProduct(productInfo){
        return _mm.request({
            type: 'post',
            url: '/manage/product/save',
            data: productInfo
        })
    }
    //产品详情
    getProduct(proId){
        return _mm.request({
            type: 'get',
            url: '/manage/product/detail',
            data: {
                productId: proId || 0
            }
        })
    }
}
export default Product;