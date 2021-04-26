package com.whoiszxl.zero.bean;

import lombok.Data;

import java.util.Map;

/**
 * canal消费到的行数据
 */
@Data
public class CanalRowEntity {

    /** 日志文件名 */
    private String logfileName;

    /** 日志文件offset */
    private Long logfileOffset;

    /** 执行时间 */
    private Long executeTime;

    /** 数据库名 */
    private String schemaName;

    /** 表名 */
    private String tableName;

    /** 事件类型 */
    private String eventType;

    /** 字段map */
    private Map<String, String> columns;

}
