package com.whoiszxl.strategy;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.ecs.model.v20140526.*;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.constants.ConfigConstants;
import com.whoiszxl.entity.Server;
import com.whoiszxl.entity.common.EcsGenerateQuery;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.mapper.ConfigMapper;
import com.whoiszxl.mapper.ServerMapper;
import com.whoiszxl.service.ConfigService;
import com.whoiszxl.utils.IdWorker;
import com.whoiszxl.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aliyuncs.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 阿里云抢占式ECS实例生成策略
 *
 * TODO 可配置化参数优化
 */
@Service
@Slf4j
public class AliyunEcsGenerateStrategy implements EcsGenerateStrategy {

    private static final String INSTANCE_STATUS_RUNNING = "Running";
    private static final int INSTANCE_STATUS_CHECK_INTERVAL_MILLISECOND = 3000;
    private static final long INSTANCE_STATUS_TOTAL_CHECK_TIME_ELAPSE_MILLISECOND = 60000 * 3;

    /**
     * 是否只预检此次请求。true：发送检查请求，不会创建实例，也不会产生费用；false：发送正常请求，通过检查后直接创建实例，并直接产生费用
     */
    private static final boolean dryRun = false;

    /**
     * 实例所属的地域ID
     */
    private String regionId = "cn-shenzhen";
    /**
     * 实例的资源规格
     */
    private String instanceType = "ecs.sn1.medium";
    /**
     * 实例的计费方式
     */
    private String instanceChargeType = "PostPaid";
    /**
     * 镜像ID
     */
    private String imageId = "centos_7_6_x64_20G_alibase_20211130.vhd";
    /**
     * 指定新创建实例所属于的安全组ID
     */
    private String securityGroupId = "sg-wz9131jvnj7vlwa5x4dg";
    /**
     * 购买资源的时长
     */
    private Integer period = 1;
    /**
     * 购买资源的时长单位
     */
    private String periodUnit = "Hourly";
    /**
     * 实例所属的可用区编号
     */
    private String zoneId = "random";
    /**
     * 网络计费类型
     */
    private String internetChargeType = "PayByTraffic";
    /**
     * 虚拟交换机ID
     */
    private String vSwitchId = "vsw-wz9dyftqehqk9w67ehcvm";

    /**
     * 公网出带宽最大值
     */
    private Integer internetMaxBandwidthOut = 50;
    /**
     * 是否为实例名称和主机名添加有序后缀
     */
    private boolean uniqueSuffix = false;
    /**
     * 是否为I/O优化实例
     */
    private String ioOptimized = "optimized";
    /**
     * 后付费实例的抢占策略
     */
    private String spotStrategy = "SpotWithPriceLimit";
    /**
     * 设置实例的每小时最高价格
     */
    private Float spotPriceLimit = 0.587f;
    /**
     * 是否开启安全加固
     */
    private String securityEnhancementStrategy = "Active";
    /**
     * 系统盘大小
     */
    private String systemDiskSize = "40";
    /**
     * 系统盘的磁盘种类
     */
    private String systemDiskCategory = "cloud_efficiency";

    String accessKeyId;
    String accessSecret;

    @Autowired
    private ConfigService configService;

    @Autowired
    private ServerMapper serverMapper;

    @PostConstruct
    public void init() {
        accessKeyId = configService.getByKey(ConfigConstants.ACCESS_KEY_ID);
        accessSecret = configService.getByKey(ConfigConstants.ACCESS_SECRET);
    }

    @Override
    public boolean stopInstance(Long id) {
        return false;
    }

    @Override
    public boolean startInstance(Long id) {
        return false;
    }

    @Override
    public boolean deleteInstance(String regionId, String instanceId) {
        DeleteInstanceRequest request = new DeleteInstanceRequest();
        request.setSysRegionId(regionId);
        request.setInstanceId(instanceId);
        IAcsClient client = initClient();
        try {
            DeleteInstanceResponse acsResponse = client.getAcsResponse(request);
            if(acsResponse == null) {
                return false;
            }

            int delete = serverMapper.delete(Wrappers.<Server>lambdaQuery().eq(Server::getInstanceId, instanceId));
            if(delete == 0) {
                return false;
            }
        } catch (ClientException e) {
            log.error("删除实例失败", e);
        }

        return true;
    }

