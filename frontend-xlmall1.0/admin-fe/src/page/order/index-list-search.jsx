import React from 'react';

class ListSearch extends React.Component {
    constructor(props){
        super(props);
        this.state = {
          orderNo: ''
        };
    }
    onGetValueChange(e){
        let searchName = e.target.name,
            searchValue = e.target.value.trim();
        this.setState({
            [searchName] : searchValue
        });
    }
    //点击搜索按钮
    onSearchSubmit(){
        this.props.onSearch(this.state.orderNo); 
    }
    //按回车提交
    onSearchKeywordKeyup(e){
        if(e.keyCode === 13){
            this.onSearchSubmit();
        }
    }
    render() {
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
                           <input type="text" name="orderNo" className="form-control" placeholder="订单号"
                                  onChange = {(e) => this.onGetValueChange(e)}
                                  onKeyUp = {(e) => this.onSearchKeywordKeyup(e)}
                                  />
                       </div>
                       <button className="btn btn-primary" onClick = {(e) => this.onSearchSubmit(e)}>搜索</button>
                   </div>
               </div>
           </div>
        );
    }
}

export default ListSearch;