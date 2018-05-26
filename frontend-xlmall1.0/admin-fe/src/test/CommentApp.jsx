import React from 'react';
import './index.css';

import CommentInput from './CommentInput.jsx';
import CommentList from './CommentList.jsx';

class CommentApp extends React.Component{
	constructor(){
		super();
		this.state  = {
			userList: [],
			comments: []
		}
	}

	// _saveComments(comments){
	// 	localStorage.setItem('comments', JSON.stringify(comments));
	// }
	// _loadComments(){
	// 	let comments = localStorage.getItem('comments');
	// 	if(comments){
	// 		this.setState({
	// 			comments
	// 		});
	// 	}
	// }
	handleSubmitComment(comment){
		if(!comment) return;
		if(!comment.username) return alert('请输入用户名！');
		if(!comment.content) return alert('请输入评论内容！');
		this.state.userList.push(comment);
		this.setState({
			userList: this.state.userList
		})
	}
	render(){
		const {userList} = this.state;
		return(
			<div className="wrapper">
				<CommentInput onSubmit = {this.handleSubmitComment.bind(this)} />
				<CommentList users = {userList} />
			</div>
		);
	}
}

export default CommentApp;