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


}
