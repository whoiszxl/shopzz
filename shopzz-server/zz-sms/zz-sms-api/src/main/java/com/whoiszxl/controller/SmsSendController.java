package com.whoiszxl.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.SmsBatchSendCommand;
import com.whoiszxl.cqrs.command.SmsSendCommand;
import com.whoiszxl.service.SmsSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信发送相关接口
 *
 * @author whoiszxl
 * @date 2022/5/26
 */
@RestController
@RequestMapping("/sms")
@Api(tags = "短信发送相关接口")
public class SmsSendController {

    @Autowired
    private SmsSendService smsSendService;


    @PostMapping("/send")
    @ApiOperation(value = "发送短信", notes = "发送单条短信", response = Boolean.class)
    public ResponseResult<Boolean> send(@RequestBody SmsSendCommand smsSendCommand) {
        smsSendService.send(smsSendCommand);
        return ResponseResult.buildSuccess();
    }


    @PostMapping("/batch/send")
    @ApiOperation(value = "批量发送短信", notes = "发送多条短信", response = Boolean.class)
    public ResponseResult<Boolean> batchSend(@RequestBody SmsBatchSendCommand smsBatchSendCommand) {
        smsSendService.batchSend(smsBatchSendCommand);
        return ResponseResult.buildSuccess();
    }


}
