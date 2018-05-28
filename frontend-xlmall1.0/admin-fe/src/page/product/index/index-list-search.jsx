import React from 'react';

class ListSearch extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            searchType: 'productId', //productId || productName， 默认productId
            searchKeyword: ''
        };
    }
    // onGetSearchKeyword(e){
    //     this.setState({
    //         searchKeyword: e.target.value
    //     })
    // }
    // onGetSearchType(e){
    //     this.setState({
    //         searchType: e.target.value
    //     })
    // }
    //合并上面2个获取value的函数
    onGetValueChange(e){
        let searchName = e.target.name,
            searchValue = e.target.value.trim();
        this.setState({
            [searchName] : searchValue
        });
    }
    //点击搜索按钮
    onSearchSubmit(){
        this.props.onSearch(this.state.searchType, this.state.searchKeyword); //?????????????
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
                           <select className="form-control" name="searchType" onChange = {(e) => this.onGetValueChange(e)}>
                               <option value="productId">按商品ID查询</option>
                               <option value="productName">按商品名称查询</option>
                           </select>
                       </div>
                       <div className="form-group">
                           <input type="text" name="searchKeyword" className="form-control" placeholder="关键词"
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