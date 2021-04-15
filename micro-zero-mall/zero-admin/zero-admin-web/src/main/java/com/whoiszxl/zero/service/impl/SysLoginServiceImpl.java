package com.whoiszxl.zero.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whoiszxl.zero.constants.RedisPrefixConstants;
import com.whoiszxl.zero.entity.SysMenu;
import com.whoiszxl.zero.entity.params.LoginParam;
import com.whoiszxl.zero.entity.vo.LoginVO;
import com.whoiszxl.zero.exception.ExceptionCatcher;
import com.whoiszxl.zero.feign.JwtToken;
import com.whoiszxl.zero.feign.OAuth2FeignClient;
import com.whoiszxl.zero.service.SysLoginService;
import com.whoiszxl.zero.service.SysMenuService;
import com.whoiszxl.zero.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SysLoginServiceImpl implements SysLoginService {

    @Autowired
    private OAuth2FeignClient oAuth2FeignClient;

    @Autowired
    private SysMenuService sysMenuService;

    @Value("${basic.token:Basic emVyby1tYWxsOnplcm8tbWFsbC1zZWNyZXQ=}")
    private String basicToken;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public LoginVO login(LoginParam loginParam) {
        //需要调用远程鉴权服务来进行登录操作
        ResponseEntity<JwtToken> tokenResponseEntity = oAuth2FeignClient.getToken("password", loginParam.getUsername(), loginParam.getPassword(), "ADMIN_TYPE", basicToken);
        if(tokenResponseEntity == null || tokenResponseEntity.getStatusCode() != HttpStatus.OK) {
            ExceptionCatcher.catchAuthFailEx();
        }

        //获取到accessToken
        JwtToken jwtToken = tokenResponseEntity.getBody();
        String accessToken = jwtToken.getAccessToken();

        //拿到userId
        Jwt jwt = JwtHelper.decode(accessToken);
        String claims = jwt.getClaims();
        JSONObject jwtObject = JSON.parseObject(claims);
        Long userId = Long.valueOf(jwtObject.getString("user_name"));

        //查询菜单数据
        List<SysMenu> menus = sysMenuService.getMenusByUserId(userId);

        //从返回的JWT信息中拿到权限数据
        JSONArray authoritiesJsonArray = jwtObject.getJSONArray("authorities");
        List<SimpleGrantedAuthority> authorities = authoritiesJsonArray.stream()
                .map(authoritiesJson -> new SimpleGrantedAuthority(authoritiesJson.toString()))
                .collect(Collectors.toList());

        //将token存在redis中让网关来做是否存在是否有效的校验
        redisUtils.setEx(RedisPrefixConstants.ADMIN_JWT_PREFIX + accessToken, "", jwtToken.getExpiresIn(), TimeUnit.SECONDS);
        return new LoginVO(jwtToken.getTokenType() + " " + accessToken, menus, authorities);
    }
}
