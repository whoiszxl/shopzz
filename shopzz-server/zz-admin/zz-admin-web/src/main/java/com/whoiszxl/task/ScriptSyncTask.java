package com.whoiszxl.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 检测脚本同步任务
 *
 * @author whoiszxl
 * @date 2021/8/16
 */
@Slf4j
@Component
public class ScriptSyncTask {


    /**
     * 一分钟扫描一次脚本是否同步到Linux上了，通过文件名匹配。考虑md5sum实现。
     */
    @Scheduled(fixedRate = 60000)
    public void scriptSync() {

    }

}
