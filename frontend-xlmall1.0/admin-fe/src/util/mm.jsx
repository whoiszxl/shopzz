class MUtil{
    request(param){
        //从localStorage中拿到token
        return new Promise((resolve, reject) => {
            $.ajax({
                type    : param.type     || 'get',
                url     : param.url      || '',
                dataType: param.dataType || 'json',
                data    : param.data     || null,
                headers     : {
                    'Authorization': this.getStorage('token')
                },
                success : res => {
                    //数据请求成功
                    if(res.status === 0){
                        typeof resolve === 'function' && resolve(res.data, res.msg);
                    }
                    //未登录
                    else if(res.status === 10){
                        this.doLogin();
                    }else{
                        //出现错误的时候走reject, 先传错误信息msg, 有些接口信息可能在data里，如果没有msg就传data
                        typeof reject === 'function' && reject(res.msg || res.data);    
                    }
                },
                error  : err => {
                    //statusText是http请求出错时返回的与状态码对应的错误信息，是http请求error对象的一个属性
                    typeof reject === 'function' && reject(err.statusText);    
                }
            });
        });
    }
    //跳转登录
    doLogin(){
        window.location.href = '/login?redirect=' + encodeURIComponent(window.location.pathname);
    }
    //取url参数
    getUrlParam(name){
        //xxx.com?param=123&param1=456
        let queryStr = window.location.search.split('?')[1] || '',   //search取出的是包括?的后面的部分，再用?分割，取数组的第二个元素
            reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)'),
            result = queryStr.match(reg);
        return result ? decodeURIComponent(result[2]) : null;
    }
    //成功提示，之所以把alert封装起来，是为了便于以后做优化，如果有新的提示样式，统一在这里修改就行了
    successTips(successMsg){
        alert(successMsg || '操作成功！');
    }
    //错误提示
    errorTips(errMsg){
        alert(errMsg || '好像哪里不对了~');
    }
    //本地存储
    setStorage(name, data){
        let dataType = typeof data;
        //json
        if(dataType === 'object'){
            window.localStorage.setItem(name, JSON.stringify(data));
        }
        //基础类型
        else if(['number', 'string', 'boolean'].indexOf(dataType) >= 0){
            window.localStorage.setItem(name, data);
        }
        //其他类型
        else{
            alert('该类型不能用于本地存储');
        }
    }
    //获取本地存储
    getStorage(name){
        let data = window.localStorage.getItem(name);
        if(data){
            return data;
        }else{
            return '';
        }
    }
    //删除本地存储
    removeStorage(name){
        window.localStorage.removeItem(name);
    }
}
export default MUtil;