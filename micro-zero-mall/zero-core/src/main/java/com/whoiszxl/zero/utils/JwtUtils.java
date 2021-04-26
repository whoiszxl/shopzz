package com.whoiszxl.zero.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * jwt工具类
 */
public class JwtUtils {


    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getUsername() {
        return getAuthentication().getPrincipal().toString();
    }

    public static Long getMemberId() {
        return Long.parseLong(getUsername());
    }

    private JwtUtils() {}
}
