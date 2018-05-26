import React from 'react';
import './save.scss';
import './detail.scss';

import CommonTitle from 'component/common-title/index.jsx';
import CategorySelector from './category-selector.jsx';

import MUtil from 'util/mm.jsx';
const _mm = new MUtil();

import Product from 'service/product-service.jsx';
const _product = new Product(); 

class ProductDetail extends React.Component {
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
          this.setState(res);
        }, (errMsg) => {
          _mm.errorTips(errMsg);
        })
      }
    }
    render() {
        return (
            <div>
                <div id="page-wrapper">
                    <CommonTitle title="商品详情" />
                    <div className="form-horizontal">
                        <div className="form-group">
                            <label className="col-md-2 control-label">商品名称</label>
                            <div className="col-md-5">
                                <p className="form-control-static">{this.state.name}</p>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-md-2 control-label">商品描述</label>
                            <div className="col-md-5">
                                <p className="form-control-static">{this.state.subtitle}</p>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-md-2 control-label">所属分类</label>
                            <CategorySelector readOnly
                            categoryId = {this.state.categoryId}
                            parentCateId = {this.state.parentCateId}
                            />
                        </div>
                        <div className="form-group">
                            <label className="col-md-2 control-label">商品价格</label>
                            <div className="col-md-3">
                                <div className="input-group">
                                    <input type="number" className="form-control" readOnly
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
                                    <input type="number" readOnly className="form-control" 
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
                                        <div className="img-con" key={index}>
                                            <img src={img.url} alt=""/>
                                        </div>
                                    )
                                    : 
                                    <div>暂无图片</div>
                                }
                            </div>
                        </div>
                        <div className="form-group pro-detail">
                            <label className="col-md-2 control-label">商品详情</label>
                            <div className="col-md-10" dangerouslySetInnerHTML = {{__html: this.state.detail}}>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ProductDetail;