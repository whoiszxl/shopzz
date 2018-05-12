package com.whoiszxl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.whoiszxl.common.HttpInterceptor;

/**
 * web配置类
 * @author whoiszxl
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

	private static Logger logger = LoggerFactory.getLogger(WebConfig.class);
	
	/**
	 * 注册拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.info("注册拦截器了.....");
		registry.addInterceptor(new HttpInterceptor());
	}
	
}
