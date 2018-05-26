import React from 'react';
import { Link } from 'react-router-dom';

import CommonTitle from 'component/common-title/index.jsx';
import Pagination from 'util/pagination/index.jsx';
import TableList from 'util/table-list/index.jsx';
import ListSearch from './index-list-search.jsx';

import MUtil from 'util/mm.jsx';
const _mm = new MUtil();

import Order from 'service/order-service.jsx';
const _order = new Order(); 

class OrderList extends React.Component{
    constructor(props){
        super(props);
        this.state = {
          list: [],
          pageNum: 1,
          listType: 'list' //看列表是否通过搜索结果渲染，默认是通过list，否则是search
        }
    }
    componentDidMount(){
        this.loadOrderList();
    }
    //加载商品列表
    loadOrderList(){
        let listParam = {};
        listParam.listType = this.state.listType;
        listParam.pageNum = this.state.pageNum;

        if(this.state.listType === 'search'){
            listParam.orderNo = this.state.orderNo;
        }

        _order.getOrderList(listParam).then((res) => {
             this.setState({
                list: res.list
             });
             }, (errMsg) => {
                this.setState({
                    list: []
                })
             _mm.errorTips(errMsg);
         });
    }
    //搜索
    handleSearch(orderNo){
        let listType = orderNo === '' ? 'list' : 'search';
        this.setState({
            listType:listType,
            pageNum: 1,
            orderNo: orderNo
        }, () => {
            this.loadOrderList();
        });
    }
    //页码发生变化的时候,setState完了之后执行一个回调函数
    handlePageNumChange(pageNum){
        this.setState({
            pageNum: pageNum
        }, () => {
            this.loadOrderList();
        })
    }
    render(){
        let tableHeads = ['订单号', '收件人', '订单状态', '订单总价', '创建时间', '操作'];
        return (
            <div id="page-wrapper">
                <CommonTitle title="订单列表" />
                <ListSearch onSearch = {(orderNo) => this.handleSearch(orderNo)} />
                <TableList tableHeads={tableHeads}>
                {
                        this.state.list.map((order, index) => {
                                return  <tr key={index} className="order-item">
                                        <td>
                                            <Link className="oper" to={ `/order/detail/${order.orderNo}` }>{order.orderNo}</Link>
                                        </td>
                                        <td>{order.receiverName}</td>
                                        <td>{order.statusDesc}</td>
                                        <td>{order.payment}</td>
                                        <td>{order.createTime}</td>
                                        <td>
                                            <Link className="oper" to={ `/order/detail/${order.orderNo}` }>查看详情</Link>
                                        </td>
                                    </tr>
                            })
                }
                </TableList> 
                <Pagination 
                current={this.state.pageNum} 
                total={this.state.total}
                onChange = {(pageNum) => this.handlePageNumChange(pageNum)}
                 />
            </div>
        );
    }
}

export default OrderList;