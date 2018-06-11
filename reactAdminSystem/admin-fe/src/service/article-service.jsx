/*
* @Author: whoiszxl
* @Date:   2018-01-26 13:38:21
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-10 02:01:07
*/
import MUtil        from 'util/mm.jsx'

const _mm   = new MUtil();

class Article{
    // 获取banner列表
    getBannerList(){
        return _mm.request({
            url: '/manage/article/banner_list',
            type: 'POST'
        });
    }

    // 获取banner详情
    getProduct(bannerId){
        return _mm.request({
            type    : 'post',
            url     : '/manage/article/banner_detail',
            data    : {
                bannerId : bannerId || 0
            }
        });
    }
}

export default Article;