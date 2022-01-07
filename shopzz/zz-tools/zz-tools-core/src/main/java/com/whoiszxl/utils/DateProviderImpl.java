package com.whoiszxl.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期服务实现
 *
 * @author whoiszxl
 * @date 2021/3/24
 */
@Component
public class DateProviderImpl implements DateProvider {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    @Override
    public Date dateNow() {
        return new Date();
    }

    @Override
    public LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public LocalDateTime longToLocalDateTime(Long timestamp) {
        return dateToLocalDateTime(new Date(timestamp));
    }
}
