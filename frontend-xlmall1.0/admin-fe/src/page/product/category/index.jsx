import React from 'react';
import './index.scss';
import { Link } from 'react-router-dom';

import CommonTitle from 'component/common-title/index.jsx';
import TableList from 'util/table-list/index.jsx';

import MUtil from 'util/mm.jsx';
const _mm = new MUtil(); 

import Category from 'service/category-service.jsx';
const _category = new Category();

class CategoryList extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            list : [],
            parentCateId: this.props.match.params.cateId || 0
        };
    }
    componentDidMount(){
        this._loadCateList();
    }
    //参数发生变化的时候state没有发生变化，所以列表不会重新加载。但是会触发组件更新，因此
    componentDidUpdate(prevProps, prevState){
        let oldPath = prevProps.location.pathname,
            newPath = this.props.location.pathname,
            newCateId = this.props.match.params.cateId || 0;
        if(oldPath !== newPath){
            this.setState({
                parentCateId: newCateId
            }, () => {
                this._loadCateList();
            })
        }
    }
    _loadCateList(){
       _category.getCategoryList(this.state.parentCateId).then((res) => {
            this.setState({
                list: res
            });
        }, (errMsg) => {
            this.setState({
                list: []
            });
            _mm.errorTips(errMsg);
        });
    }
    //更新品类名称
    handleUpdateName(categoryId, categoryName){
        let newName = window.prompt('请输入新的品类名称', categoryName);
        if(newName){
            _category.updateCateName({
                categoryId: categoryId,
                categoryName: newName
            }).then((res) => {
                _mm.successTips(res);
                this._loadCateList();
            }, (errMsg) => {
                _mm.errorTips(errMsg);
            });
        }
    }
    render(){
        let tableHeads = [
            {name: '品类ID'},
            {name: '品类名称'},
            {name: '操作'}
        ];
        return (
            <div id="page-wrapper">
                <CommonTitle title="品类列表">
                    <div className="page-header-right">
                    <Link to="/product-category/add" className="btn btn-primary">
                        <i className="fa fa-plus"></i>
                        <span>添加品类</span>
                    </Link>
                    </div>
                </CommonTitle>
                <div className="row">
                    <div className="col-md-12">
                        <p>父品类ID：{this.state.parentCateId}</p>
                    </div>
                </div>
                <TableList tableHeads = {tableHeads}>
                    {
                        this.state.list.map((cate, index) => {
                            return (
                                    <tr key={index}>
                                        <td>{cate.id}</td>
                                        <td>{cate.name}</td>
                                        <td>
                                            <a className="oper" onClick = {(e) => this.handleUpdateName(cate.id, cate.name)}>修改名称</a>
                                            {
                                                cate.parentId === 0
                                                ? <Link to = {`/product-category/index/${cate.id}`}>查看子品类</Link>
                                                : null
                                            }
                                        </td>
                                    </tr>
                                )
                            })
                    }
                </TableList>
            </div>
        );
    }
}

export default CategoryList;