package com.whoiszxl.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.command.MemberApplicationService;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.query.MemberQueryApplicationService;
import com.whoiszxl.query.model.qry.MemberQuery;
import com.whoiszxl.query.model.response.MemberResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理后台相关接口
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@RestController
@RequestMapping("/member")
@Api(tags = "用户管理后台相关接口")
public class MemberAdminController {

    @Autowired
    private MemberApplicationService memberApplicationService;

    @Autowired
    private MemberQueryApplicationService memberQueryApplicationService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取用户列表", notes = "分页获取用户列表", response = MemberResponse.class)
    public ResponseResult<IPage<MemberResponse>> list(@RequestBody MemberQuery query) {

        IPage<MemberResponse> result = memberQueryApplicationService.list(query);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @DeleteMapping("/ban/{memberId}")
    @ApiOperation(value = "禁用用户", notes = "禁用用户", response = ResponseResult.class)
    public ResponseResult<Boolean> ban(@PathVariable Long memberId) {
        memberApplicationService.ban(memberId);
        return ResponseResult.buildSuccess();
    }
}
