/*
* @Author: whoiszxl
* @Date:   2018-01-26 13:38:21
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-11 23:30:32
*/
import MUtil        from 'util/mm.jsx'

const _mm   = new MUtil();

class Statistic{
    // 首页数据统计
    getHomeCount(){
        return _mm.request({
            url: _mm.apiUrl + '/manage/statistic/base_count',
            type: 'POST'
        });
    }
}

export default Statistic;