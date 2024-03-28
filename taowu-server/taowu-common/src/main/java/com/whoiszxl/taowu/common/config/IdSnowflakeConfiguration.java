package com.whoiszxl.taowu.common.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.whoiszxl.taowu.common.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 雪花算法生成器配置
 * @author whoiszxl
 */
@Configuration
public class IdSnowflakeConfiguration {

    @Bean
    public Snowflake snowflake() {
        return IdUtil.getSnowflake();
    }

}
