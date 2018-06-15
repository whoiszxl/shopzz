/*
* @Author: whoiszxl
* @Date:   2018-02-02 17:13:05
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-15 15:21:18
*/
import React        from 'react';
import Simditor     from 'simditor';
import 'simditor/styles/simditor.scss';
import './index.scss';
import MUtil        from 'util/mm.jsx';

const _mm           = new MUtil();
// 通用的富文本编辑器，依赖jquery
class RichEditor extends React.Component{
    constructor(props){
        super(props);
    }
    componentDidMount(){
        this.loadEditor();
    }
    componentWillReceiveProps(nextProps){
        if(this.props.defaultDetail !== nextProps.defaultDetail){
            this.simditor.setValue(nextProps.defaultDetail);
        }
    }
    loadEditor(){
        let element = this.refs['textarea'];
        this.simditor = new Simditor({
            textarea: $(element),
            defaultValue: this.props.placeholder || '请输入内容',
            upload: {
                url             : _mm.apiUrl+'/manage/product/richtext_img_upload',
                defaultImage    : '',
                params          : {
                    'auth_token': _mm.getToken()
                },
                fileKey         : 'upload_file'
            },
        });
        this.bindEditorEvent();
    }
    // 初始化富文本编辑器的事件
    bindEditorEvent(){
        this.simditor.on('valuechanged', e => {
            this.props.onValueChange(this.simditor.getValue());
        })
    }
    render(){
        return (
            <div className="rich-editor">
                <textarea ref="textarea"></textarea>
            </div>
        );
    }
}

export default RichEditor;