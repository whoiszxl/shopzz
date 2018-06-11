/*
* @Author: whoiszxl
* @Date:   2018-02-05 13:17:10
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-11 23:31:07
*/
import MUtil    from 'util/mm.jsx'

const _mm       = new MUtil();

class Order{
    // 获取订单列表
    getOrderList(listParam){
        let url     = '',
            data    = {};
        if(listParam.listType === 'list'){
            url             = _mm.apiUrl + '/manage/order/list';
            data.pageNum    = listParam.pageNum;
        }else if(listParam.listType === 'search'){
            url = _mm.apiUrl + '/manage/order/search';
            data.pageNum    = listParam.pageNum;
            data.orderNo    = listParam.orderNo;
        }
        return _mm.request({
            type    : 'post',
            url     : url,
            data    : data
        });
    }
    // 获取订单详情
    getOrderDetail(orderNumber){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/order/detail',
            data    : {
                orderNo : orderNumber
            }
        });
    }
    sendGoods(orderNumber){
        return _mm.request({
            type    : 'post',
            url     : _mm.apiUrl + '/manage/order/send_goods',
            data    : {
                orderNo : orderNumber
            }
        });
    }
}

export default Order;