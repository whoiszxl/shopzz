import React from 'react';


class Dad extends React.Component{
	  componentDidMount(){
	  	console.log(this.props.children);
	  }
	  render () {
	    return (
	    	<div className="wrap">

	    	</div>
	    )
	  }
}

class Son extends React.Component{
	  render () {
	    return (
	    	<div className="son">
	    		<h2>React.js 小书</h2>
	    	   	<div>开源、免费、专业、简单</div>
	    	   	订阅：<input />
	    	</div>
	    )
	  }
}


class Test extends React.Component{
	  render () {
	    return (
	    	<Dad>
	    		<Son />
	    	</Dad>
	    )
	  }
}

export default Test;