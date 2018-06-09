/*
 * @Author: whoiszxl 
 * @Date: 2018-06-09 14:10:03 
 * @Last Modified by:   whoiszxl 
 * @Last Modified time: 2018-06-09 14:10:03 
 */

import React from 'react';

import NavTop from 'component/nav-top/index.jsx';
import NavSide from 'component/nav-side/index.jsx';

import './theme.css';
import './index.scss';

class Layout extends React.Component{
    constructor(props){
        super(props);
    }
    render(){
        return (
            <div id="wrapper">
                <NavTop />
                <NavSide />
                {this.props.children}
            </div>
        );
    }
}

export default Layout;