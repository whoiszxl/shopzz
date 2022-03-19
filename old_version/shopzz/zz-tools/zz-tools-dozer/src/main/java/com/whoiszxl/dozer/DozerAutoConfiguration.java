package com.whoiszxl.dozer;

import com.github.dozermapper.core.Mapper;
import org.springframework.context.annotation.Bean;

/**
 * dozer自動配置類
 *
 * @author whoiszxl
 * @date 2021/11/30
 */
public class DozerAutoConfiguration {

    @Bean
    public DozerUtils dozerUtils(Mapper mapper) {
        DozerUtils dozerUtils = new DozerUtils(mapper);
        return dozerUtils;
    }
}
