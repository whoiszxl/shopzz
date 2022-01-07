package com.whoiszxl.log;

import com.whoiszxl.logger.entity.OptLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2021/12/1
 */
@Service
@Slf4j
public class LogService {

    public void saveLog(OptLogDTO optLogDTO) {
        log.info("日志入库：{}", optLogDTO);
    }
}
