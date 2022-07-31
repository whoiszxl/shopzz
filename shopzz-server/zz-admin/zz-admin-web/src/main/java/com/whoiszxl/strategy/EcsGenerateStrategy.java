package com.whoiszxl.strategy;

import com.whoiszxl.entity.common.EcsGenerateQuery;

/**
 * 安装策略接口
 */
public interface EcsGenerateStrategy {

    /**
     * 生成默认机器
     * @return
     */
    boolean generateDefault(EcsGenerateQuery generateQuery);

    /**
     * 删除实例
     * @param regionId
     * @param instanceId
     * @return
     */
    boolean deleteInstance(String regionId, String instanceId);


    /**
     * 停止实例
     * @param id
     * @return
     */
    boolean stopInstance(Long id);

    /**
     * 启动实例
     * @param id
     * @return
     */
    boolean startInstance(Long id);

}
