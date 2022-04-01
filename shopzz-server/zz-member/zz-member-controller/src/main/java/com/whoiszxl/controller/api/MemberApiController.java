package com.whoiszxl.controller.api;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.command.MemberApplicationService;
import com.whoiszxl.command.cmd.MemberLoginCommand;
import com.whoiszxl.command.cmd.UpdateMemberCommand;
import com.whoiszxl.query.MemberQueryApplicationService;
import com.whoiszxl.query.model.response.MemberDetailResponse;
import com.whoiszxl.query.model.response.MemberResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 会员接口
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@RestController
@RequestMapping("/api/member")
@Api(tags = "C端:用户相关接口")
public class MemberApiController {

    @Autowired
    private MemberApplicationService memberApplicationService;

    @Autowired
    private MemberQueryApplicationService memberQueryApplicationService;


    @GetMapping("/detail")
    @ApiOperation(value = "获取当前登录用户的详细信息", notes = "获取当前登录用户的详细信息", response = MemberResponse.class)
    public ResponseResult<MemberResponse> memberInfo() {
        MemberResponse memberResponse = memberQueryApplicationService.memberInfo();
        return ResponseResult.buildSuccess(memberResponse);
    }

    @GetMapping("/detail/{memberId}")
    @ApiOperation(value = "通过用户主键ID获取用户详细信息", notes = "通过用户主键ID获取用户详细信息", response = MemberDetailResponse.class)
    public ResponseResult<MemberDetailResponse> memberDetailById(@PathVariable("memberId") String memberId) {
        MemberDetailResponse memberDetailResponse = memberQueryApplicationService.memberDetailById(memberId);
        return ResponseResult.buildSuccess(memberDetailResponse);
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新当前登录用户的详细信息", notes = "更新当前登录用户的详细信息", response = Boolean.class)
    public ResponseResult<Boolean> updateMemberInfo(@RequestBody UpdateMemberCommand updateMemberCommand) {
        memberApplicationService.updateMemberInfo(updateMemberCommand);
        return ResponseResult.buildSuccess();
    }

    @PostMapping("/login")
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录", response = ResponseResult.class)
    public ResponseResult<String> login(@RequestBody MemberLoginCommand loginCommand) {
        String token = memberApplicationService.login(loginCommand);
        return ResponseResult.buildSuccess("登录成功", token);
    }

    @PostMapping("/phone/register")
    @ApiOperation(value = "手机号注册", notes = "手机号注册", response = ResponseResult.class)
    public ResponseResult<String> register(@RequestBody MemberLoginCommand loginCommand) {
        memberApplicationService.register(loginCommand);
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @GetMapping("/logout")
    @ApiOperation(value = "登出", notes = "登出", response = ResponseResult.class)
    public ResponseResult<String> logout() {
        StpUtil.logout();
        return ResponseResult.buildSuccess();
    }




}
