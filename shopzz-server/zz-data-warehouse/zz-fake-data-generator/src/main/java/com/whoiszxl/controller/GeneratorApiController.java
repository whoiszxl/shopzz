package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.DataGenerateExecuteCommand;
import com.whoiszxl.service.DataGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 数据生成控制器
 */
@RestController
@RequestMapping("/api/data/generator/")
@Api(tags = "数据生成控制器")
public class GeneratorApiController {

    @Autowired
    private DataGeneratorService dataGeneratorService;

    @SaCheckLogin
    @PostMapping("/db/execute")
    @ApiOperation(value = "生成fake数据到数据库", notes = "生成fake数据到数据库", response = Boolean.class)
    public ResponseResult<Boolean> dbExecute(@RequestBody DataGenerateExecuteCommand executeCommand) {
        return ResponseResult.buildByFlag(dataGeneratorService.dbExecute(executeCommand));
    }

    @SaCheckLogin
    @PostMapping("/log/execute")
    @ApiOperation(value = "生成fake埋点日志到Kafka", notes = "生成fake埋点日志到Kafka", response = Boolean.class)
    public ResponseResult<Boolean> logExecute(@RequestBody DataGenerateExecuteCommand executeCommand) {
        return ResponseResult.buildByFlag(dataGeneratorService.logExecute(executeCommand));
    }

}

