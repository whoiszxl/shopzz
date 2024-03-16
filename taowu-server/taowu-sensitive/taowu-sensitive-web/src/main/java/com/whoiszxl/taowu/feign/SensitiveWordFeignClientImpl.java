package com.whoiszxl.taowu.feign;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.dto.KeywordFeignDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SensitiveWordFeignClientImpl implements SensitiveWordFeignClient{

    private final SensitiveWordBs sensitiveWordBs;

    @Override
    public ResponseResult<Boolean> verifyKeyword(KeywordFeignDto keywordFeignDto) {
        boolean containFlag = sensitiveWordBs.contains(keywordFeignDto.getKeyword());
        return ResponseResult.buildByFlag(!containFlag);
    }
}
