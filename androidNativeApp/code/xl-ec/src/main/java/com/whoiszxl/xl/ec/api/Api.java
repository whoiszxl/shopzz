package com.whoiszxl.xl.ec.api;

public class Api {


    public static final String host = "http://118.126.92.128:10101";


    /**
     * @Method POST
     * jwt登录接口
     */
    public static final String jwt_login = host + "/user/jwt_login";

    /**
     * @Method POST
     * 用户注册接口
     */
    public static final String register = host + "/user/register";


    /**
     * @Method GET
     * 获取商品列表接口
     */
    public static final String index_goods = host + "/product/list?pageSize=8";


    /**
     * @Method GET
     * @Params categoryId 分类id
     * 通过分类id获取分类下的子分类，为空则获取所有顶级分类
     */
    public static final String get_category = host + "/category/get_category";


}
