/*
* @Author: whoiszxl
* @Date:   2018-01-25 21:21:46
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-09 15:01:53
*/
import MUtil        from 'util/mm.jsx'

const _mm   = new MUtil();

class User{
    // 用户登录
    login(loginInfo){
        return _mm.request({
            type: 'post',
            url: '/manage/user/jwt_login',
            data: loginInfo
        });
    }
    // 检查登录接口的数据是不是合法
    checkLoginInfo(loginInfo){
        let username = $.trim(loginInfo.username),
            password = $.trim(loginInfo.password);
        // 判断用户名为空
        if(typeof username !== 'string' || username.length ===0){
            return {
                status: false,
                msg: '用户名不能为空！'
            }
        }
        // 判断密码为空
        if(typeof password !== 'string' || password.length ===0){
            return {
                status: false,
                msg: '密码不能为空！'
            }
        }
        return {
            status : true,
            msg : '验证通过'
        }
    }
    // 退出登录
    logout(){
        return _mm.request({
            type    : 'post',
            url     : '/user/logout'
        });
    }
    getUserList(pageNum){
        return _mm.request({
            type    : 'post',
            url     : '/manage/user/list',
            data    : {
                pageNum : pageNum
            }
        });
    }
}

export default User;