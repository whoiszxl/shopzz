/*
* @Author: Rosen
* @Date:   2018-01-31 13:06:57
* @Last Modified by:   Rosen
* @Last Modified time: 2018-02-04 22:21:43
*/
import React            from 'react';
import { BrowserRouter as Router, Switch, Redirect, Route, Link } from 'react-router-dom'

// 页面
import ProductList      from 'page/product/index/index.jsx';
import ProductSave      from 'page/product/index/save.jsx';
import ProductDetail    from 'page/product/index/detail.jsx';
import CategoryList     from 'page/product/category/index.jsx';
import CategoryAdd      from 'page/product/category/add.jsx';

class ProductRouter extends React.Component{
    render(){
        return (
            <Switch>
                <Route path="/product/index" component={ProductList}/>
                <Route path="/product/save/:pid?" component={ProductSave}/>
                <Route path="/product/detail/:pid" component={ProductDetail}/>
                <Route path="/product-category/index/:categoryId?" component={CategoryList}/>
                <Route path="/product-category/add" component={CategoryAdd}/>
                <Redirect exact from="/product" to="/product/index"/>
                <Redirect exact from="/product-category" to="/product-category/index"/>
            </Switch>
        )
    }
}
export default ProductRouter;