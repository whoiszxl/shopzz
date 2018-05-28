import React from 'react';
import './index.scss';
import { Link } from 'react-router-dom';

import CommonTitle from 'component/common-title/index.jsx';
import Pagination from 'util/pagination/index.jsx';
import TableList from 'util/table-list/index.jsx';

import MUtil from 'util/mm.jsx';
const _mm = new MUtil(); 

import User from 'service/user-service.jsx';
const _user = new User();

class UserList extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            pageNum: 1,
            list : []
        }
    }
    componentDidMount(){
        this.loadUserList();
    }
    loadUserList(){
       _user.getUserList(this.state.pageNum).then((res) => {
            this.setState(res);
        }, (errMsg) => {
            _mm.errorTips(errMsg);
        });
    }
    //页码发生变化的时候,setState完了之后执行一个回调函数
    onPageNumChange(pageNum){
        this.setState({
            pageNum: pageNum
        }, () => {
            this.loadUserList();
        })
    }
    render(){
        let tableHeads = [
            {name: '用户ID'},
            {name: '用户名'},
            {name: '邮箱'},
            {name: '电话'},
            {name: '注册时间'}
        ];
        return (
            <div id="page-wrapper">
                <CommonTitle title="用户列表" />
                <TableList tableHeads = {tableHeads}>
                    {
                        this.state.list.map((user, index) => {
                            return (
                                    <tr key={index}>
                                        <td>{user.id}</td>
                                        <td>{user.username}</td>
                                        <td>{user.email}</td>
                                        <td>{user.phone}</td>
                                        <td>{new Date(user.createTime).toLocaleString()}</td>
                                    </tr>
                                )
                            })
                    }
                </TableList>
                <Pagination 
                current={this.state.pageNum} 
                total={this.state.total}
                onChange = {(pageNum) => this.onPageNumChange(pageNum)}
                 />
            </div>
        );
    }
}

export default UserList;