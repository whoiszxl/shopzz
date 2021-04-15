package com.whoiszxl.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * jwt token校验过滤器
 *
 * @author whoiszxl
 * @date 2021/4/8
 */
@Component
public class JwtTokenCheckFilter implements GlobalFilter, Ordered {

    /** 没有token也能访问的URL地址 */
    @Value("${noTokenAccessUrls}")
    private Set<String> noTokenAccessUrls;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //判断访问地址是否在无需token的名单内
        String path = exchange.getRequest().getURI().getPath();
        for (String noTokenAccessUrl : noTokenAccessUrls) {
            if(path.startsWith(noTokenAccessUrl)) {
                return chain.filter(exchange);
            }
        }

        String token = getToken(exchange);
        if(StringUtils.isEmpty(token)) {
            return buildUnAuthorizedResult(exchange);
        }

        return chain.filter(exchange);
    }

    /**
     * 从request中获取token
     * @param exchange
     * @return
     */
    private String getToken(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isBlank(authorization)) {
            return null;
        }
        return StringUtils.replace(authorization, "bearer", "");
    }

    /**
     * 构建无权限401返回结果
     * @param exchange
     * @return
     */
    private Mono<Void> buildUnAuthorizedResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set("Content-Type", "application/json;charset=UTF-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", "unauthorized");
        jsonObject.put("error_description", "invalid_token");
        DataBuffer dataBuffer = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        return response.writeWith(Flux.just(dataBuffer));
    }
}
