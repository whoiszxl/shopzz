/**
 * Created by hans on 2016/10/31.
 * 购物车数据
 */
var cartModel = {

    // 加入购物车商品
    add : function (data, success) {
        czHttp.getJSON('./data/success.json', data, function (responseData) {
            if(responseData.isok){
                success(responseData);
            }
        });
    },

    // 删除购物车商品
    remove : function (data, success) {
        czHttp.getJSON('./data/success.json', data, function (responseData) {
            if(responseData.isok){
                success(responseData);
            }
        });
    },

    // 修改商品数量
    changeNumber : function (data, success) {
        czHttp.getJSON('./data/success.json', data, function (responseData) {
            if(responseData.isok){
                success(responseData);
            }
        });
    },

    // 购物车统计
    subtotal : function (success) {
        czHttp.getJSON('./data/orders.json', data, function (responseData) {
            if(responseData.isok){
                success(responseData);
            }
        });
    },

    // 购物车列表
    list : function (success) {

        czHttp.getJSON('./data/orders.json', {}, function(responseData){
            success(responseData);
        });
    }
};