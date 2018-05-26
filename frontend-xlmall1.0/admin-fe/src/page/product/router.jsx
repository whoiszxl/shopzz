import React from 'react';
import {BrowserRouter as Router, Switch, Redirect, Route, Link} from 'react-router-dom';

//页面
import ProductList from 'page/product/index/index.jsx';
import ProductSave from 'page/product/index/save.jsx';
import ProductDetail from 'page/product/index/detail.jsx';
import CategoryList from 'page/product/category/index.jsx';
import CategoryAdd from 'page/product/category/add.jsx';

class ProductRouter extends React.Component {
    render() {
        return (
              <Switch>
                 <Route path="/product/index" component={ProductList} />
                 <Route path="/product/save/:proId?" component={ProductSave} />
                 <Route path="/product/detail/:proId" component={ProductDetail} />
                 <Route path="/product-category/index/:cateId?" component={CategoryList} />
                 <Route path="/product-category/add" component={CategoryAdd} />

                 <Redirect exact from="/product" to="/product/index" />
                 <Redirect exact from="/product-category" to="/product-category/index" />
              </Switch>
        );
    }
}

export default ProductRouter;