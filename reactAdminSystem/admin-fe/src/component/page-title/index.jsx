/*
 * @Author: whoiszxl 
 * @Date: 2018-06-09 14:11:10 
 * @Last Modified by:   whoiszxl 
 * @Last Modified time: 2018-06-09 14:11:10 
 */

import React from 'react';

class PageTitle extends React.Component{
    constructor(props){
        super(props);
    }
    componentWillMount(){
        document.title = this.props.title + ' - HAPPY XLMALL';
    }
    render(){
        return (
            <div className="row">
                <div className="col-md-12">
                    <h1 className="page-header">{this.props.title}</h1>
                    {this.props.children}
                </div>
            </div>
        );
    }
}

export default PageTitle;