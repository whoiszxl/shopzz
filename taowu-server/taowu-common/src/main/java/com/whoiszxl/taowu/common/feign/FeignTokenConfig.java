package com.whoiszxl.taowu.common.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * oauth2 feign配置
 * 多服务调用中传递jwt header信息
 * @author whoiszxl
 * @date 2021/4/8
 */
@Slf4j
public class FeignTokenConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        //在request中获取到jwt的header偷
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            String headerAuth = request.getHeader("Authorization");

            //使用RequestTemplate传递token
            template.header("Authorization", headerAuth);
        }
    }
}