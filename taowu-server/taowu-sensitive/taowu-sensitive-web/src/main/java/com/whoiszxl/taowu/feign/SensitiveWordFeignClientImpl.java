package com.whoiszxl.taowu.feign;

import cn.hutool.core.collection.CollUtil;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.utils.BeanUtil;
import com.whoiszxl.taowu.common.utils.JsonUtil;
import com.whoiszxl.taowu.dto.VideoAuditMqDto;
import com.whoiszxl.taowu.entity.SensitiveWordLog;
import com.whoiszxl.taowu.service.ISensitiveWordLogService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SensitiveWordFeignClientImpl implements SensitiveWordFeignClient{

    private final SensitiveWordBs sensitiveWordBs;

    private final ISensitiveWordLogService sensitiveWordLogService;

    @Hidden
    @Override
    public ResponseResult<Boolean> verifyKeyword(VideoAuditMqDto videoAuditMqDto) {
        List<String> allKeywords = sensitiveWordBs.findAll(videoAuditMqDto.getDescs());
        if(CollUtil.isNotEmpty(allKeywords)) {
            // 如果包含敏感词，则将敏感词记录到数据库中
            SensitiveWordLog log = BeanUtil.copyProperties(videoAuditMqDto, SensitiveWordLog.class);
            log.setVideoId(videoAuditMqDto.getId());
            log.setSensitiveWord(JsonUtil.toJson(allKeywords));
            log.setOriginalText(videoAuditMqDto.getDescs());
            log.setOriginalVideoUrl(videoAuditMqDto.getVideoUrl());

            // TODO 视频敏感内容校验
            log.setVideoSensitiveReason("TODO");
            sensitiveWordLogService.save(log);

            return ResponseResult.buildError();
        }

        return ResponseResult.buildSuccess();
    }
}
