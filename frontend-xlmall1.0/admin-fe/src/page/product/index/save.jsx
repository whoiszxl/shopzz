import React from 'react';
import './save.scss';

import CommonTitle from 'component/common-title/index.jsx';
import CategorySelector from './category-selector.jsx';
import FileUploader from 'util/file-upload/index.jsx';
import RichEditor from 'util/rich-editor/index.jsx';

import MUtil from 'util/mm.jsx';
const _mm = new MUtil();

import Product from 'service/product-service.jsx';
const _product = new Product(); 

class ProductSave extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            id: this.props.match.params.proId, //取路由中的参数
            name: '',
            subtitle: '',
            price: '',
            stock: '',
            categoryId: 0,
            parentCateId: 0,
            subImages: [],
            detail: '',
            status: 1 //商品状态1表示在售
        }
    }
    componentDidMount(){
      this._loadProduct();
    }
    //加载商品详情
    _loadProduct(){
      //有id时表示编辑，回填表单
      if(this.state.id){
        _product.getProduct(this.state.id).then((res) => {
          let imgs = res.subImages.split(',');
          res.subImages = imgs.map((imgUri) => {
            return {
              uri: imgUri,
              url: res.imageHost + imgUri
            }
          });
          res.defaultDetail = res.detail;
          this.setState(res);
        }, (errMsg) => {
          _mm.errorTips(errMsg);
        })
      }
    }
    //简单字段的改变，商品名称、描述、价格、库存
    handleValueChange(e){
      let name = e.target.name,
          value = e.target.value.trim();
      this.setState({
        [name] : value
      })
    }
    //品类选择器的变化
    handleCateSave(categoryId, parentCateId){
        this.setState({
            categoryId,
            parentCateId
        });
    }
    //图片上传成功
    handleUploadSuccess(res){
        let subImages = this.state.subImages;
        subImages.push(res);   //push()的返回值是数组的长度！！！
        this.setState({
            // subImages: [...this.state.subImages, res]
            subImages: subImages //如果用这种写法，必须先赋值再取值
        })
    }
    //图片上传失败
    handleUploadError(errMsg){
        _mm.errorTips(errMsg);
    }
    //删除图片
    handleImgDelete(e){
      let index = parseInt(e.target.getAttribute('index')), //获取target的非标准属性
          subImages = this.state.subImages;
      subImages.splice(index, 1); //删除下标为index的元素
      this.setState({
        subImages
      })
    }
    //获取富文本编辑器中的值
    handleSimditorValueChange(value){
      this.setState({
        detail: value
      })
    }
    //处理图片
    _subImagesString(){
      return this.state.subImages.map((img) => img.uri).join(',');
    }
    //提交商品
    handleSubmit(){
      let product = {
        name        : this.state.name,
        subtitle    : this.state.subtitle,
        categoryId  : parseInt(this.state.categoryId),
        subImages   : this._subImagesString(),
        detail      : this.state.detail,
        price       : parseFloat(this.state.price),
        stock       : parseInt(this.state.stock),
        status      : this.state.status
      };
      let productCheckRes = _product.checkProduct(product);
      if(this.state.id){
        product.id = this.state.id;
      }
      //表单验证成功
      if(productCheckRes.status){
        _product.saveProduct(product).then((res) => {
          _mm.successTips(res);
          this.props.history.push('/product/index'); //跳转到商品首页
        }, (errMsg) => {
          _mm.errorTips(errMsg);
        })
      }
      //表单验证失败
      else{
        _mm.errorTips(productCheckRes.msg);
      }
    }
    render() {
        return (
            <div>
                <div id="page-wrapper">
                    <CommonTitle title={this.state.id ? "编辑商品" : "添加商品"} />
                    <div className="form-horizontal">
                        <div className="form-group">
                            <label className="col-md-2 control-label">商品名称</label>
                            <div className="col-md-5">
                                <input type="text" className="form-control" 
                                name = "name"
                                onChange = {(e) => this.handleValueChange(e)}
                                placeholder="请输入商品名称"
                                value = {this.state.name}
                                />
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-md-2 control-label">商品描述</label>
                            <div className="col-md-5">
                                <input type="text" className="form-control" 
                                name = "subtitle"
                                onChange = {(e) => this.handleValueChange(e)}
                                placeholder="请输入商品描述"
                                value = {this.state.subtitle}
                                 />
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-md-2 control-label">所属分类</label>
                            <CategorySelector onCateChange = {(categoryId, parentCateId) => this.handleCateSave(categoryId, parentCateId)}
                            categoryId = {this.state.categoryId}
                            parentCateId = {this.state.parentCateId}
                             />
                        {/*
                            这里用组件实现
                            <div className="col-md-5">
                                <select name="" className="form-control cate-select">
                                    <option value="">请选择一级分类</option>
                                </select>
                                <select name="" className="form-control cate-select">
                                    <option value="">请选择二级分类</option>
                                </select>
                            </div>
                        */}
                        </div>
                        <div className="form-group">
                            <label className="col-md-2 control-label">商品价格</label>
                            <div className="col-md-3">
                                <div className="input-group">
                                    <input type="number" className="form-control" 
                                    name = "price"
                                    onChange = {(e) => this.handleValueChange(e)}
                                    placeholder="价格"
                                    value = {this.state.price}
                                     />
                                    <span className="input-group-addon">元</span>
                                </div>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-md-2 control-label">商品库存</label>
                            <div className="col-md-3">
                                <div className="input-group">
                                    <input type="number" className="form-control" 
                                    name = "stock"
                                    onChange = {(e) => this.handleValueChange(e)}
                                    placeholder="库存" 
                                    value = {this.state.stock}
                                    />
                                    <span className="input-group-addon">件</span>
                                </div>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-md-2 control-label">商品图片</label>
                            <div className="col-md-10">
                                {
                                    this.state.subImages.length 
                                    ? 
                                    this.state.subImages.map((img, index) => 
                                        (<div className="img-con" key={index}>
                                            <img src={img.url} alt=""/>
                                            <i className="fa fa-close" onClick={(e) => this.handleImgDelete(e)} index = {index}></i>
                                        </div>)
                                    )
                                    : 
                                    (<div>请上传图片</div>)
                                }
                            </div>
                            <div className="col-md-offset-2 col-md-10">
                               <FileUploader onSuccess = {(res) => this.handleUploadSuccess(res)} 
                                             onError = {(errMsg) => this.handleUploadError(errMsg)}
                                />
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-md-2 control-label">商品详情</label>
                            <div className="col-md-10">
                               <RichEditor 
                               detail = {this.state.detail}
                               defaultDetail = {this.state.defaultDetail}
                               onValueChange = {(value) => this.handleSimditorValueChange(value)} />
                            </div>
                        </div>
                        <div className="form-group">
                            <div className="col-md-offset-2 col-md-5">
                                <button type="submit" className="btn btn-primary"
                                onClick = {(e) => this.handleSubmit(e)}
                                >提交</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ProductSave;