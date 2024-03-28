package com.whoiszxl.taowu.feign;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.feign.FeignTokenConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 视频feign接口
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@FeignClient(name = "taowu-video", contextId = "videoFeign", configuration = FeignTokenConfig.class)
public interface VideoFeignClient {


    /**
     * 根据ID判断视频内容是否存在
     * @param id
     * @return
     */
    @GetMapping("/checkVideoExistById/{id}")
    ResponseResult<Boolean> checkVideoExistById(@PathVariable Long id);
}
