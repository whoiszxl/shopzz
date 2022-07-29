package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.KafkaTopicConstants;
import com.whoiszxl.cqrs.command.LoggerCollectCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据生成控制器
 */
@RestController
@RequestMapping("/api/logger/")
@Api(tags = "数据生成控制器")
public class LoggerApiController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @SaCheckLogin
    @PostMapping("/collect")
    @ApiOperation(value = "收集日志", notes = "收集日志", response = Boolean.class)
    public ResponseResult<Boolean> collect(@RequestBody LoggerCollectCommand command) {
        kafkaTemplate.send(KafkaTopicConstants.ODS_DB_LOG, command.getData());
        return ResponseResult.buildSuccess();
    }

}

