import React from 'react';
import Comment from './Comment.jsx';

class CommentList extends React.Component{
	render(){
		const {users} = this.props;
		return(
			<div>
				{users.map((user, index) => 
					<Comment user = {user} key={index} />
				)}
			</div>
		);
	}
}

export default CommentList;