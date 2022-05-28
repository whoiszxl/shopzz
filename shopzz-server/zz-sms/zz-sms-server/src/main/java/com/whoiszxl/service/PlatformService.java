package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.Platform;

/**
 * <p>
 * 短信平台表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
public interface PlatformService extends IService<Platform> {

    /**
     * 缓存查平台信息
     * @param accessKeyId
     * @return
     */
    Platform getPlatformByCache(String accessKeyId);

}
