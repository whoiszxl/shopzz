package com.whoiszxl.zero.bean;

/**
 * 常用状态码
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
public class StatusCode {
    public static final int OK = 0;//成功
    public static final int ERROR = 1;//失败
    public static final int LOGINERROR = 20002;//用户名或密码错误
    public static final int ACCESSERROR = 20003;//权限不足
    public static final int REMOTEERROR = 20004;//远程调用失败
    public static final int REPERROR = 20005;//重复操作
}