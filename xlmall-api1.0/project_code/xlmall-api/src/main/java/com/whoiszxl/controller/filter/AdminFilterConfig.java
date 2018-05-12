package com.whoiszxl.controller.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author whoiszxl
 *
 */
@Configuration
public class AdminFilterConfig {

	@Bean
	public FilterRegistrationBean adminFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		
		AdminFilter adminFilter = new AdminFilter();
		filterRegistrationBean.setFilter(adminFilter);
		
		List<String> urls = new ArrayList<>();
		urls.add("/manage/*");
		filterRegistrationBean.setUrlPatterns(urls);
		filterRegistrationBean.setOrder(2);
		filterRegistrationBean.setName("adminFilter");
		return filterRegistrationBean;
	}
	
	
	
}

