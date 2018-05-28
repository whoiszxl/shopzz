import React from 'react';
import Simditor from 'simditor';
import 'simditor/styles/simditor.scss';
import './index.scss';

import MUtil from 'util/mm.jsx';
const _mm = new MUtil();


//通用富文本编辑器，依赖jquery
class RichEditor extends React.Component {
    constructor(props){
        super(props);
        this.state = {
           
        }
    }
    componentDidMount(){
        this._loadEditor();
    }
    componentWillReceiveProps(nextProps){
        if(this.props.defaultDetail !== nextProps.defaultDetail){
            this.simditor.setValue(nextProps.defaultDetail);
        }
    }
    _loadEditor(){
        let ele = this.refs['textarea'];
        this.simditor = new Simditor({
            textarea: $(ele),
            defaultValue: this.props.placeholder || '请输入内容...',
            upload: {
                url         : '/manage/product/richtext_img_upload.do',
                defaultImage: '',
                fileKey     : 'upload_file'
            }
        });
        this._bindEditorEvent();

    }
    _bindEditorEvent(){
        this.simditor.on('valuechanged', e => {
            this.props.onValueChange(this.simditor.getValue());
        })
    }
    render() {
        return (
            <div className="rich-editor">
                <textarea ref="textarea">
                </textarea>
            </div>
        );
    }
}

export default RichEditor;