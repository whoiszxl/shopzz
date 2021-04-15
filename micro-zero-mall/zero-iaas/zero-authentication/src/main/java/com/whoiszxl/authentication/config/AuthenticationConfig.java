package com.whoiszxl.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * 鉴权配置
 *
 * @author whoiszxl
 * @date 2021/4/8
 */
@EnableAuthorizationServer
@Configuration
public class AuthenticationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("customizeUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("zero-mall")
                .secret(passwordEncoder.encode("zero-mall-secret"))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(24 * 7200)
                .refreshTokenValiditySeconds(7 * 24 * 7200)
                .and()
                .withClient("inside-app")
                .secret(passwordEncoder.encode("inside-secret"))
                .authorizedGrantTypes("client_credentials")
                .scopes("all")
                .accessTokenValiditySeconds(7 * 24 *3600);
        super.configure(clients);
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(jwtTokenStore())
                .tokenEnhancer(jwtAccessTokenConverter());
        super.configure(endpoints);
    }

    public TokenStore jwtTokenStore() {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        return jwtTokenStore;
    }

    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        ClassPathResource classPathResource = new ClassPathResource("zero-mall.jks");
        // 获取KeyStoreFactory
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, "123456".toCharArray()) ;
        tokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("zero-mall", "123456".toCharArray()));
        return tokenConverter;
    }
}
