/*
* @Author: whoiszxl
* @Date:   2018-02-01 16:19:36
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-11 13:08:25
*/
import React                from 'react';
import MUtil                from 'util/mm.jsx'
import Product              from 'service/product-service.jsx'
import PageTitle            from 'component/page-title/index.jsx';
import FileUploader         from 'util/file-uploader/index.jsx'
import RichEditor           from 'util/rich-editor/index.jsx'

import './save.scss';

const _mm           = new MUtil();
const _product      = new Product();

class CategorySave extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            id               : this.props.match.params.categoryId,
            img              : '',
            imgHost          : '',
            name             : '',
            parentId         : '',
            sortOrder        : 0,
            status           : 1,
        }
    }
    componentDidMount(){
        this.loadCategory();
    }
    // 加载banner详情
    loadCategory(){
        // 有id的时候，表示是编辑功能，需要表单回填
        if(this.state.id){
            _product.getCategory(this.state.id).then((res) => {
                this.setState(res);
            }, (errMsg) => {
                _mm.errorTips(errMsg);
            });
        }
    }
    // 简单字段的改变，比如品类名称，描述，价格，库存
    onValueChange(e){
        let name = e.target.name,
            value = e.target.value.trim();
        this.setState({
            [name] : value
        });
    }

    // 上传图片成功
    onUploadSuccess(res){
        console.log(res);
        this.setState({
            img           : res.uri,
        });
    }
    // 上传图片失败
    onUploadError(errMsg){
        _mm.errorTips(errMsg);
    }


    // 提交表单
    onSubmit(){
        let category = {
            name        : this.state.name,
            img         : this.state.img,
            sortOrder   : parseInt(this.state.sortOrder)
        },

        categoryCheckResult = _product.checkCategory(category);
        if(this.state.id){
            category.id = this.state.id;
        }
        // 表单验证成功
        if(categoryCheckResult.status){
            _product.saveManageCategory(category).then((res) => {
                _mm.successTips(res);
                this.props.history.push('/product-category/index');
            }, (errMsg) => {
                _mm.errorTips(errMsg);
            });
        }
        // 表单验证失败
        else{
            _mm.errorTips(categoryCheckResult.msg);
        }
        
    }
    render(){
        return (
            <div id="page-wrapper">
                <PageTitle title={this.state.id ? '编辑品类' : '添加品类'} />
                <div className="form-horizontal">
                    <div className="form-group">
                        <label className="col-md-2 control-label">品类标题</label>
                        <div className="col-md-5">
                            <input type="text" className="form-control" 
                                placeholder="请输入品类标题"
                                name="title"
                                value={this.state.name}
                                onChange={(e) => this.onValueChange(e)}/>
                        </div>
                    </div>
                    
                    <div className="form-group">
                        <label className="col-md-2 control-label">品类图片</label>
                        <div className="col-md-10">
                          
                               
                        <div className="category-img-con">
                            <img className="category-img" src={this.state.imgHost+this.state.img} />
                            <i className="fa fa-close"></i>
                        </div>
                        
                        <div>请上传图片</div>
                            
                        </div>
                        <div className="col-md-offset-2 col-md-10 file-upload-con">
                            <FileUploader onSuccess={(res) => this.onUploadSuccess(res)}
                                onError={(errMsg) => this.onUploadError(errMsg)}/>
                        </div>
                    </div>

                    <div className="form-group">
                        <label className="col-md-2 control-label">排序权重</label>
                        <div className="col-md-5">
                            <input type="text" className="form-control" 
                                placeholder="请输入品类排序权重"
                                name="sortOrder"
                                value={this.state.sortOrder}
                                onChange={(e) => this.onValueChange(e)}/>
                        </div>
                    </div>
                    <div className="form-group">
                        <div className="col-md-offset-2 col-md-10">
                            <button type="submit" className="btn btn-primary" 
                                onClick={(e) => {this.onSubmit(e)}}>提交</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default CategorySave;