/*
* @Author: whoiszxl
* @Date:   2018-02-01 16:19:36
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-13 16:05:14
*/
import React                from 'react';
import MUtil                from 'util/mm.jsx'
import Article              from 'service/article-service.jsx'
import PageTitle            from 'component/page-title/index.jsx';
import FileUploader         from 'util/file-uploader/index.jsx'
import RichEditor           from 'util/rich-editor/index.jsx'

import './save.scss';

const _mm           = new MUtil();
const _article      = new Article();

class KeywordsSave extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            id               : this.props.match.params.pid,
            title            : '',
            imgurl           : '',
            showimg          : '',
            jumpurl          : '',
            sort             : 0,
            status           : 1,
        }
    }
    componentDidMount(){
        this.loadKeywords();
    }
    // 加载banner详情
    loadKeywords(){
        // 有id的时候，表示是编辑功能，需要表单回填
        if(this.state.id){
            _article.getKeywordsDetail(this.state.id).then((res) => {
                this.setState(res);
                this.setState({
                    showimg : res.imgurl
                });
            }, (errMsg) => {
                _mm.errorTips(errMsg);
            });
        }
    }
    // 简单字段的改变，比如轮播图名称，描述，价格，库存
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
            imgurl           : res.uri,
            showimg          : res.url,
        });
    }
    // 上传图片失败
    onUploadError(errMsg){
        _mm.errorTips(errMsg);
    }


    // 提交表单
    onSubmit(){
        let banner = {
            title        : this.state.title,
            imgurl    : this.state.imgurl,
            jumpurl      : this.state.jumpurl,
            sort       : parseInt(this.state.sort),
            status      : this.state.status
        },

        bannerCheckResult = _article.checkKeywords(banner);
        if(this.state.id){
            banner.id = this.state.id;
        }
        // 表单验证成功
        if(bannerCheckResult.status){
            _article.saveKeywords(banner).then((res) => {
                _mm.successTips(res);
                this.props.history.push('/banner/index');
            }, (errMsg) => {
                _mm.errorTips(errMsg);
            });
        }
        // 表单验证失败
        else{
            _mm.errorTips(bannerCheckResult.msg);
        }
        
    }
    render(){
        return (
            <div id="page-wrapper">
                <PageTitle title={this.state.id ? '编辑轮播图' : '添加轮播图'} />
                <div className="form-horizontal">
                    <div className="form-group">
                        <label className="col-md-2 control-label">轮播图标题</label>
                        <div className="col-md-5">
                            <input type="text" className="form-control" 
                                placeholder="请输入轮播图标题"
                                name="title"
                                value={this.state.title}
                                onChange={(e) => this.onValueChange(e)}/>
                        </div>
                    </div>
                    
                    <div className="form-group">
                        <label className="col-md-2 control-label">轮播图图片</label>
                        <div className="col-md-10">
                          
                               
                        <div className="img-con">
                            <img className="img" src={this.state.showimg} />
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
                        <label className="col-md-2 control-label">轮播图点击跳转地址</label>
                        <div className="col-md-5">
                            <input type="text" className="form-control" 
                                placeholder="请输入轮播图点击跳转地址"
                                name="jumpurl"
                                value={this.state.jumpurl}
                                onChange={(e) => this.onValueChange(e)}/>
                        </div>
                    </div>

                    <div className="form-group">
                        <label className="col-md-2 control-label">排序权重</label>
                        <div className="col-md-5">
                            <input type="text" className="form-control" 
                                placeholder="请输入轮播图排序权重"
                                name="sort"
                                value={this.state.sort}
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
export default KeywordsSave;