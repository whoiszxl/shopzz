package com.whoiszxl.permission;

import cn.dev33.satoken.stp.StpInterface;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 返回一个账号所拥有的权限集合
     * @param loginId 用户登录标识
     * @param loginType 登录类型
     * @return
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = redisUtils.lRange(RedisKeyPrefixConstants.ADMIN_PRIVILEGE_PREFIX + loginId, 0, -1);
        return permissionList;
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
        if(loginId.equals("admin")) {
            list.add("admin");
        }else {
            list.add("member");
        }
        return list;
    }




}
