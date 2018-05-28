import React    from 'react';
import {Link}   from 'react-router-dom';

import MUtil from 'util/mm.jsx';
const _mm = new MUtil();    //重新定义

import User from 'service/user-service.jsx';
const _user = new User();

class TopNav extends React.Component{
	constructor(props){
		super(props);
    this.state = {
      username:  _mm.getStorage('userInfo').username || ''
    }
	}


  // componentWillMount(){
  //   console.log(_mm.getStorage('username'));
  //   this.setState({
  //     userInfo: _mm.getStorage('username'),
  //     name: 'kally'
  //   })
  // }
  // componentDidMount(){
  //   console.log(this.state.userInfo);
  //   console.log(this.state.name);
  // }
    //退出登录
    onLogout(){
      _user.logout().then(res => {
        _mm.removeStorage('userInfo');
        //退出成功后跳转到登录页
        // this.props.history.push('/login');
        window.location.href = '/login';
      }, errMsg => {
        _mm.errorTips(errorTips);
      });
    }
	render(){
		return (
			<div className="navbar navbar-default top-navbar">
	            <div className="navbar-header">
	                <Link className="navbar-brand" to="/"><b>HAPPY</b>MMALL</Link>
	            </div>
	            <ul className="nav navbar-top-links navbar-right">
	      			<li className="dropdown">
                      <a className="dropdown-toggle" href="javascript:;" aria-expanded="false">
                          <i className="fa fa-user fa-fw"></i>
                          {
                            this.state.username 
                            ? 
                            <span>欢迎，{this.state.username}</span>
                            :
                            <span>欢迎您！</span>
                          }
                            <i className="fa fa-caret-down"></i>
                      </a>
                      	<ul className="dropdown-menu dropdown-user">
                            <li>
                          	    <a onClick={() => {this.onLogout()}}><i className="fa fa-sign-out fa-fw"></i>
                                    <span>退出登录</span>
                                </a>
                            </li>
                      	</ul>
                  	</li>
	            </ul>
	        </div>			
		);
	}
}

export default TopNav;