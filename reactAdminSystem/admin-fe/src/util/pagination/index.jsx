/*
* @Author: whoiszxl
* @Date:   2018-01-26 17:02:27
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-09 14:32:20
*/
import React        from 'react';
import RcPagination   from 'rc-pagination';
import 'rc-pagination/dist/rc-pagination.min.css';

// 通用分页组件
class Pagination extends React.Component{
    constructor(props){
        super(props);
    }
    render(){
        return (
            <div className="row">
                <div className="col-md-12">
                    <RcPagination {...this.props} 
                        hideOnSinglePage
                        showQuickJumper/>
                </div>
            </div>
        );
    }
}

export default Pagination;