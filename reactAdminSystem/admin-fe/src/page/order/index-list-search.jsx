/*
* @Author: Rosen
* @Date:   2018-02-05 13:14:41
* @Last Modified by:   Rosen
* @Last Modified time: 2018-02-05 13:16:56
*/
import React        from 'react';

class ListSearch extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            orderNumber   : ''
        }
    }
    // 数据变化的时候
    onValueChange(e){
        let name    = e.target.name,
            value   = e.target.value.trim();
        this.setState({
            [name] : value
        });
    }
    // 点击搜索按钮的时候
    onSearch(){
        this.props.onSearch(this.state.orderNumber);
    }
    // 输入关键字后按回车，自动提交
    onSearchKeywordKeyUp(e){
        if(e.keyCode === 13){
            this.onSearch();
        }
    }
    render(){
        return (
            <div className="row search-wrap">
                <div className="col-md-12">
                    <div className="form-inline">
                        <div className="form-group">
                            <select className="form-control">
                                <option value="">按订单号查询</option>
                            </select>
                        </div>
                        <div className="form-group">
                            <input type="text" 
                                className="form-control" 
                                placeholder="订单号"
                                name="orderNumber"
                                onKeyUp={(e) => this.onSearchKeywordKeyUp(e)}
                                onChange={(e) => this.onValueChange(e)}/>
                        </div>
                        <button className="btn btn-primary" 
                            onClick={(e) => this.onSearch()}>搜索</button>
                    </div>
                </div>
            </div>
        )
    }
}
export default ListSearch;