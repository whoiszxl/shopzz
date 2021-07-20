package com.whoiszxl.permission;

import cn.dev33.satoken.stp.StpInterface;
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
public class PermissionInterfaceImpl implements StpInterface {


    /**
     * 返回一个账号所拥有的权限集合
     * @param loginId 用户登录标识
     * @param loginType 登录类型
     * @return
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // TODO 从Redis中拿到此用户的权限集合
        List<String> list = new ArrayList<>();
        list.add("101");
        list.add("user-add");
        list.add("user-delete");
        list.add("user-update");
        list.add("user-get");
        list.add("article-get");
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     * @param loginId 用户登录标识
     * @param loginType 登录类型
     * @return
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // TODO 从Redis中拿到此用户的角色集合
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("super-admin");
        return list;
    }
}
