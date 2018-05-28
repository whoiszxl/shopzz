import React from 'react';
import { Link } from 'react-router-dom';

import CommonTitle from 'component/common-title/index.jsx';
import TableList from 'util/table-list/index.jsx';

import MUtil from 'util/mm.jsx';
const _mm = new MUtil();

import Order from 'service/order-service.jsx';
const _order = new Order(); 

class OrderDetail extends React.Component{
    constructor(props){
        super(props);
        this.state = {
          orderNo: this.props.match.params.orderNo,
          orderInfo: {}  //存放订单相关的信息
        }
    }
    componentDidMount(){
        this.loadOrderDetail();
    }
    //加载订单详情
    loadOrderDetail(){
        _order.getOrderDetail({
          orderNo: this.state.orderNo
        }).then((res) => {
               this.setState({
                orderInfo: res
               })
             }, (errMsg) => {
             _mm.errorTips(errMsg);
         });
    }

    render(){
        let receiverInfo = this.state.orderInfo.shippingVo || {},
            proList = this.state.orderInfo.orderItemVoList || [],
            tableHeads = ['商品图片', '商品信息', '单价', '数量', '合计'];
        return (
            <div id="page-wrapper">
                <CommonTitle title="订单详情" />
                <div className="row">
                  <div className="form-wrap col-md-12">
                    <div className="form-horizontal">
                      <div className="form-group">
                        <label htmlFor="name" className="col-md-2 control-label">订单号：</label>
                        <div className="col-md-5">
                          <p type="text" className="form-control-static">{this.state.orderInfo.orderNo}</p>
                        </div>
                      </div>
                      <div className="form-group">
                        <label htmlFor="subtitle" className="col-md-2 control-label">创建时间：</label>
                        <div className="col-md-5">
                          <p type="text" className="form-control-static">{this.state.orderInfo.createTime}</p>
                        </div>
                      </div>
                      <div className="form-group">
                        <label htmlFor="subtitle" className="col-md-2 control-label">收件人：</label>
                        <div className="col-md-5">
                          <p type="text" className="form-control-static">
                          {receiverInfo.receiverName}
                          {receiverInfo.receiverProvince}
                          {receiverInfo.receiverCity}
                          {receiverInfo.receiverAddress}
                          {receiverInfo.receiverMobile || receiverInfo.receiverPhone}
                          </p>
                        </div>
                      </div>
                      <div className="form-group"><label htmlFor="subtitle" className="col-md-2 control-label">订单状态：</label>
                        <div className="col-md-5">
                          <p type="text" className="form-control-static">{this.state.orderInfo.statusDesc}</p>
                          {
                            this.state.orderInfo.status === 20 ? <button className="btn btn-sm btn-default btn-send-goods">立即发货</button> : null
                          }
                        </div>
                      </div>
                      <div className="form-group">
                          <label htmlFor="subtitle" className="col-md-2 control-label">支付方式：</label>
                          <div className="col-md-5">
                              <p type="text" className="form-control-static">{this.state.orderInfo.paymentTypeDesc}</p>
                          </div>
                      </div>
                      <div className="form-group">
                          <label htmlFor="subtitle" className="col-md-2 control-label">订单金额：</label>
                          <div className="col-md-5">
                              <p type="text" className="form-control-static">￥{this.state.orderInfo.payment}</p>
                          </div>
                      </div>
                      <div className="form-group">
                          <label htmlFor="subtitle" className="col-md-2 control-label">商品列表：</label>
                          <div className="col-md-10">
                              <TableList tableHeads = {tableHeads}>
                                {
                                  proList.map((product, index) => {
                                      return (
                                              <tr key={index} className="pro-item">
                                                  <td>
                                                      <img src={`${this.state.orderInfo.imageHost}${product.productImage}`} alt={product.productName}/>
                                                  </td>
                                                  <td>
                                                      <p>{product.productName}</p>
                                                  </td>
                                                  <td>￥{product.currentUnitPrice}</td>
                                                  <td>{product.quantity}</td>
                                                  <td>￥{product.totalPrice}</td>
                                              </tr>
                                          )
                                      })
                                }
                              </TableList>
                          </div>
                      </div>
                    </div>
                  </div>
                </div>
               
            </div>
        );
    }
}

export default OrderDetail;