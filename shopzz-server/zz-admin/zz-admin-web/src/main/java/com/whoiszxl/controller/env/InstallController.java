package com.whoiszxl.controller.env;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.convert.Convert;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.PlatformConstants;
import com.whoiszxl.constants.SoftwareConstants;
import com.whoiszxl.constants.SoftwareInstallStatusConstants;
import com.whoiszxl.entity.Software;
import com.whoiszxl.entity.common.EcsGenerateQuery;
import com.whoiszxl.entity.common.InstallQuery;
import com.whoiszxl.service.ServerService;
import com.whoiszxl.service.SoftwareService;
import com.whoiszxl.strategy.EcsGenerateStrategy;
import com.whoiszxl.strategy.InstallStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 安装服务控制器
 *
 * @author whoiszxl
 * @date 2021/8/13
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/install")
@Api(tags = "安装服务相关接口")
public class InstallController {

    @Autowired
    private ServerService serverService;

    @Autowired
    private InstallStrategy installStrategy;

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private EcsGenerateStrategy ecsGenerateStrategy;


    @SaCheckLogin
    @PostMapping("/ecsGenerate")
    @ApiOperation(value = "ECS服务器生成", notes = "ECS服务器生成", response = Object.class)
    public ResponseResult<Object> ecsGenerate(@RequestBody EcsGenerateQuery generateQuery) {
        boolean generateFlag = false;
        switch (generateQuery.getPlatform()) {
            case PlatformConstants.ALIYUN:
                generateFlag = ecsGenerateStrategy.generateDefault(generateQuery);
                break;
        }

        return ResponseResult.buildByFlag(generateFlag);
    }

    @SaCheckLogin
    @PostMapping("/install")
    @ApiOperation(value = "安装组件", notes = "安装组件", response = Object.class)
    public ResponseResult<Object> install(@RequestBody InstallQuery installQuery) {
        boolean installFlag = false;
        switch (installQuery.getSoftwareName()) {
            case SoftwareConstants.JDK:
                installFlag = installStrategy.installJDK(installQuery.getServerIds());
                break;
            case SoftwareConstants.ZOOKEEPER:
                installFlag = installStrategy.installZookeeper(installQuery.getServerIds());
                break;
            case SoftwareConstants.KAFKA:
                installFlag = installStrategy.installKafka(installQuery.getServerIds());
                break;
            case SoftwareConstants.HADOOP:
                installFlag = installStrategy.installHadoop();
                break;
            case SoftwareConstants.FLUME:
                installFlag = installStrategy.installFlume(installQuery.getServerIds());
                break;
            case SoftwareConstants.SPARK:
                installFlag = installStrategy.installSpark(installQuery.getServerIds());
                break;
            case SoftwareConstants.HBASE:
                installFlag = installStrategy.installHbase(installQuery.getServerIds());
                break;
            default:
                break;
        }

        //更新安装状态
        Software updateSoftware = softwareService.getBySoftwareName(installQuery.getSoftwareName());
        updateSoftware.setInstallServerIds(Convert.toStr(installQuery.getServerIds()));

        long serverCount = serverService.count(null);
        if(installQuery.getServerIds().size() < serverCount) {
            updateSoftware.setInstallStatus(SoftwareInstallStatusConstants.PART_INSTALL);
        }else if(installQuery.getServerIds().size() == serverCount) {
            updateSoftware.setInstallStatus(SoftwareInstallStatusConstants.ALL_INSTALL);
        }else {
            updateSoftware.setInstallStatus(SoftwareInstallStatusConstants.NOT_INSTALL);
        }
        softwareService.updateById(updateSoftware);
        return ResponseResult.buildByFlag(installFlag);
    }

}
