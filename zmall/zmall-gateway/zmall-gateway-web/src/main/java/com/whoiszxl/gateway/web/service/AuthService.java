package com.whoiszxl.gateway.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-25
 **/
@Service
public class AuthService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //从cookie中获取jti的值
    public String getJtiFromCookie(ServerHttpRequest request) {
        HttpCookie httpCookie = request.getCookies().getFirst("uid");
        if (httpCookie != null){
            String jti = httpCookie.getValue();
            return jti;
        }
        return null;
    }

    //从redis中获取jwt
    public String getJwtFromRedis(String jti) {
        String jwt = stringRedisTemplate.boundValueOps(jti).get();
        return jwt;
    }
}

