package com.whoiszxl.taowu.controller;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.service.IMemberRelationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户关系接口
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@RestController
@RequestMapping("/api/member/relation")
@Tag(name = "C端:用户关系接口 API")
@RequiredArgsConstructor
public class MemberRelationApiController {

    private final IMemberRelationService memberRelationService;

    @PostMapping("/attention/{memberId}")
    @Operation(summary = "关注一个用户", description = "关注一个用户")
    public ResponseResult<Boolean> attention(@PathVariable Long memberId) {
        Boolean flag = memberRelationService.attention(memberId);
        return ResponseResult.buildByFlag(flag);
    }

    @PostMapping("/unattention/{memberId}")
    @Operation(summary = "取消关注一个用户", description = "取消关注一个用户")
    public ResponseResult<Boolean> unattention(@PathVariable Long memberId) {
        Boolean flag = memberRelationService.unattention(memberId);
        return ResponseResult.buildByFlag(flag);
    }

}
