package com.whoiszxl.taowu.common.utils;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author whoiszxl
 */
public class MyServletUtil {


    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
    }

    public static String getBrowser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        return userAgent.getBrowser().getName() + " " + userAgent.getVersion();
    }
}
