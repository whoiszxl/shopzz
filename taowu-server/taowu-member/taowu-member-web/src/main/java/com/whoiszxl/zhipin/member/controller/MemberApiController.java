package com.whoiszxl.zhipin.member.controller;


import cn.hutool.core.bean.BeanUtil;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.token.entity.AppLoginMember;
import com.whoiszxl.zhipin.member.cqrs.response.MemberInfoResponse;
import com.whoiszxl.zhipin.member.service.IMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-04
 */
@Tag(name = "C端: 会员 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApiController {

    private final TokenHelper tokenHelper;

    private final IMemberService memberService;

    @Operation(summary = "获取会员基本信息", description = "获取会员基本信息")
    @GetMapping("/info")
    public ResponseResult<MemberInfoResponse> memberInfo() {
        AppLoginMember appLoginMember = tokenHelper.getAppLoginMember();
        MemberInfoResponse adminDetailResponse = BeanUtil.copyProperties(appLoginMember, MemberInfoResponse.class);
        return ResponseResult.buildSuccess(adminDetailResponse);
    }

}

