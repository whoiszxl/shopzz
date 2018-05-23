package com.whoiszxl.controller.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SessionExpireConfig {

	@Bean
	public FilterRegistrationBean userFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

		UserFilter userFilter = new UserFilter();
		filterRegistrationBean.setFilter(userFilter);

		List<String> urls = new ArrayList<>();
		filterRegistrationBean.setUrlPatterns(urls);
		filterRegistrationBean.setOrder(3);
		filterRegistrationBean.setName("sessionExpireFilter");
		return filterRegistrationBean;
	}

}