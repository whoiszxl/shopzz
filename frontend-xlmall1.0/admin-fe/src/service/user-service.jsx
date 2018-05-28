import MUtil from 'util/mm.jsx';
const _mm = new MUtil();

class User{
	//用户登录
    login(userInfo){
        return _mm.request({
            type: 'post',
            url: '/manage/user/jwt_login',
            data: userInfo
        });
    }
    logout(){
        return _mm.request({
            type: 'post',
            url: '/user/logout'
        });
    }
    //表单验证
    checkLoginInfo(userInfo){
        let username = $.trim(userInfo.username),
    	    password = $.trim(userInfo.password);
		if(typeof username !== 'string' || username.length === 0){
			return {
				status: false,
				msg: '用户名不能为空！'
			}
		}
        if(typeof password !== 'string' || password.length === 0){
            return {
                status: false,
                msg: '密码不能为空！'
            }
        }
        return {
            status: true,
            msg: '验证通过！'
        }
    }
    //获取首页统计数据
    getStatisticCount(){
        return _mm.request({
            type: 'post',
            url: '/manage/statistic/base_count'
        });
    }
    //获取用户列表
    getUserList(pageNum){
        return _mm.request({
            type: 'get',
            url: '/manage/user/list',
            data: {
                pageNum: pageNum
            }
        })
    }
}
export default User;