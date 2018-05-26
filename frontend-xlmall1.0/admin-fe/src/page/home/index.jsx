/*
* @Author: Administrator
* @Date:   2018-04-13 17:33:53
* @Last Modified by:   Administrator
* @Last Modified time: 2018-04-19 09:52:31
*/

import React from 'react';
import './index.scss';
import { Link } from 'react-router-dom';

import CommonTitle from 'component/common-title/index.jsx';

import MUtil from 'util/mm.jsx';
const _mm = new MUtil(); 

import User from 'service/user-service.jsx';
const _user = new User();

////////////////////////////////
//test
// import CommentApp from '../../test/CommentApp.jsx';
// import Test from '../../test/Test.jsx';


class Home extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            userCount: '-',
            productCount: '-',
            orderCount: '-'
        }
    }
    componentDidMount(){    //使用didMount更保险，如果使用willMount,在接口请求很快的情况下，组件渲染还没有完成，就会出现报错的情况
        this.loadCount();
    }
    loadCount(){
        _user.getStatisticCount().then((res) => {
            //因为state中定义的字段名称与接口返回的正好一致，因此可以直接使用res进行设置
            this.setState(res);
        }, (errMsg) => {
            this.setState({
                list: []    //请求错误时，将list清空
            })
            _mm.errorTips(errMsg);
        })
    }
    render() {
        return (
        	<div id="page-wrapper">
       
    {/*
            <Test />
     */}
                <CommonTitle title="首页" />
                <div className="row">
                    <div className="col-md-4">
                        <Link to="/user" className="color-box brown">
                            <p className="count">{this.state.userCount}</p>
                            <p className="desc">
                                <i className="fa fa-user-o"></i>
                                <span>用户总数</span>
                            </p>
                        </Link>
                    </div>
                    <div className="col-md-4">
                        <Link to="/product" className="color-box green">
                            <p className="count">{this.state.productCount}</p>
                            <p className="desc">
                                <i className="fa fa-list"></i>
                                <span>商品总数</span>
                            </p>
                        </Link>
                    </div>
                    <div className="col-md-4">
                        <Link to="/order" className="color-box blue">
                            <p className="count">{this.state.orderCount}</p>
                            <p className="desc">
                                <i className="fa fa-check-square-o"></i>
                                <span>订单总数</span>
                            </p>
                        </Link>
                    </div>
                </div>


        	</div>
        );
    }
}

export default Home;