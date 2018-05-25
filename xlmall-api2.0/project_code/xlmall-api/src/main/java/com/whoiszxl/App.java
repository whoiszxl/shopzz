package com.whoiszxl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.whoiszxl"})
@MapperScan("com.whoiszxl.dao")
@EnableScheduling //打开支持定时任务的注解
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
