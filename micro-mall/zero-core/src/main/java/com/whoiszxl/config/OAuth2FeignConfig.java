package com.whoiszxl.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
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
public class OAuth2FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        //在request中获取到jwt的header偷
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.info("无request对象，无法传递");
            return;
        }
        assert requestAttributes != null;
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String header = request.getHeader("zxltoken");
        String headerAuth = request.getHeader("Authorization");

        //使用RequestTemplate传递token
        if (!StringUtils.isEmpty(header)) {
            template.header("zxltoken", header);
            template.header("Authorization", headerAuth);
        }
    }
}