package com.whoiszxl.taowu.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.entity.Video;
import com.whoiszxl.taowu.service.IVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VideoFeignClientImpl implements VideoFeignClient{

    private final IVideoService videoService;


    @Override
    public ResponseResult<Boolean> checkVideoExistById(Long id) {
        long count = videoService.count(Wrappers.<Video>lambdaQuery().eq(Video::getId, id));
        return ResponseResult.buildSuccess(count == 1);
    }
}
