package com.whoiszxl.taowu.controller;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.dto.KeywordFeignDto;
import com.whoiszxl.taowu.feign.SensitiveWordFeignClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 敏感词匹配 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-13
 */
@Tag(name = "C端：敏感词匹配 API")
@RestController
@RequestMapping("/api/sensitive-word")
@RequiredArgsConstructor
public class SensitiveWordController {

    private final SensitiveWordBs sensitiveWordBs;

    @GetMapping("/verify/{keyword}")
    @Operation(summary = "校验", description = "校验")
    public ResponseResult<List<String>> publish(@PathVariable("keyword") String keyword) {
        List<String> all = sensitiveWordBs.findAll(keyword);
        return ResponseResult.buildSuccess(all);
    }
}

