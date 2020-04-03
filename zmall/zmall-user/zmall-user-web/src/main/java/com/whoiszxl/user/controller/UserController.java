package com.whoiszxl.user.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.common.exception.custom.JwtAuthException;
import com.whoiszxl.common.sms.SmsSender;
import com.whoiszxl.common.utils.ValidateUtils;
import com.whoiszxl.user.config.TokenDecode;
import com.whoiszxl.user.entity.User;
import com.whoiszxl.user.entity.request.RegisterRequest;
import com.whoiszxl.user.entity.request.SmsRequest;
import com.whoiszxl.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-03-25
 */
@Api(value = "ZMALL-用户管理控制器", tags = "ZMALL-用户管理控制器")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenDecode tokenDecode;

    @Value("${message.isMock}")
    private Boolean isMock;

    @Autowired
    private SmsSender smsSender;

    @ApiOperation("查询所有的用户")
    @GetMapping
    public Result<List<User>> findAll() {
        List<User> list = userService.list();
        return Result.success(list);
    }

    /**
     * 发送短信接口
     * @param smsRequest mobile 手机号
     * @return
     */
    @PostMapping("/sendVerifySms")
    public Result sendVerifySms(@RequestBody SmsRequest smsRequest) {
        return smsSender.sendVerifySms(smsRequest.getMobile(), isMock);
    }


    /**
     * 发送短信接口
     * @return
     */
    @PostMapping("/sendVerifySmsToMyself")
    public Result sendVerifySmsToMyself() {
        String username = tokenDecode.getUsername();
        User currentUser = userService.getById(username);
        return smsSender.sendVerifySms(currentUser.getPhone(), isMock);
    }


    /**
     * 注册
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestBody RegisterRequest registerRequest){
        //校验手机号的密码
        ValidateUtils.checkPasswordEqual(registerRequest.getPassword(), registerRequest.getRePassword());
        ValidateUtils.checkPasswordLevel(registerRequest.getPassword());
        ValidateUtils.checkPhoneRegex(registerRequest.getMobile());

        //校验缓存中的验证码
        boolean isSuccess = userService.checkVerifyCode(registerRequest.getMobile(), registerRequest.getCode());
        if(!isSuccess) {
            throw new JwtAuthException();
            //return Result.buildError("验证码输入错误");
        }

        //入库并清除验证码
        userService.registerToDb(registerRequest);
        userService.removeVerifyInRedis(registerRequest.getMobile());

        return Result.successMsg("注册成功");
    }


    @ApiOperation("增加用户积分")
    @GetMapping("/points/add/{username}")
    public Result addPoints(@PathVariable("username") String username) {
        userService.addUserPoints(username, 1);
        return Result.success();
    }

    @ApiOperation("根据用户名查询用户")
    @ApiImplicitParam(value = "用户名",name = "username",dataType = "string",paramType = "path")
    @GetMapping("/{username}")
    public Result findById(@PathVariable String username){
        User user = userService.getById(username);
        return Result.success(user);
    }

    @ApiOperation("oauth2使用的加载用户信息")
    @ApiImplicitParam(value = "用户名",name = "username",dataType = "string",paramType = "path")
    @GetMapping("/load/{username}")
    public User findUserInfo(@PathVariable("username") String username){
        return userService.getById(username);
    }


    @ApiOperation("新增用户数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户对象", required = true, dataType = "User", paramType = "body")})
    @PostMapping
    public Result add(@RequestBody User user){
        boolean isSave = userService.save(user);
        return isSave ? Result.success() : Result.fail("userd fail");
    }

    @ApiOperation("修改用户数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户对象", required = true, dataType = "User", paramType = "body"),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "integer",paramType = "path")})
    @PutMapping(value="/{username}")
    public Result update(@RequestBody User user, @PathVariable String username){
        user.setUsername(username);
        boolean isUpdate = userService.updateById(user);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除用户数据")
    @ApiImplicitParam(value = "用户名",name = "username",dataType = "string",paramType = "path")
    @DeleteMapping(value = "/{username}" )
    @PreAuthorize("hasAnyAuthority('admin', 'deleteman')")
    public Result delete(@PathVariable String username){
        boolean isDelete = userService.removeById(username);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}