import React from 'react';

import CommonTitle from 'component/common-title/index.jsx';

import MUtil from 'util/mm.jsx';
const _mm = new MUtil(); 

import Category from 'service/category-service.jsx';
const _category = new Category();

class CategoryAdd extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            cateList : [],
            parentCateId : 0,
            newCateName: ''
        };
    }
    componentDidMount(){
        this._loadCateList();
    }
    //加载品类列表，显示父品类列表
    _loadCateList(){
       _category.getCategoryList().then((res) => {
            this.setState({
                cateList: res
            })
        }, (errMsg) => {
            _mm.errorTips(errMsg);
        });
    }
    handleValueChange(e){
        let name = e.target.name,
            value = e.target.value;
        this.setState({
            [name] : value
        })
    }
    handleSubmit(e){
        let newCateName = this.state.newCateName.trim();
        if(newCateName){
            _category.addCategory({
                parentCateId: this.state.parentCateId,
                categoryName: newCateName
            }).then((res) => {
                _mm.successTips(res);
                this.props.history.push('/product-category/index');
            }, (errMsg) => {
                _mm.errorTips(errMsg);
            });
        }
        //新增品类名称为空
        else{
            _mm.errorTips('请输入品类名称！');
        }
    }
    render(){
        return (
            <div id="page-wrapper">
                <CommonTitle title="新增品类" />
                <div className="row">
                    <div className="col-md-12">
                        <div className="form-horizontal">
                            <div className="form-group">
                                <label className="col-md-2 control-label">所属品类</label>
                                <div className="col-md-5">
                                    <select name="parentCateId" className="form-control"
                                            onChange = {(e) => this.handleValueChange(e)}
                                    >
                                        <option value="0">根品类 /</option>
                                        {
                                            this.state.cateList.map((cate, index) => {
                                                return <option value={cate.id} key={index}>{cate.name} /</option>
                                            })
                                        }
                                    </select>
                                </div>
                            </div>
                            <div className="form-group">
                                <label className="col-md-2 control-label">新增品类名称</label>
                                <div className="col-md-5">
                                   <input type="text" className = "form-control"
                                            name = "newCateName"
                                            placeholder = "请输入新品类名称"
                                            onChange = {(e) => this.handleValueChange(e)}
                                   />
                                </div>
                            </div>
                            <div className="form-group">
                                <div className="col-md-offset-2 col-md-10">
                                    <button type="submit" className="btn btn-primary"
                                            onClick = {(e) => this.handleSubmit(e)}
                                    >提交</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default CategoryAdd;