package com.whoiszxl.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.google.common.collect.ImmutableMap;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.exception.custom.DataNullException;
import com.whoiszxl.exception.custom.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 全局异常捕获
 * 错误码设计参考 https://www.kancloud.cn/onebase/ob/484204
 *
 * 第一段，1 位，类型，暂用两种，剩下的可以自定义
 *      1 - 业务级别异常
 *      2 - 系统级别异常
 *
 * 第二段，2 位，系统模块
 *      01 - 用户模块
 *      02 - 交易模块
 *      03 - 聊天模块
 *      04 - 行情模块
 *      05 - 钱包模块
 *      06 - otc模块
 *      07 - 管理员模块
 *
 *
 * 第三段，3位，代表详细的错误定位号码，一般自增
 *
 * 通过以上三段共6位的错误码可以直接定位到错误原因，可以迅速找到解决方案。
 * @author: whoiszxl
 * @create: 2020-01-03
 **/
@Slf4j
@ControllerAdvice
public class ExceptionCatchAdvice {


    //定义map，配置异常类型所对应的错误代码
    private static ImmutableMap<Class<? extends Throwable>, ResponseResult> exceptions;
    //定义map的builder对象，去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResponseResult> builder = ImmutableMap.builder();

    static {
        //定义异常类型所对应的错误代码 后续修改到其他地方初始化

        //业务级别异常

        //SA token
        builder.put(NotPermissionException.class, ResponseResult.buildError(101001, "无操作权限"));
        builder.put(NotLoginException.class, ResponseResult.buildError(101002, "用户未登录"));
        builder.put(NotRoleException.class, ResponseResult.buildError(101003, "无角色权限"));

        //系统级别异常
        builder.put(HttpMessageNotReadableException.class, ResponseResult.buildError(200001, "消息体不可读"));
        builder.put(IllegalArgumentException.class, ResponseResult.buildError(200002, "参数填写错误"));
        builder.put(HttpRequestMethodNotSupportedException.class, ResponseResult.buildError(200003, "不支持当前请求方式"));




    }

    //捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        //记录日志
        log.error("全局异常捕捉:{}",exception);
        if(exceptions == null){
            exceptions = builder.build();//EXCEPTIONS构建成功
        }
        //从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应异常
        ResponseResult result = exceptions.get(exception.getClass());
        if(result != null){
            return result;
        }else{
            //返回报错
            return ResponseResult.buildError();
        }
    }

    //捕获Exception此类异常
    @ExceptionHandler(ValidateException.class)
    @ResponseBody
    public ResponseResult catchValidateException(ValidateException ex){
        //记录日志
        log.error("ExceptionCatcher {}", ex.getResult().getMessage());
        if(exceptions == null){
            exceptions = builder.build();//EXCEPTIONS构建成功
        }
        return ex.getResult();
    }


    //捕获Exception此类异常
    @ExceptionHandler(DataNullException.class)
    @ResponseBody
    public ResponseResult catchDataNullException(DataNullException ex){
        if(exceptions == null){
            exceptions = builder.build();//EXCEPTIONS构建成功
        }
        return ex.getResult();
    }


}