/*
* @Author: whoiszxl
* @Date:   2018-01-23 22:54:28
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-15 16:11:04
*/

class MUtil{

    constructor() {
        //this.apiUrl = "http://118.126.92.128:10101";
        this.apiUrl = "http://localhost:8888";
    }

    request(param){
        console.log("requ");
        let my_headers = null;
        if(param.headers == null && this.getToken() != null) {
            my_headers = {
                'Authorization': this.getToken()
            };
        }
        return new Promise((resolve, reject) => {
            $.ajax({
                type        : param.type        || 'get',
                url         : param.url         || '',
                dataType    : param.dataType    || 'json',
                data        : param.data        || null,
                headers     : param.headers     || my_headers,
                success     : res => {
                    // 数据请求成功
                    if(0 === res.status){
                        typeof resolve === 'function' && resolve(res.data, res.msg);
                    }
                    // 没有登录状态，强制登录
                    else if(10 === res.status || 401 === res.status){
                        this.doLogin();
                    }
                    else{
                        typeof reject === 'function' && reject(res.msg || res.data);
                    }
                },
                error       : err => {
                    console.log(err);
                    typeof reject === 'function' && reject(err.statusText);
                }
            });
        });  
    }
    // 跳转登录
    doLogin(){
        window.location.href = '/login?redirect=' + encodeURIComponent(window.location.pathname);
    }
    // 获取URL参数
    getUrlParam(name){
        // param=123&param1=456
        let queryString = window.location.search.split('?')[1] || '',
            reg         = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"),
            result      = queryString.match(reg);
        return result ? decodeURIComponent(result[2]) : null;
    }
    // 成功提示
    successTips(successMsg){
        alert(successMsg || '操作成功！');
    }
    // 错误提示
    errorTips(errMsg){
        alert(errMsg || '好像哪里不对了~');
    }
    // 本地存储
    setStorage(name, data){
        let dataType = typeof data;
        // json对象
        if(dataType === 'object'){
            window.localStorage.setItem(name, JSON.stringify(data));
        }
        // 基础类型
        else if(['number','string','boolean'].indexOf(dataType) >= 0){
            window.localStorage.setItem(name, data);
        }
        // 其他不支持的类型
        else{
            alert('该类型不能用于本地存储');
        }
    }

    //取出当前登录的token
    getToken(){
        let data = window.localStorage.getItem('jwt_token');
        if(data){
            return data;
        }
        else{
            return '';
        }
    }

    // 取出本地存储内容
    getStorage(name){
        let data = window.localStorage.getItem(name);
        if(data){
            return JSON.parse(data);
        }
        else{
            return '';
        }
    }
    // 删除本地存储
    removeStorage(name){
        window.localStorage.removeItem(name);
    }
}

export default MUtil;