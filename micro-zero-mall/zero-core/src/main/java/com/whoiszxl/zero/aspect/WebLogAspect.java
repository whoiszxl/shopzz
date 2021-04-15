package com.whoiszxl.zero.aspect;

import com.whoiszxl.zero.bean.Result;
import com.whoiszxl.zero.bean.WebLog;
import com.whoiszxl.zero.exception.ExceptionCatcher;
import com.whoiszxl.zero.utils.JsonUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * web日志记录切面
 *
 * @author whoiszxl
 * @date 2021/4/8
 */
@Component
@Aspect
@Order(1)
@Slf4j
public class WebLogAspect {

    /**
     * 日志记录：
     *  环绕通知：方法执行之前、之后
     */

    /**
     * 1 定义切入点
     */
    @Pointcut("execution(* com.whoiszxl.*.controller.*.*(..))") // controller 包里面所有类，类里面的所有方法 都有该切面
    public void webLog(){}

    /**
     * 2 记录日志的环绕通知
     */

    @Around("webLog()")
    public Object recodeWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null ;
        WebLog webLog = new WebLog();
        long start = System.currentTimeMillis() ;

        // 执行方法的真实调用
        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());

        long end = System.currentTimeMillis() ;


        webLog.setSpendTime((int)(start-end)/1000); // 请求该接口花费的时间
        // 获取当前请求的request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null) {
            ExceptionCatcher.catchNullEx(Result.buildError("request数据不存在"));
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取安全的上下文
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String url = request.getRequestURL().toString();
        webLog.setUri(request.getRequestURI()); // 设置请求的uri
        webLog.setUrl(url);
        webLog.setBasePath(url); // http://ip:port/
        webLog.setUsername(authentication==null ? "anonymous":authentication.getPrincipal().toString()); // 获取用户的id
        webLog.setIp(request.getRemoteAddr());
        // 获取方法
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        // 获取类的名称
        String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();
        Method method = signature.getMethod();
        // 因为我们会使用Swagger 这工具，我们必须在方法上面添加@ApiOperation(value="")该注解
        // 获取ApiOperation
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        webLog.setDescription(annotation==null ? "no desc":annotation.value());
        webLog.setMethod(targetClassName+"."+method.getName()); // com.bjsxt.controller.UserController.login()
        webLog.setParameter(getMethodParameter(method,proceedingJoinPoint.getArgs())); //{"key_参数的名称":"value_参数的值"}
        webLog.setResult(result);
        log.info(JsonUtil.toJson(webLog));
        return result;
    }

    /**
     * 获取方法的执行参数
     * @param method
     * @param args
     * @return
     * {"key_参数的名称":"value_参数的值"}
     */
    private Object getMethodParameter(Method method, Object[] args) {
        Map<String, Object> methodParametersWithValues = new HashMap<>();
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer =
                new LocalVariableTableParameterNameDiscoverer();
        // 方法的形参名称
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        for (int i = 0; i <parameterNames.length ; i++) {
            if(parameterNames[i].equals("password") || parameterNames[i].equals("file")){
                methodParametersWithValues.put(parameterNames[i],"受限的支持类型") ;
            }else{
                methodParametersWithValues.put(parameterNames[i],args[i]) ;
            }

        }

        return methodParametersWithValues ;
    }
}