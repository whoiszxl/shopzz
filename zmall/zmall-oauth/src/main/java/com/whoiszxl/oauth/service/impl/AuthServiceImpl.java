package com.whoiszxl.oauth.service.impl;

import com.whoiszxl.oauth.service.AuthService;
import com.whoiszxl.oauth.utils.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-25
 **/
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${auth.ttl}")
    private long ttl;

    @Value("${spring.application.name}")
    private String serverId;

    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret) {
        //1. 负载均衡获取oauth2服务地址
        ServiceInstance serviceInstance = loadBalancerClient.choose(serverId);
        URI uri = serviceInstance.getUri();
        String url = uri + "/oauth/token";

        //2. 构建请求体
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username",username);
        body.add("password",password);

        //3. 封装http请求header头
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization",this.getHttpBasic(clientId,clientSecret));
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(body,headers);

        //4. 设置error处理器
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if(response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });

        // 5. restTemplate发起请求
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
        Map map = responseEntity.getBody();
        if (map == null || map.get("access_token") == null || map.get("refresh_token") == null || map.get("jti") == null){
            //申请令牌失败
            throw new RuntimeException("申请令牌失败");
        }

        //6.封装结果数据
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken((String) map.get("access_token"));
        authToken.setRefreshToken((String) map.get("refresh_token"));
        authToken.setJti((String)map.get("jti"));

        //7.将jti作为redis中的key,将jwt作为redis中的value进行数据的存放
        stringRedisTemplate.boundValueOps(authToken.getJti()).set(authToken.getAccessToken(),ttl, TimeUnit.SECONDS);
        return authToken;
    }


    /**
     * 获取basic请求体
     * @param clientId
     * @param clientSecret
     * @return
     */
    private String getHttpBasic(String clientId, String clientSecret) {
        String value = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode(value.getBytes());
        return "Basic " + new String(encode);
    }
}
