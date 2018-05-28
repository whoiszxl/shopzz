import React from 'react';
import {Link, NavLink} from 'react-router-dom';

class SideNav extends React.Component{
	constructor(props){
		super(props);
	}
	render(){
		return (
			<div className="navbar-default navbar-side">
			            <div className="sidebar-collapse">
			                <ul className="nav">
			                    <li>
			                        <NavLink exact to="/" activeClassName="active-menu"><i className="fa fa-dashboard"></i>
			                        	<span>首页</span>
			                        </NavLink>
			                    </li>
			                    <li className="active">
			                        <Link to="/product">
			                        	<i className="fa fa-list"></i>
			                        	<span>商品</span> 
			                        	<span className="fa arrow"></span>
			                        </Link>
			                        <ul className="nav nav-second-level collapse in">
			                        	<li>
			                                <NavLink to="/product" activeClassName="active-menu">
			                                	<span>商品管理</span>
			                                </NavLink>
			                            </li>
			                            <li>
			                                <NavLink to="/product-category" activeClassName="active-menu">
			                                	<span>品类管理</span>
			                                </NavLink>
			                            </li>
			                        </ul>
			                    </li>
			                    <li className="active">
			                        <Link to="/order">
			                        	<i className="fa fa-check-square-o"></i>
			                        	<span>订单</span> 
			                        	<span className="fa arrow"></span>
			                        </Link>
			                        <ul className="nav nav-second-level collapse in">
			                            <li>
			                                <NavLink to="/order" activeClassName="active-menu">
			                                	<span>订单管理</span>
			                                </NavLink>
			                            </li>
			                        </ul>
			                    </li>
			                    <li className="active">
			                        <Link to="/user">
			                        	<i className="fa fa-user-o"></i>
			                        	<span>用户</span> 
			                        	<span className="fa arrow"></span>
			                        </Link>
			                        <ul className="nav nav-second-level collapse in">
			                            <li>
			                                <NavLink to="/user" activeClassName="active-menu">
			                                	<span>用户管理</span>
			                                </NavLink>
			                            </li>
			                        </ul>
			                    </li>
			                </ul>
			            </div>
			        </div>
		);
	}
}

export default SideNav;