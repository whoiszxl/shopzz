package com.whoiszxl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Flink Log收集器
 */
@SpringBootApplication
@MapperScan("com.whoiszxl.mapper")
public class FlinkCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlinkCollectorApplication.class, args);
    }

}
