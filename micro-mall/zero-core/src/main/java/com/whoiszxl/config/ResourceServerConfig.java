package com.whoiszxl.config;//package com.whoiszxl.edu.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.util.FileCopyUtils;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//
///**
// * 资源服务配置
// *
// * @author whoiszxl
// * @date 2021/4/8
// */
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Configuration
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    private final String[] noAuthPath = new String[] {
//            "/**/sms/send", "/register", "/login",
//
//            "/home/**" ,  "/category/**", "/brand/**", "/product/**",
//
//            "/v2/api-docs", "/swagger-ui.html", "/swagger-resources/**",
//            "/webjars/**", "/v3/**", "/swagger-ui.html/**", "/swagger-ui/**"
//    };
//
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()//关闭csrf
//                .sessionManagement().disable() //关闭session
//                .authorizeRequests()
//                .antMatchers(noAuthPath).permitAll() //放行无需鉴权的地址
//                .antMatchers("/**").authenticated()
//                .and().headers().cacheControl();
//    }
//
//    /**
//     * 设置公钥
//     *
//     * @param resources
//     * @throws Exception
//     */
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenStore(jwtTokenStore());
//
//    }
//
//    private TokenStore jwtTokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }
//
//    @Bean // 放在ioc容器的
//    public JwtAccessTokenConverter accessTokenConverter() {
//        //resource 验证token（公钥） authorization 产生 token （私钥）
//        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
//        String s = null;
//        try {
//            ClassPathResource classPathResource = new ClassPathResource("zero-mall.pub");
//            byte[] bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
//            s = new String(bytes, UTF_8);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        tokenConverter.setVerifierKey(s);
//        return tokenConverter;
//    }
//}
