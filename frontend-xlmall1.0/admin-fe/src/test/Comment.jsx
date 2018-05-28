import React from 'react';

class Comment extends React.Component{
	render(){
		const {user} = this.props;
		return(
			<div>
				<p>
					<span className="username">{user.username} :</span>
					<span className="content">{user.content}</span>
				</p>
				<hr/>
			</div>
		);
	}
}

export default Comment;