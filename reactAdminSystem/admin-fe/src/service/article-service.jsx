/*
* @Author: whoiszxl
* @Date:   2018-01-26 13:38:21
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-13 16:30:40
*/
import MUtil        from 'util/mm.jsx'

const _mm   = new MUtil();

class Article{
    // 获取banner列表
    getBannerList(){
        return _mm.request({
            url: _mm.apiUrl + '/manage/article/banner_list',
            type: 'POST'
        });
    }

    // 获取banner详情
    getBannerDetail(bannerId){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/article/banner_detail',
            data    : {
                bannerId : bannerId || 0
            }
        });
    }

    // 保存banner
    saveBanner(banner){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/article/banner_save',
            data    : banner
        });
    }


    // 获取keywords列表
    getKeywordsList(){
        return _mm.request({
            url: _mm.apiUrl + '/manage/article/keywords_list',
            type: 'POST'
        });
    }

    // 保存keywords
    updateKeywordsWords(keywords){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/article/keywords_save',
            data    : keywords
        });
    }

    

    // 检查保存商品的表单数据
    checkBanner(banner){
        let result = {
            status: true,
            msg: '验证通过'
        };
        // 判断banner title为空
        if(typeof banner.title !== 'string' || banner.title.length ===0){
            return {
                status: false,
                msg: 'banner名称不能为空！'
            }
        }
        return result;
    }
}

export default Article;