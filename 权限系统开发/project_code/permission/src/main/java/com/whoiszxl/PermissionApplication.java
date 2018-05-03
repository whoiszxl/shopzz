package com.whoiszxl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.whoiszxl.dao")
public class PermissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PermissionApplication.class, args);
	}
}
