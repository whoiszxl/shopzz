package com.whoiszxl.controller.env;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.common.ViewFileQuery;
import com.whoiszxl.strategy.InstallStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 初始化控制器
 *
 * @author whoiszxl
 * @date 2021/8/13
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/init")
@Api(tags = "初始化相关接口")
public class InitController {

    @Autowired
    private InstallStrategy installStrategy;

    @SaCheckLogin
    @PostMapping("/configHosts")
    @ApiOperation(value = "配置hosts", notes = "配置hosts", response = Object.class)
    public ResponseResult<Object> configHosts() {
        boolean configFlag = installStrategy.configHosts();
        return ResponseResult.buildByFlag(configFlag);
    }

    @SaCheckLogin
    @PostMapping("/syncScript")
    @ApiOperation(value = "同步脚本", notes = "同步脚本", response = Object.class)
    public ResponseResult<Object> syncScript() {
        boolean configFlag = installStrategy.syncScript();
        return ResponseResult.buildByFlag(configFlag);
    }

    @SaCheckLogin
    @PostMapping("/syncSoftware")
    @ApiOperation(value = "同步组件安装包", notes = "同步组件安装包", response = Object.class)
    public ResponseResult<Object> syncSoftware() {
        boolean configFlag = installStrategy.syncSofware();
        return ResponseResult.buildByFlag(configFlag);
    }


    @SaCheckLogin
    @PostMapping("/configSshNoPasswordLogin")
    @ApiOperation(value = "配置免密登录", notes = "配置免密登录", response = Object.class)
    public ResponseResult<Object> configSshNoPasswordLogin() {
        boolean configFlag = installStrategy.configSshNoPasswordLogin();
        return ResponseResult.buildByFlag(configFlag);
    }


    @SaCheckLogin
    @PostMapping("/viewFile")
    @ApiOperation(value = "查看服务器文件", notes = "查看服务器文件", response = String.class)
    public ResponseResult<String> viewFile(@RequestBody ViewFileQuery query) {
        String result = installStrategy.viewFile(query.getAbsolutePath(), query.getServerId());
        return ResponseResult.buildSuccess(result);
    }
}
