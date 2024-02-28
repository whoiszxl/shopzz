package com.whoiszxl.taowu.common.token.permission;

import cn.dev33.satoken.stp.StpInterface;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.token.entity.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * sa-token 权限认证机制接口实现
 *
 * @author whoiszxl
 * @date 2021/7/20
 */
@Component
@RequiredArgsConstructor
public class PermissionInterfaceImpl implements StpInterface {

    private final TokenHelper tokenHelper;

    /**
     * 返回一个账号所拥有的权限集合
     * @param loginId 用户登录标识
     * @param loginType 登录类型
     * @return
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        LoginMember loginMember = tokenHelper.getLoginMember();
        return new ArrayList<>(loginMember.getPermissions());
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     * @param loginId 用户登录标识
     * @param loginType 登录类型
     * @return
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        LoginMember loginMember = tokenHelper.getLoginMember();
        return new ArrayList<>(loginMember.getRoles());
    }




}
