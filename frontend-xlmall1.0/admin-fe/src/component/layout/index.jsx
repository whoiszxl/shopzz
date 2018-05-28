import React from 'react';
import './theme.css';
import './index.scss';

import TopNav from 'component/top-nav/index.jsx';
import SideNav from 'component/side-nav/index.jsx';

class Layout extends React.Component{
	constructor(props){
		super(props);
	}
	render(){
		return (
			<div id="wrapper">
				<TopNav />
				<SideNav />
				{this.props.children}
			</div>
		);
	}
}

export default Layout;