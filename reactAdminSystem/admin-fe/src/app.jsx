/*
 * @Author: whoiszxl 
 * @Date: 2018-06-08 12:28:22 
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-13 16:04:11
 */

import React from "react";
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Switch, Redirect, Route, Link } from 'react-router-dom'

import Layout           from 'component/layout/index.jsx';

// 页面
import Home             from 'page/home/index.jsx';
import ProductRouter    from 'page/product/router.jsx';
import Login            from 'page/login/index.jsx';
import OrderList        from 'page/order/index.jsx';
import OrderDetail      from 'page/order/detail.jsx';
import UserList         from 'page/user/index.jsx';
import BannerList       from 'page/banner/index.jsx';
import BannerSave       from 'page/banner/save.jsx';
import KeywordsList     from 'page/keywords/index.jsx';
import KeywordsSave     from 'page/keywords/save.jsx';
import ErrorPage        from 'page/error/index.jsx';


class App extends React.Component {
    render(){
        let LayoutRouter = (
            <Layout> 
                <Switch>
                    <Route exact path="/" component={Home}/>
                    <Route path="/product" component={ProductRouter}/>
                    <Route path="/product-category" component={ProductRouter}/>
                    <Route path="/order/index" component={OrderList}/>
                    <Route path="/order/detail/:orderNumber" component={OrderDetail}/>
                    <Route path="/user/index" component={UserList}/>
                    <Redirect exact from="/order" to="/order/index"/>
                    <Redirect exact from="/user" to="/user/index"/>

                    <Route path="/banner/index" component={BannerList}/>
                    <Route path="/banner/save/:pid?" component={BannerSave}/>
                    <Route path="/keywords/index" component={KeywordsList}/>
                    <Route path="/keywords/save/:pid?" component={KeywordsSave}/>
                    <Route component={ErrorPage}/>
                </Switch>
            </Layout>
        );
        return (
            <Router>
                <Switch>
                    <Route path="/login" component={Login}/>
                    <Route path="/" render={ props => LayoutRouter}/>
                </Switch>
            </Router>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('app')
);