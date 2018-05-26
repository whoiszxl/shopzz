import React from 'react';
import FileUpload from './react-file-upload.jsx';

class FileUploader extends React.Component{
	constructor(){
		super()
	}
	render(){
		const options={
			baseUrl:'/manage/product/upload.do',
			fileFieldName: 'upload_file',	//后端根据这个名字提取相关的value
			dataType: 'json',
			chooseAndUpload: true,
			uploadSuccess: (res) => this.props.onSuccess(res.data),	//回调函数从外面传进来，这里参数直接取res中的data字段
			uploadError: (err) => this.props.onError(err.message || '图片上传失败')
		}
		return (
			<FileUpload options={options}>
				<button ref="chooseAndUpload" className="btn btn-xs btn-primary btn-upload">选择图片</button>
			</FileUpload>
		)	        
	}
}

export default FileUploader;

