package com.whoiszxl.log;

import com.whoiszxl.logger.event.SysLogListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2021/12/1
 */
@Configuration
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SysLogListener sysLogListener(LogService logService) {
        return new SysLogListener(optLogDTO -> {
            logService.saveLog(optLogDTO);
        });
    }
}