    @Override
    public boolean generateDefault(EcsGenerateQuery generateQuery) {

        /**
         * 使用须知：
         * 调用 OpenAPI 创建实例会自动扣费，请谨慎调用
         * 您的账号必须支持账号余额支付或信用支付，请确保金额充足
         *
         * 调用创建实例API后会查询实例的状态，直到变成Running为止
         *
         * 由于实例的密码为敏感信息，故不在这里显示，您可调用setPassword自行设置密码
         */
        RunInstancesResponse response = callOpenApi(composeRunInstancesRequest(generateQuery));
        if(response == null) {
            return false;
        }

        log.info("ECS生成成功，参数为：[{}],结果为：[{}]", generateQuery, response);

        //保存到DB
        List<Server> serverList = new ArrayList<>();

        DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
        describeInstancesRequest.setSysRegionId(regionId);
        describeInstancesRequest.setInstanceIds(JSON.toJSONString(response.getInstanceIdSets()));

        //立即调用查询instance时未分配公网和内网ip，所以延迟10秒，可以优化为消息队列创建数据库记录
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DescribeInstancesResponse describeInstancesResponse = callOpenApi(describeInstancesRequest);
        List<DescribeInstancesResponse.Instance> instances = describeInstancesResponse.getInstances();

        for (DescribeInstancesResponse.Instance instance : instances) {
            Server server = new Server();
            server.setInstanceId(instance.getInstanceId());
            server.setServerName(instance.getInstanceName());
            server.setServerDetail(JsonUtil.toJson(instance));

            if(CollectionUtils.isNotEmpty(instance.getPublicIpAddress())) {
                server.setServerOuterIp(instance.getPublicIpAddress().get(0));
            }

            if(CollectionUtils.isNotEmpty(instance.getInnerIpAddress())) {
                server.setServerInnerIp(instance.getInnerIpAddress().get(0));
            }else if(CollectionUtils.isNotEmpty(instance.getNetworkInterfaces())) {
                server.setServerInnerIp(instance.getNetworkInterfaces().get(0).getPrimaryIpAddress());
            }

            server.setServerPort("22");
            server.setServerUsername("root");
            server.setServerPassword(generateQuery.getPassword());
            server.setStatus(StatusEnum.OPEN.getCode());
            server.setVersion(0L);
            server.setIsDeleted(0);
            serverList.add(server);
        }

        serverMapper.insertBatchSomeColumn(serverList);

        callToDescribeInstances(response.getInstanceIdSets());
        return true;
    }


    private RunInstancesRequest composeRunInstancesRequest(EcsGenerateQuery generateQuery) {
        RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
        runInstancesRequest.setDryRun(dryRun);
        runInstancesRequest.setSysRegionId(regionId);
        runInstancesRequest.setInstanceType(instanceType);
        runInstancesRequest.setInstanceChargeType(instanceChargeType);
        runInstancesRequest.setImageId(imageId);
        runInstancesRequest.setSecurityGroupId(securityGroupId);
        runInstancesRequest.setPeriod(period);
        runInstancesRequest.setPeriodUnit(periodUnit);
        runInstancesRequest.setZoneId(zoneId);
        runInstancesRequest.setInternetChargeType(internetChargeType);
        runInstancesRequest.setVSwitchId(vSwitchId);
        runInstancesRequest.setInstanceName(generateQuery.getInstanceName());
        runInstancesRequest.setAmount(generateQuery.getQuantity());
        runInstancesRequest.setInternetMaxBandwidthOut(internetMaxBandwidthOut);
        runInstancesRequest.setHostName(generateQuery.getHostName());
        runInstancesRequest.setUniqueSuffix(uniqueSuffix);
        runInstancesRequest.setIoOptimized(ioOptimized);
        runInstancesRequest.setSpotStrategy(spotStrategy);
        runInstancesRequest.setSpotPriceLimit(spotPriceLimit);
        runInstancesRequest.setSecurityEnhancementStrategy(securityEnhancementStrategy);
        runInstancesRequest.setSystemDiskSize(systemDiskSize);
        runInstancesRequest.setSystemDiskCategory(systemDiskCategory);
        runInstancesRequest.setPassword(generateQuery.getPassword());

        return runInstancesRequest;
    }


    /**
     * 每3秒中检查一次实例的状态，超时时间设为3分钟。
     * @param instanceIds 需要检查的实例ID
     */
    private void callToDescribeInstances(List<String> instanceIds) {
        Long startTime = System.currentTimeMillis();
        for(;;) {
            sleepSomeTime(INSTANCE_STATUS_CHECK_INTERVAL_MILLISECOND);
            DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
            describeInstancesRequest.setSysRegionId(regionId);
            describeInstancesRequest.setInstanceIds(JSON.toJSONString(instanceIds));
            DescribeInstancesResponse describeInstancesResponse = callOpenApi(describeInstancesRequest);
            Long timeStamp = System.currentTimeMillis();
            if(describeInstancesResponse == null) {
                continue;
            } else {
                for(DescribeInstancesResponse.Instance instance : describeInstancesResponse.getInstances()) {
                    if(INSTANCE_STATUS_RUNNING.equals(instance.getStatus())) {
                        instanceIds.remove(instance.getInstanceId());
                        log.info("实例启动成功, 实例id: {}", instance.getInstanceId());
                    }
                }
            }
            if(instanceIds.size() == 0) {
                log.info("所有实例启动成功");
                return;
            }
        }
    }

    /**
     * 调用OpenAPI的方法，这里进行了错误处理
     *
     * @param request AcsRequest, Open API的请求
     * @param <T> AcsResponse 请求所对应返回值
     * @return 返回OpenAPI的调用结果，如果调用失败，则会返回null
     */
    private <T extends AcsResponse> T callOpenApi(AcsRequest<T> request) {
        IAcsClient client = initClient();
        try {
            T response = client.getAcsResponse(request, false, 0);
            log.info("OpenAPI调用成功:{}", request.getSysActionName());
            return response;
        } catch (ServerException e) {
            log.info("OpenAPI调用失败:{}:{}", e.getErrCode(), e.getMessage());
        } catch (ClientException e) {
            log.info("OpenAPI客户端调用失败:{}:{}", e.getErrCode(), e.getMessage());
        }
        return null;
    }

    /**
     * 需要填充账号的AccessKey ID，以及账号的Access Key Secret
     */
    private IAcsClient initClient() {
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
        return new DefaultAcsClient(profile);
    }

    private static void sleepSomeTime(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
