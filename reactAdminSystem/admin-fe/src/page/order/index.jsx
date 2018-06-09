/*
* @Author: Rosen
* @Date:   2018-02-05 13:01:18
* @Last Modified by:   Rosen
* @Last Modified time: 2018-02-05 13:24:59
*/
import React        from 'react';
import { Link }     from 'react-router-dom';
import MUtil        from 'util/mm.jsx'
import Order        from 'service/order-service.jsx'

import PageTitle    from 'component/page-title/index.jsx';
import ListSearch   from './index-list-search.jsx';
import TableList    from 'util/table-list/index.jsx';
import Pagination   from 'util/pagination/index.jsx';


const _mm           = new MUtil();
const _order        = new Order();

class OrderList extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            list            : [],
            pageNum         : 1,
            listType        : 'list' // list / search
        };
    }
    componentDidMount(){
        this.loadOrderList();
    }
    // 加载商品列表
    loadOrderList(){
        let listParam = {};
        listParam.listType = this.state.listType;
        listParam.pageNum  = this.state.pageNum;
        // 如果是搜索的话，需要传入搜索类型和搜索关键字
        if(this.state.listType === 'search'){
            listParam.orderNo = this.state.orderNumber;
        }
        // 请求接口
        _order.getOrderList(listParam).then(res => {
            this.setState(res);
        }, errMsg => {
            this.setState({
                list : []
            });
            _mm.errorTips(errMsg);
        });
    }
    // 搜索
    onSearch(orderNumber){
        let listType = orderNumber === '' ? 'list' : 'search';
        this.setState({
            listType        : listType,
            pageNum         : 1,
            orderNumber     : orderNumber
        }, () => {
            this.loadOrderList();
        });
    }
    // 页数发生变化的时候
    onPageNumChange(pageNum){
        this.setState({
            pageNum : pageNum
        }, () => {
            this.loadOrderList();
        });
    }
    render(){
        let tableHeads = ['订单号', '收件人', '订单状态', '订单总价', '创建时间', '操作'];
        return (
            <div id="page-wrapper">
                <PageTitle title="订单列表" />
                <ListSearch onSearch={(orderNumber) => {this.onSearch(orderNumber)}}/>
                <TableList tableHeads={tableHeads}>
                    {
                        this.state.list.map((order, index) => {
                            return (
                                <tr key={index}>
                                    <td>
                                        <Link to={ `/order/detail/${order.orderNo}` }>{order.orderNo}</Link>
                                    </td>
                                    <td>{order.receiverName}</td>
                                    <td>{order.statusDesc}</td>
                                    <td>￥{order.payment}</td>
                                    <td>{order.createTime}</td>
                                    <td>
                                        <Link to={ `/order/detail/${order.orderNo}` }>详情</Link>
                                    </td>
                                </tr>
                            );
                        })
                    }
                </TableList>
                <Pagination current={this.state.pageNum} 
                    total={this.state.total} 
                    onChange={(pageNum) => this.onPageNumChange(pageNum)}/>
            </div>
        );
    }
}

export default OrderList;