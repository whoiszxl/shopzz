package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.cqrs.command.SmsSendCommand;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短信feign接口
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@FeignClient(name = "zz-sms-api", contextId = "smsApiFeign", configuration = OAuth2FeignConfig.class)
public interface SmsApiFeignClient {

    @PostMapping("/send")
    @ApiOperation(value = "发送短信", notes = "发送单条短信", response = Boolean.class)
    public ResponseResult<Boolean> send(@RequestBody SmsSendCommand smsSendCommand);
}
