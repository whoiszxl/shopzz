import MUtil from 'util/mm.jsx';
const _mm = new MUtil();

class Order{
    //获取订单列表
    getOrderList(listParam){
        let url = '',
            data = {};
        if(listParam.listType           === 'list'){
            url                         = '/manage/order/list';
            data.pageNum                = listParam.pageNum;
        }else if(listParam.listType     === 'search'){
            url = '/manage/order/search';
            data.orderNo                = listParam.orderNo;
        }
        return _mm.request({
            type: 'get',
            url: url,
            data: data
        })
    }
    //获取订单详情
    getOrderDetail(orderNo){
        return _mm.request({
            type: 'get',
            url: '/manage/order/detail',
            data: orderNo
        })
    }
    sendGoods(orderNo){
        return _mm.request({
            type: 'post',
            url: '/manage/order/send_goods',
            data: orderNo
        })
    }
}
export default Order;