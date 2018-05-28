import React from 'react';

class CommentInput extends React.Component{
	constructor(){
		super();
		this.state = {
			username: '',
			content: ''
		}
	}
	changeUsername(e){
		this.setState({
			username: e.target.value
		})
	}
	changeContent(e){
		this.setState({
			content: e.target.value
		})
	}
	handleSubmit(){
		if(this.props.onSubmit){
			const {username, content} = this.state;
			this.props.onSubmit({username, content});
		}
		let comments = [];
		comments.push(comment);
	}
	componentWillMount(){
		this._loadUsername();
	}
	_loadUsername(){
		const username = localStorage.getItem('username');
		if(username){
			this.setState({
				username: username
			})
		}
	}
	//让鼠标光标最初处于内容输入框内
	componentDidMount(){
		this.textarea.focus();
	}
	//保存用户名
	_saveUsername(username){
		localStorage.setItem('username', username);
	}

	handleUsernameBlur(e){
		this._saveUsername(e.target.value);
	}

	render(){
		return(
			<div className='form-group comment-input'>
		        <div className='comment-field'>
		          	<span className='control-label comment-field-name'>用户名：</span>
		          	<div className='comment-field-input'>
		            	<input type="text" 
		            		   className="form-control" 
		            			value = {this.state.username} 
		            			onChange = {this.changeUsername.bind(this)}
		            			onBlur = {this.handleUsernameBlur.bind(this)}
		            			/>
		          	</div>
		        </div>
		        <div className='comment-field'>
		          	<span className='control-label comment-field-name'>评论内容：</span>
		          	<div className='comment-field-input'>
		            	<textarea className="form-control" 
		            			rows="10" 
		            			onChange = {this.changeContent.bind(this)}
		            			ref = {(textarea) => this.textarea = textarea}
		            			/>
		          	</div>
		        </div>
		        <div className='comment-field-button'>
		          	<button className="btn btn-primary"
		          			onClick = {this.handleSubmit.bind(this)}>发布</button>
		        </div>
      		</div>
		);
	}
}

export default CommentInput;